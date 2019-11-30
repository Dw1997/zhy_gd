package com.example.zhy_gdapp.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.beans.Orders;
import com.example.zhy_gdapp.beans.Person;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class Dialog_in extends DialogFragment implements View.OnClickListener{
    private final static String TAG="Dialog_in";
    private TextView tv1,tv3,tv5;
    private TextView tv2,tv4,tv6;
    private Button bt1,bt2;
    String typee;
    Orders orders;
    private Handler handler = null;

    @SuppressLint("ValidFragment")
    public Dialog_in(Orders order,String typeee){
        super();
        orders = order;
        typee = typeee;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_in,container);

        InitView(view);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    getinf();
                    handler.sendMessage(handler.obtainMessage(0,"hhh"));

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
                        Log.d(TAG,"hhh");

                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    public void InitView(View v){
        tv1 = v.findViewById(R.id.dii_tv1);
        tv3 = v.findViewById(R.id.dii_tv3);
        tv5 = v.findViewById(R.id.dii_tv5);

        tv2 = v.findViewById(R.id.dii_tv2);
        tv6 = v.findViewById(R.id.dii_tv4);
        tv4 = v.findViewById(R.id.dii_tv6);

        bt1 = v.findViewById(R.id.dii_bt1);
        bt2 = v.findViewById(R.id.dii_bt2);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);

        if (typee.equals("1")){
            tv1.setText("快递员  姓名：");
            tv3.setText("快递员手机号：");
            tv5.setText("快递录入时间：");
        }else{
            tv1.setText("收件人  姓名：");
            tv3.setText("收件人门牌号：");
            tv5.setText("收件人手机号：");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dii_bt1:
                Log.d(TAG,"hhhhh");
                break;
        }
    }

    public void getinf(){
        final Person[] p = new Person[1];
        String phone = "";
        if(typee.equals("1")){
            phone = orders.getPoster();
        }
        else
            phone = orders.getGetuser();
        String url = "http://dwy.dwhhh.cn/zhy/api/getubp?up="+phone;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                JSONObject wonm = JSONObject.parseObject(json.getString("result"));
                String userphone = wonm.getString("userphone");
                String username = wonm.getString("username");
                String userpass = wonm.getString("userpass");
                String usertype = wonm.getString("usertype");
                String useraddr = wonm.getString("useraddr");
                String areaid = wonm.getString("areaid");
                Log.d(TAG,userphone+username+userpass+usertype+useraddr+areaid);
                p[0] = new Person(userphone,username,userpass,usertype,useraddr,areaid);
                Log.d("TAG", p[0].toString());
                if (typee.equals("1")){
                    tv2.setText(p[0].getUsername());
                    tv4.setText(p[0].getUserphone());
                    tv6.setText(orders.getTimee());
                }else{
                    tv2.setText(p[0].getUsername());
                    tv4.setText(p[0].getUserphone());
                    tv6.setText(p[0].getUseraddr());
                }
            }
        });
    }
}
