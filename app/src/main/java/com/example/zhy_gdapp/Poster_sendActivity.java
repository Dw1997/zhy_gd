package com.example.zhy_gdapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Poster_sendActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "Poster_sendActivity";
    private Button bt_et,bt_ad;
    private EditText et_id;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.poster_send);
        InitView();
    }

    public void InitView(){
        bt_et = findViewById(R.id.ps_bt1);
        bt_ad = findViewById(R.id.ps_bt2);
        et_id = findViewById(R.id.ps_et1);

        bt_et.setOnClickListener(this);
        bt_ad.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ps_bt1:
                finish();
                break;
            case R.id.ps_bt2:
                posteradd();
                break;
        }
    }

    private void posteradd(){
        String ph = SharePreUtils.getPhone(Poster_sendActivity.this);
        String ad = et_id.getText().toString();
        String ai = SharePreUtils.getArea(Poster_sendActivity.this);
        String url = "http://dwy.dwhhh.cn/zhy/api/poster_send?ph="+ph+"&ad="+ad+"&ai="+ai;
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
                        Toast.makeText(Poster_sendActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
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
                            Toast.makeText(Poster_sendActivity.this,"添加成功",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Poster_sendActivity.this,"添加失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
    }
}
