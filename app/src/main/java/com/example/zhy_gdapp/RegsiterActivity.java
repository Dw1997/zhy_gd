package com.example.zhy_gdapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegsiterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_back,bt_reg;
    private EditText et_phone,et_name,et_pass,et_addr,et_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_layout);
        initView();
    }

    @SuppressLint("WrongViewCast")
    private void initView(){
        bt_back = findViewById(R.id.re_bt1);
        bt_reg = findViewById(R.id.re_bt2);
        et_phone = findViewById(R.id.re_et1);
        et_name = findViewById(R.id.re_et2);
        et_pass = findViewById(R.id.re_et3);
        et_addr = findViewById(R.id.re_et4);
        et_area = findViewById(R.id.re_et5);

        bt_back.setOnClickListener(this);
        bt_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_bt1:
                finish();
                break;
            case R.id.re_bt2:
                reguser();
                break;
        }
    }

    private void reguser(){
        String phone = et_phone.getText().toString();
        String name = et_name.getText().toString();
        String pass = et_pass.getText().toString();
        String addr = et_addr.getText().toString();
        String area = et_area.getText().toString();
        if(phone.length()!=11){
            Toast.makeText(RegsiterActivity.this,"手机号为11位",Toast.LENGTH_LONG).show();
            return;
        }
        String url = "http://dwy.dwhhh.cn/zhy/api/reg?phone="+phone+"&name="+name+"&pass="+pass+"&addr="+addr+"&area="+area;
        Log.d("reurl",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegsiterActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegsiterActivity.this,"注册成功",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegsiterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
