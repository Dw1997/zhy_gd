package com.example.zhy_gdapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhy_gdapp.adminac.AddposterActivity;
import com.example.zhy_gdapp.adminac.ChangepassActivity;
import com.example.zhy_gdapp.utils.SharePreUtils;


public class ThirdFragment extends Fragment implements View.OnClickListener{
    private String TAG = "ThirdFragment";
    TextView tv_name;
    Button bt_post,bt_send,bt_add,bt_change,bt_exit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.three_fragment,container,false);
        initView(messageLayout);
        return messageLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void initView(View v){

        tv_name = (TextView) v.findViewById(R.id.thfr_tv_name);

        bt_post = (Button) v.findViewById(R.id.thfr_tv_post);
        bt_send = (Button) v.findViewById(R.id.thfr_bt_send);
        bt_add = (Button) v.findViewById(R.id.thfr_bt_two);
        String type = SharePreUtils.getType(getActivity());
        Log.d("thirdfragment+++++",type);
        if(type.equals("0")){
            bt_add.setVisibility(View.VISIBLE);
        }
        if(type.equals("1")){
            bt_post.setVisibility(View.VISIBLE);
        }
        if(type.equals("2")){
            bt_send.setVisibility(View.VISIBLE);
        }

        bt_change = (Button) v.findViewById(R.id.thfr_bt_th);
        bt_exit = (Button) v.findViewById(R.id.thfr_bt_exit);

        bt_add.setOnClickListener(this);
        bt_change.setOnClickListener(this);
        bt_exit.setOnClickListener(this);

        String name = SharePreUtils.getname(getActivity());
        Log.d("---++----------",name);
        tv_name.setText(name);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.thfr_bt_exit:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
            case R.id.thfr_bt_two:
                startActivity(new Intent(getActivity(), AddposterActivity.class));
                break;
            case R.id.thfr_bt_th:
                startActivity(new Intent(getActivity(), ChangepassActivity.class));
        }
    }
}