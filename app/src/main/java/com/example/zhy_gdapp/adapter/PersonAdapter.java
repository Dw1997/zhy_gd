package com.example.zhy_gdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.beans.Person;

import java.util.List;

public class PersonAdapter extends ArrayAdapter {
    private int resouceId;
    List<Person> listorder;
    Person orders;
    Context mContent;
    int pos;

    public PersonAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public PersonAdapter(Context context,int listview,List<Person> lists){
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
            holder = new PersonAdapter.viewHolder();
            holder.tv_id=(TextView) view.findViewById(R.id.lv_tv1);
            holder.tv_na=(TextView) view.findViewById(R.id.lv_tv2);
            holder.tv_ti=(TextView) view.findViewById(R.id.lv_tv3);
            view.setTag(holder);
        }else{
            view=convertView;
            holder=(viewHolder) view.getTag();
        }

        holder.tv_id.setText(orders.getUsername());
        holder.tv_na.setText(orders.getUserphone());
        holder.tv_ti.setText(orders.getUseraddr());
        return view;
    }

    class viewHolder{
        TextView tv_id,tv_na,tv_ti;
    }
}
