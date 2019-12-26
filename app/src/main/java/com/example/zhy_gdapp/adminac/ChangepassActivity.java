package com.example.zhy_gdapp.adminac;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.R;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangepassActivity extends Activity implements ViewGroup.OnClickListener {

    private Button bt_back,bt_change;
    private EditText et_phone,et_old,et_new;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_passw);
        InitView();
    }

    private void InitView(){
        bt_back = findViewById(R.id.cp_bt1);
        bt_change = findViewById(R.id.cp_bt2);

        bt_back.setOnClickListener(this);
        bt_change.setOnClickListener(this);

        et_phone = findViewById(R.id.cp_et1);
        et_old = findViewById(R.id.cp_et2);
        et_new = findViewById(R.id.cp_et3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cp_bt1:
                finish();
                break;
            case R.id.cp_bt2:
                changepass();
                break;
        }
    }

    private void changepass(){
        String phone = et_phone.getText().toString();
        String oldpass = et_old.getText().toString();
        String newpass = et_new.getText().toString();
        if(phone.length()!=11){
            Toast.makeText(ChangepassActivity.this,"手机号应为11位",Toast.LENGTH_LONG).show();
            return;
        }
        String url = "http://dwy.dwhhh.cn/zhy/api/changepass?phone="+phone+"&oldp="+oldpass+"&newp="+newpass;
        Log.d("changepass-------",url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangepassActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(ChangepassActivity.this,"更改密码成功",Toast.LENGTH_LONG).show();

                        }
                    });
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangepassActivity.this,"更改密码失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
