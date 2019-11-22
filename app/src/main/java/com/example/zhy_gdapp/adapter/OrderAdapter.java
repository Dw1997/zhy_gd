package com.example.zhy_gdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.beans.Orders;

import java.util.List;

public class OrderAdapter extends ArrayAdapter {

    private int resouceId;
    List<Orders> listorder;
    Orders orders;
    Context mContent;
    int pos;

    public OrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public OrderAdapter(Context context,int listview,List<Orders> lists){
        super(context,listview,lists);
        resouceId=listview;
        mContent=context;
        listorder=lists;
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
        holder.tv_na.setText(orders.getPoster());
        holder.tv_ti.setText(orders.getTimee());
        return view;
    }

    class viewHolder{
        TextView tv_id,tv_na,tv_ti;
    }
}
