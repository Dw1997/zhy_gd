package com.example.zhy_gdapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.example.zhy_gdapp.utils.SharePreUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt1,bt2,bt3;
    FrameLayout mycontent;

    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
        setTabSelection(0);
    }

    private void InitView(){
        bt1 = findViewById(R.id.frg_one);
        bt1.setOnClickListener(this);
        bt2 = findViewById(R.id.frg_two);
        bt2.setOnClickListener(this);
        bt3 = findViewById(R.id.frg_tre);
        bt3.setOnClickListener(this);

        String type = SharePreUtils.getType(MainActivity.this);
        if(type.equals("0")){
            bt1.setText("用户");
            bt2.setText("快递员");
        }
        if(type.equals("1")){
            bt1.setText("取");
            bt2.setText("发");
        }
        if(type.equals("2")){
            bt1.setText("派");
            bt2.setText("收");
        }

        manager = getFragmentManager();
        mycontent = findViewById(R.id.mycontent);
    }

    private void setTabSelection(int page){
        FragmentTransaction trans = manager.beginTransaction();
        hideFragment(trans);
        switch (page){
            case 0:
                if(firstFragment==null){
                    firstFragment = new FirstFragment();
                    trans.add(R.id.mycontent,firstFragment);
                }else{
                    trans.show(firstFragment);
                }
                break;
            case 1:
                if(secondFragment==null){
                    secondFragment = new SecondFragment();
                    trans.add(R.id.mycontent,secondFragment);
                }else{
                    trans.show(secondFragment);
                }
                break;
            case 2:
                if (thirdFragment == null) {
                    thirdFragment = new ThirdFragment();
                    trans.add(R.id.mycontent,thirdFragment);
                }else{
                    trans.show(thirdFragment);
                }

        }
        trans.commit();

    }

    private void hideFragment(android.app.FragmentTransaction t) {
        if(firstFragment != null) {
            t.hide(firstFragment);
        }
        if(secondFragment != null) {
            t.hide(secondFragment);
        }
        if(thirdFragment != null) {
            t.hide(thirdFragment);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.frg_one:
                setTabSelection(0);
                break;
            case R.id.frg_two:
                setTabSelection(1);
                break;
            case R.id.frg_tre:
                setTabSelection(2);
                break;
        }
    }


}
