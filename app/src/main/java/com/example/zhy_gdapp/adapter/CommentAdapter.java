package com.example.zhy_gdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.beans.Comments;

import org.w3c.dom.Comment;

import java.util.List;

public class CommentAdapter extends ArrayAdapter {
    private int resourceId;
    List<Comments> listc;
    Context mcontext;
    Comments mcomment;

    public CommentAdapter(@NonNull Context context, int resource, List<Comments> listcd) {
        super(context, resource);
        mcontext = context;
        resourceId = resource;
        listc = listcd;

    }

    @Override
    public int getCount(){
        return listc.size();
    }

    @Override
    public Object getItem(int position){
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        viewHolder holder;
        mcomment = listc.get(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item,null);
            holder = new viewHolder();
            holder.tv_co = (TextView) view.findViewById(R.id.coi_tv1);
            holder.tv_ti = (TextView) view.findViewById(R.id.coi_tv2);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (viewHolder) view.getTag();
        }

        holder.tv_co.setText(mcomment.getComm());
        holder.tv_ti.setText(mcomment.getTimee());
        return view;
    }



    class viewHolder{
        TextView tv_co,tv_ti;
    }
}
