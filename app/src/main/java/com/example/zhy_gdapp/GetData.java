package com.example.zhy_gdapp;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.beans.Orders;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetData {
    private static List<Orders> listo=null;
    public static List<Orders> getdata(final String url){
        System.out.println("------------"+url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("------------网络连接失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                System.out.println("------------"+res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
                    System.out.println("------------"+json.getString("result"));
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
                        listo.add(od);
                    }
                }
                else{
                    System.out.println("------------没有数据");
                }
            }
        });
        return listo;
    }
}
