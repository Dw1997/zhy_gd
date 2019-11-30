package com.example.zhy_gdapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.adapter.OuterAdapter;
import com.example.zhy_gdapp.beans.Outorder;
import com.example.zhy_gdapp.dialog.Dialog_out;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PoseroutActivity extends Activity {
    private final static String TAG="PoseroutActivity";
    private TextView tv_title;
    private ListView lv;
    public List<Outorder> listod = new ArrayList<Outorder>();
    OuterAdapter outerAdapter;
    private Handler handler = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_send);
        InitView();
        listod = getorders();
        outerAdapter = new OuterAdapter(PoseroutActivity.this,R.layout.list_item,listod);
        lv.setAdapter(outerAdapter);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    listod=getorders();
                    handler.sendMessage(handler.obtainMessage(0,listod));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try{
            new Thread(runnable).start();
            handler = new Handler(){
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        outerAdapter.notifyDataSetChanged();
                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newDialog(listod.get(position),SharePreUtils.getType(PoseroutActivity.this));
            }
        });
    }

    private void InitView(){
        tv_title = findViewById(R.id.os_et1);
        lv = findViewById(R.id.os_lv);
    }

    public List<Outorder> getorders(){
        List<Outorder> oo = new ArrayList<Outorder>();
        String phone = SharePreUtils.getPhone(PoseroutActivity.this);
        String url="http://dwy.dwhhh.cn/zhy/api/nso?id="+phone;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(PoseroutActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG,res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
                    Log.d(TAG,json.getString("result").getClass().toString());
                    JSONArray ret = json.getJSONArray("result");

                    for(int i=0;i<ret.size();i++){
                        String woc = ret.get(i).toString();
                        com.alibaba.fastjson.JSONObject wonm = JSONObject.parseObject(woc);
                        String id = wonm.getString("id");
                        String uname = wonm.getString("uname");
                        String uphone = wonm.getString("uphone");
                        String areaid = wonm.getString("areaid");
                        String uaddr = wonm.getString("uaddr");
                        String gname = wonm.getString("gname");
                        String gphone = wonm.getString("gphone");
                        String gaddr = wonm.getString("gaddr");
                        String time = wonm.getString("time");
                        String state = wonm.getString("state");
                        String poster = wonm.getString("poster");
                        String kuaidid = wonm.getString("kuaidid");
                        Outorder od = new Outorder(id,uname,uphone,areaid,uaddr,gname,gphone,gaddr,time,state,poster,kuaidid);
                        oo.add(od);
                    }
                    Log.d(TAG,oo.get(0).toString());

                }
                else{
                    Toast.makeText(PoseroutActivity.this,"快递获取失败",Toast.LENGTH_LONG).show();
                }
            }
        });
        return oo;
    }

    Dialog_out dialog_out;
    private void  newDialog(Outorder od,String typeee){
        dialog_out = new Dialog_out(od,typeee);
        final View view = dialog_out.getView();
        dialog_out.show(getFragmentManager(),"dialog_out");
    }
}
