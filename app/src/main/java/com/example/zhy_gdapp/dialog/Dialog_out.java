package com.example.zhy_gdapp.dialog;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.beans.Outorder;
import com.example.zhy_gdapp.beans.Person;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class Dialog_out extends DialogFragment implements View.OnClickListener{
    private final static String TAG = "Dialog_out";
    private TextView tv1,tv3,tv5,tv7,tv9,tv11;
    private TextView tv2,tv4,tv6,tv8,tv10,tv12;
    private EditText et_c;
    private Button bt_c,bt_g,bt_t;
    String typee;
    Outorder outorder;
    private Handler handler = null;

    @SuppressLint("ValidFragment")
    public Dialog_out(Outorder od, String typeee){
        super();
        outorder = od;
        typee = typeee;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_out,container);
        InitView(view);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    if(typee.equals("1"))
                        getposer();
                    if(typee.equals("2")){
                        tv2.setText(outorder.getUname());
                        tv4.setText(outorder.getUphone());
                        tv6.setText(outorder.getUaddr());
                        tv8.setText(outorder.getGname());
                        tv10.setText(outorder.getGphone());
                        tv12.setText(outorder.getGaddr());
                    }
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

        tv1 = v.findViewById(R.id.dio_tv1);
        tv3 = v.findViewById(R.id.dio_tv3);
        tv5 = v.findViewById(R.id.dio_tv5);
        tv7 = v.findViewById(R.id.dio_tv7);
        tv9 = v.findViewById(R.id.dio_tv9);
        tv11 = v.findViewById(R.id.dio_tv11);

        tv2 = v.findViewById(R.id.dio_tv2);
        tv4 = v.findViewById(R.id.dio_tv4);
        tv6 = v.findViewById(R.id.dio_tv6);
        tv8 = v.findViewById(R.id.dio_tv8);
        tv10 = v.findViewById(R.id.dio_tv10);
        tv12 = v.findViewById(R.id.dio_tv12);

        et_c = v.findViewById(R.id.dio_et1);
        bt_c = v.findViewById(R.id.dio_bt1);
        bt_g = v.findViewById(R.id.dio_bt2);
        bt_t = v.findViewById(R.id.dio_bt3);

        if(typee.equals("1")){
            et_c.setVisibility(View.VISIBLE);
            bt_c.setVisibility(View.VISIBLE);
            tv1.setText("收件人姓名：");
            tv3.setText("收件人地址：");
            tv5.setText("收件人手机：");
            tv7.setText("快递员姓名：");
            tv9.setText("快递员手机：");
            tv11.setText("快递运单号：");

            tv2.setText(outorder.getGname());
            tv4.setText(outorder.getGaddr());
            tv6.setText(outorder.getGphone());
        }else {
            String state = outorder.getState();
            if(state.equals("0"))
                bt_g.setVisibility(View.VISIBLE);
            if(state.equals("1")){
                bt_t.setVisibility(View.VISIBLE);
                et_c.setVisibility(View.VISIBLE);
            }
            tv1.setText("发件人姓名：");
            tv3.setText("发件人手机：");
            tv5.setText("发件人地址：");
            tv7.setText("收件人姓名：");
            tv9.setText("收件人手机：");
            tv11.setText("收件人地址：");

        }

        bt_c.setOnClickListener(this);
        bt_g.setOnClickListener(this);
        bt_t.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dio_bt1:
            {
                String state = outorder.getState();
                if(state.equals("0")){
                    Toast.makeText(getActivity(),"该快件未被获取",Toast.LENGTH_LONG).show();
                }
                else
                    comment();

            }
                break;
            case R.id.dio_bt2:
                getit();
                break;

            case R.id.dio_bt3:
                tkuidid();
                break;
        }
    }

    public void getposer(){
        Outorder od;
        String id = outorder.getId();
        String url = "http://dwy.dwhhh.cn/zhy/api/ugp?id="+id;
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
                String name = wonm.getString("name");
                String phone = wonm.getString("phone");
                String kuaidid = wonm.getString("kuaidid");

                tv8.setText(name);
                tv10.setText(phone);
                tv12.setText(kuaidid);

            }
        });
    }

    public void comment(){
        String id = outorder.getId();
        String up = outorder.getUphone();
        String pp = outorder.getPoster();
        String co = et_c.getText().toString();
        String url = "http://dwy.dwhhh.cn/zhy/api/uc?id="+id+"&up="+up+"&pp="+pp+"&co="+co;
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
                Log.d("res---------add",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){

                    Looper.prepare();
                    Toast.makeText(getActivity(),"评论成功",Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
                else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),"评论失败，您已经评论过",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    public void getit(){
        String oit = outorder.getId();
        String pho = SharePreUtils.getPhone(getActivity());
        String url = "http://dwy.dwhhh.cn/zhy/api/getoit?id="+oit+"&ph="+pho;
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
                Log.d("res---------add",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){

                    Looper.prepare();
                    Toast.makeText(getActivity(),"提取寄件成功",Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
                else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),"提取寄件失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    public void tkuidid(){
        String id = outorder.getId();
        String kid = et_c.getText().toString();
        String url = "http://dwy.dwhhh.cn/zhy/api/ansi?id="+id+"&kid="+kid;
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
                Log.d("res---------add",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){

                    Looper.prepare();
                    Toast.makeText(getActivity(),"发件成功",Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
                else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),"发件失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });

    }
}
