package com.example.zhy_gdapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.adminac.ChangepassActivity;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_for,tv_reg;
    private EditText ed_us,ed_pa;
    private Button bt_log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        InitView();
    }

    private void InitView(){
        tv_for = findViewById(R.id.lg_tv1);
        tv_reg = findViewById(R.id.lg_tv2);
        ed_us = findViewById(R.id.ed_user);
        ed_pa = findViewById(R.id.ed_pas);
        bt_log = findViewById(R.id.lg_bt1);
        tv_for.setOnClickListener(this);
        tv_reg.setOnClickListener(this);
        bt_log.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lg_bt1:
                login();
                break;
            case R.id.lg_tv2:
                startActivity(new Intent(LoginActivity.this,RegsiterActivity.class));
                break;
            case R.id.lg_tv1:
                startActivity(new Intent(LoginActivity.this, ChangepassActivity.class));
                break;
        }
    }

    private void login(){
        String user = ed_us.getText().toString();
        String pas = ed_pa.getText().toString();
        String url = "http://dwy.dwhhh.cn/zhy/api/login?name="+user+"&passw="+pas;
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        }
                    });
                    JSONObject ret = JSONObject.parseObject(json.getString("result"));
                    Log.d("++++++++++++++++++",ret.getString("type"));
                    SharePreUtils.setAddr(LoginActivity.this,ret.getString("addr"));
                    SharePreUtils.setName(LoginActivity.this,ret.getString("name"));
                    SharePreUtils.setPass(LoginActivity.this,ret.getString("pass"));
                    SharePreUtils.setType(LoginActivity.this,ret.getString("type"));
                    SharePreUtils.setArea(LoginActivity.this,ret.getString("area"));
                    SharePreUtils.setPhone(LoginActivity.this,user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this,"登录失败,检查账户密码",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });

    }
}
