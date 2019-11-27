package com.example.zhy_gdapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zhy_gdapp.utils.SharePreUtils;

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
        String na = SharePreUtils.getPhone(Poster_sendActivity.this);
        String id = et_id.getText().toString();
        String url = "http://dwy.dwhhh.cn/zhy/api/ps?ph="+ph+"&na="+na+"&id="+id;
        Log.d(TAG,url);
    }
}
