package com.example.zhy_gdapp.adminac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.adapter.CommentAdapter;
import com.example.zhy_gdapp.beans.Comments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DelposterActivity extends Activity implements View.OnClickListener{
    private Button bt11,bt22;
    private ListView listView;
    private List<Comments> listc = new ArrayList<Comments>();
    CommentAdapter commentAdapter;
    String ph;
    String name;
    private Handler handler = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_poser);
        Intent intent = getIntent();
        ph = intent.getStringExtra("ph");
        name = intent.getStringExtra("user");
        getcomment();
        InitView();
    }

    private void InitView(){
        bt11 = findViewById(R.id.cpd_bt1);
        bt11.setText("<=="+name+"的评论");
        bt22 = findViewById(R.id.cpd_bt2);
        bt11.setOnClickListener(this);
        bt22.setOnClickListener(this);
        listView = findViewById(R.id.cpd_lv1);
        commentAdapter = new CommentAdapter(DelposterActivity.this,R.layout.comment_item,listc);
        listView.setAdapter(commentAdapter);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    getcomment();
                    handler.sendMessage(handler.obtainMessage(1,listc));

                    } catch (InterruptedException ex) {
                    ex.printStackTrace();

                }
            }
        };
        try{
            new Thread(runnable).start();
            handler = new Handler(){
                public void handleMessage(Message msg){
                    if(msg.what==1){
                        commentAdapter.notifyDataSetChanged();
                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void getcomment(){
        String url = "http://dwy.dwhhh.cn/zhy/api/repoc?ph="+ph;
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(DelposterActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("-----",url);
                Log.d("firstfragment-------res",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
//                    Looper.prepare();
//                    Toast.makeText(getActivity(),"邮件获取成功",Toast.LENGTH_LONG).show();
//                    Looper.loop();
                    Log.d("firstFragemnt--res",json.getString("result").getClass().toString());
                    JSONArray ret = json.getJSONArray("result");

                    for(int i=0;i<ret.size();i++){
                        String woc = ret.get(i).toString();
                        com.alibaba.fastjson.JSONObject wonm = JSONObject.parseObject(woc);
                        String id = wonm.getString("id");
                        String user = wonm.getString("user");
                        String poser = wonm.getString("poser");
                        String comm = wonm.getString("comm");
                        String timee = wonm.getString("time");
                        Comments od = new Comments(id,user,poser,comm,timee);
                        listc.add(od);
                        Collections.reverse(listc);
                    }
//                    orderAdapter.notifyDataSetChanged();
                    Log.d("firstfragment",listc.get(0).toString());

                }
                else{
                    Looper.prepare();
                    Toast.makeText(DelposterActivity.this,"暂无评论",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

            }
        });

    }

    private void delpo(){
        String url = "http://dwy.dwhhh.cn/zhy/api/deluser?ph="+ph;
        Log.d("=====",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DelposterActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("--------",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DelposterActivity.this,"删除此用户成功",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DelposterActivity.this,"删除此用户失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cpd_bt1:
                finish();
                break;
            case R.id.cpd_bt2:
                delpo();
                break;
        }
    }
}
