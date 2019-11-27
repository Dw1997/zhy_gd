package com.example.zhy_gdapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zhy_gdapp.utils.SharePreUtils;

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
        String a_na = SharePreUtils.getname(User_sendActivity.this);
        String a_ph = SharePreUtils.getPhone(User_sendActivity.this);
        String b_na = et_na.getText().toString();
        String b_ph = et_ph.getText().toString();
        String b_ad = et_ad.getText().toString();
        String url = "http://dwy.dwhhh.cn/zhy/api/us?ana="+a_na+"&aph="+a_ph+"&bna="+b_na+"&bph"+b_ph+"&bad="+b_ad;
        Log.d(TAG,url);
    }
}
