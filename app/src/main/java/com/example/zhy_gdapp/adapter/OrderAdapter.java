package com.example.zhy_gdapp.adapter;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.beans.Orders;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderAdapter extends ArrayAdapter {

    private int resouceId;
    List<Orders> listorder;
    Orders orders;
    Context mContent;
    String usertype;
    String inf;
    int pos;

    public OrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public OrderAdapter(Context context,int listview,List<Orders> lists,String utype){
        super(context,listview,lists);
        resouceId=listview;
        mContent=context;
        listorder=lists;
        usertype=utype;
    }

    @Override
    public int getCount(){
        return listorder.size();
    }

    @Override
    public Object getItem(int position){
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        viewHolder holder;
        orders = listorder.get(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item,null);
            holder = new viewHolder();
            holder.tv_id=(TextView) view.findViewById(R.id.lv_tv1);
            holder.tv_na=(TextView) view.findViewById(R.id.lv_tv2);
            holder.tv_ti=(TextView) view.findViewById(R.id.lv_tv3);
            view.setTag(holder);
        }else{
            view=convertView;
            holder=(viewHolder) view.getTag();
        }

        holder.tv_id.setText(orders.getOrderid());
        if(usertype.equals("1"))
            holder.tv_na.setText(get_addr_name(orders.getPoster()));
        if (usertype.equals("2"))
            holder.tv_na.setText(get_addr_name(orders.getGetuser()));
        holder.tv_ti.setText(orders.getTimee());
        return view;
    }

    class viewHolder{
        TextView tv_id,tv_na,tv_ti;
    }

    public String get_addr_name(String phone){
        String url = "http://dwy.dwhhh.cn/zhy/api/gan?tp="+usertype+"&up="+phone;
        Log.d("orderadpater",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(),"网络连接失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("OrderAdapter", res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                inf = json.getString("result");
            }

        });
        return inf;
    }
}
