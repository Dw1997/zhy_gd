package com.example.zhy_gdapp.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import com.example.zhy_gdapp.R;

public class Dialog_out extends DialogFragment {
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;
    private TextView tvl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_out,container);
        return view;
    }
}
