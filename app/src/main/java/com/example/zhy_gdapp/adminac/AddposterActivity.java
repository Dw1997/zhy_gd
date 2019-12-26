package com.example.zhy_gdapp.adminac;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.R;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddposterActivity extends Activity implements View.OnClickListener{
    private Button bt_back,bt_add;
    private EditText et_phone,et_name,et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_poster);
        InitView();
    }

    private void InitView(){
        bt_back = findViewById(R.id.ap_bt1);
        bt_add = findViewById(R.id.ap_bt2);
        et_phone = findViewById(R.id.ap_et1);
        et_name = findViewById(R.id.ap_et2);
        et_pass = findViewById(R.id.ap_et3);

        bt_back.setOnClickListener(this);
        bt_add.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ap_bt1:
                finish();
                break;
            case R.id.ap_bt2:
                addposer();
                break;
        }
    }

    private void addposer(){
        String phone = et_phone.getText().toString();
        String name = et_name.getText().toString();
        String pass = et_pass.getText().toString();
        String area = SharePreUtils.getArea(AddposterActivity.this);
        String url = "http://dwy.dwhhh.cn/zhy/api/addposter?phone="+phone+"&name="+name+"&pass="+pass+"&area="+area;
        Log.d("reurl",url);
        if(phone.length()!=11){
            Toast.makeText(AddposterActivity.this,"手机号为11位",Toast.LENGTH_LONG).show();
            return;
        }
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddposterActivity.this,"网络连接失败",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("res---------add",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").equals("true")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddposterActivity.this,"添加成功",Toast.LENGTH_LONG).show();

                        }
                    });
//                    startActivity(new Intent(RegsiterActivity.this, MainActivity.class));
                }
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddposterActivity.this,"添加失败",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });

    }
}
