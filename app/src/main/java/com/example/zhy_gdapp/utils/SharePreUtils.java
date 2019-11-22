package com.example.zhy_gdapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreUtils {
    static final String PRENAME = "zhy_spdata";

    public static String getname(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Name", "null");
    }

    public static void setName(Context context,String name) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Name", name);
        ed.commit();
    }

    public static String getPass(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Pass", "null");
    }
    public static void setPass(Context context,String pass) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Pass", pass);
        ed.commit();
    }

    public static void setAddr(Context context,String addr) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Addr", addr);
        ed.commit();
    }

    public static String getAddr(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Addr", "null");
    }

    public static void setType(Context context,String type) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Type", type);
        ed.commit();
    }

    public static String getType(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Type", "null");
    }

    public static void setArea(Context context,String area) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Area", area);
        ed.commit();
    }

    public static String getArea(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Area", "null");
    }

    public static void setPhone(Context context,String phone){
        SharedPreferences sp = context.getSharedPreferences(PRENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Phone",phone);
        ed.commit();
    }

    public static String getPhone(Context context){
        SharedPreferences sp = context.getSharedPreferences(PRENAME,Context.MODE_PRIVATE);
        return sp.getString("Phone",null);
    }


}
