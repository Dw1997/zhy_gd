package com.example.zhy_gdapp;

import android.app.Fragment;
import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.one_fragment,container,false);
        listview = (ListView) messageLayout.findViewById(R.id.lv_one);
        getorders();
        orderAdapter = new OrderAdapter(getActivity(),R.layout.list_item,listo);
        listview.setAdapter(orderAdapter);
        return messageLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void getorders(){
        String usertype = SharePreUtils.getType(getActivity());
        String whoo = "";
        if(usertype.equals("2"))
            whoo = "poster";
        if(usertype.equals("1"))
            whoo = "getuser";
        String userphone = SharePreUtils.getPhone(getActivity());
        String url = "http://dwy.dwhhh.cn/zhy/api/orders?who="+whoo+"&phone="+userphone+"&gp=0";
        Log.d("mainactivity",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(),"网络连接失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
                    Toast.makeText(getActivity(),"邮件获取成功",Toast.LENGTH_LONG).show();
                    JSONArray ret = JSONArray.parseArray(json.getString("result"));
                    for(Object i:ret){
                        JSONObject jj = JSONObject.parseObject(i.toString());
                        String orderid = jj.getString("orderid");
                        String getuser = jj.getString("gteuser");
                        String poster = jj.getString("poster");
                        String state = jj.getString("state");
                        String getpost = jj.getString("getpost");
                        String timee = jj.getString("time");
                        Orders orders = new Orders(orderid,getuser,poster,state,getpost,timee);
                        listo.add(orders);
                    }
                    Log.d("tyep",ret.getClass().toString());
                    Log.d("-----------",listo.toString());
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    Toast.makeText(getActivity(),"快递获取失败",Toast.LENGTH_LONG).show();

            }
        });
    }

}
