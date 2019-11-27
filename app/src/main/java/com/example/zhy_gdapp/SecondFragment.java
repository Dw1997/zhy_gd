package com.example.zhy_gdapp;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.adapter.OrderAdapter;
import com.example.zhy_gdapp.adapter.OuterAdapter;
import com.example.zhy_gdapp.beans.Outorder;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SecondFragment extends Fragment {
    private String TAG = "SecondFragment";
    public List<Outorder> listod = new ArrayList<Outorder>();
    private ListView listView;
    OuterAdapter newop;
    private Handler handler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.two_fragment,container,false);
        listView = (ListView) messageLayout.findViewById(R.id.lv_two);
        Log.d(TAG,"==============");
        listod = getorders();
        newop = new OuterAdapter(getActivity(),R.layout.list_item,listod);
        listView.setAdapter(newop);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    listod = getorders();
                    handler.sendMessage(handler.obtainMessage(0,listod));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try{
            new Thread(runnable).start();
            handler = new Handler(){
                public void handleMessage(Message msg){
                    if(msg.what==0){
                        newop.notifyDataSetChanged();
                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }
        return messageLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public List<Outorder> getorders(){
        List<Outorder> oo = new ArrayList<Outorder>();
        String usertype = SharePreUtils.getType(getActivity());
        String phone = SharePreUtils.getPhone(getActivity());
        String whoo = "";
        String url="";
        if(usertype.equals("2"))
            url = "http:dwy.dwhhh.cn/zhy/api/gp";
        if(usertype.equals("1"))
            url = "http:dwy.dwhhh.cn/zhy/api/user_out?ph="+phone;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(),"网络连接失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG,res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
//                    Looper.prepare();
//                    Toast.makeText(getActivity(),"邮件获取成功",Toast.LENGTH_LONG).show();
//                    Looper.loop();
                    Log.d(TAG,json.getString("result").getClass().toString());
                    JSONArray ret = json.getJSONArray("result");

                    for(int i=0;i<ret.size();i++){
                        String woc = ret.get(i).toString();
                        com.alibaba.fastjson.JSONObject wonm = JSONObject.parseObject(woc);
                        String id = wonm.getString("id");
                        String uname = wonm.getString("uname");
                        String uphone = wonm.getString("uphone");
                        String uaddr = wonm.getString("uaddr");
                        String gname = wonm.getString("gname");
                        String gphone = wonm.getString("gphone");
                        String gaddr = wonm.getString("gaddr");
                        String time = wonm.getString("time");
                        String state = wonm.getString("state");
                        Outorder od = new Outorder(id,uname,uphone,uaddr,gname,gphone,gaddr,time,state);
                        oo.add(od);
                    }
//                    orderAdapter.notifyDataSetChanged();
                    Log.d(TAG,oo.get(0).toString());

                }
                else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),"快递获取失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
        return oo;
    }
}
