package com.example.zhy_gdapp;

import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class User_sendActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG="User_sendActivity";
    private Button bt_exit,bt_send;
    private EditText et_na,et_ph,et_ad;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.user_send);
        InitView();

    }

    public void InitView(){
        bt_exit = findViewById(R.id.us_bt1);
        bt_send = findViewById(R.id.us_bt2);
        et_na = findViewById(R.id.us_et1);
        et_ph = findViewById(R.id.us_et2);
        et_ad = findViewById(R.id.us_et3);

        bt_exit.setOnClickListener(this);
        bt_send.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.us_bt1:
                finish();
                break;
            case R.id.us_bt2:
                send();
                break;
        }
    }

    public void send(){
        String a_ph = SharePreUtils.getPhone(User_sendActivity.this);
        String b_na = et_na.getText().toString();
        String b_ph = et_ph.getText().toString();
        String b_ad = et_ad.getText().toString();
        String url = "http://dwy.dwhhh.cn/zhy/api/user_send?up="+a_ph+"&gn="+b_na+"&gh="+b_ph+"&ga="+b_ad;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(User_sendActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG,res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(User_sendActivity.this,"添加成功",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(User_sendActivity.this,"添加失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
