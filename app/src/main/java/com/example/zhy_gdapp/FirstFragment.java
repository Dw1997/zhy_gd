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
import com.example.zhy_gdapp.beans.Orders;
import com.example.zhy_gdapp.utils.SharePreUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment {
    private String TAG = "FirstFragmrnt";

    public List<Orders> listo = new ArrayList<Orders>();
    private ListView listview;
    OrderAdapter orderAdapter;
    private Handler handler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.one_fragment,container,false);
        listview = (ListView) messageLayout.findViewById(R.id.lv_one);
        listo = getorders();
        orderAdapter = new OrderAdapter(getActivity(),R.layout.list_item,listo);
        listview.setAdapter(orderAdapter);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    listo = getorders();
                    handler.sendMessage(handler.obtainMessage(0,listo));

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
                        orderAdapter.notifyDataSetChanged();
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

    public List<Orders> getorders(){
        List<Orders> oo = new ArrayList<Orders>();
        String usertype = SharePreUtils.getType(getActivity());
        String whoo = "";
        if(usertype.equals("2"))
            whoo = "poster";
        if(usertype.equals("1"))
            whoo = "getuser";
        String userphone = SharePreUtils.getPhone(getActivity());
        String url = "http://dwy.dwhhh.cn/zhy/api/orders?who="+whoo+"&phone="+userphone+"&gp=1";
        Log.d("mainactivity",url);
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
                        String getpost = wonm.getString("getpost");
                        String getuser = wonm.getString("getuser");
                        String orderid = wonm.getString("orderid");
                        String timee = wonm.getString("timee");
                        String state = wonm.getString("state");
                        String poster = wonm.getString("poster");
                        Log.d("wonm",getpost+orderid);
                        Orders od = new Orders(orderid,getuser,poster,state,getpost,timee);
                        oo.add(od);
                    }
//                    orderAdapter.notifyDataSetChanged();
                    Log.d("firstfragment",oo.get(0).toString());

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
