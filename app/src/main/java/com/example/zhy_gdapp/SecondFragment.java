package com.example.zhy_gdapp;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zhy_gdapp.adapter.OrderAdapter;
import com.example.zhy_gdapp.adapter.OuterAdapter;
import com.example.zhy_gdapp.adapter.PersonAdapter;
import com.example.zhy_gdapp.beans.Outorder;
import com.example.zhy_gdapp.beans.Person;
import com.example.zhy_gdapp.dialog.Dialog_in;
import com.example.zhy_gdapp.dialog.Dialog_out;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SecondFragment extends Fragment {
    private String TAG = "SecondFragment";
    public List<Outorder> listod = new ArrayList<Outorder>();
    public List<Person> listp = new ArrayList<Person>();
    private ListView listView;
    OuterAdapter newop;
    PersonAdapter personAdapter;
    private Handler handler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.two_fragment,container,false);
        listView = messageLayout.findViewById(R.id.lv_two);
        String typee = SharePreUtils.getType(getActivity());
        Log.d(TAG,typee);
        if (typee.equals("0"))
        {
            listp = getPerson();
            personAdapter = new PersonAdapter(getActivity(),R.layout.list_item,listp);
            listView.setAdapter(personAdapter);
        }
        if(typee.equals("1") || typee.equals("2")){
            listod = getorders();
            newop = new OuterAdapter(getActivity(),R.layout.list_item,listod);
            listView.setAdapter(newop);
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    if(typee.equals("0")){
                        listp=getPerson();
                        handler.sendMessage(handler.obtainMessage(0,listp));
                    }
                    if(typee.equals("1") || typee.equals("2")) {
                        listod = getorders();
                        handler.sendMessage(handler.obtainMessage(1,listod));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        try{
            new Thread(runnable).start();
            handler = new Handler(){
                public void handleMessage(Message msg){
                    if(msg.what==0){
                        personAdapter.notifyDataSetChanged();
                    }
                    if (msg.what==1){
                        newop.notifyDataSetChanged();
                    }
                }
            };
        }catch (Exception e){
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newDialog(listod.get(position),typee);
            }
        });

        return messageLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public List<Outorder> getorders(){
        List<Outorder> oo = new ArrayList<Outorder>();
        String usertype = SharePreUtils.getType(getActivity());
        String phone = SharePreUtils.getPhone(getActivity());
        String areaid = SharePreUtils.getArea(getActivity());
        String whoo = "";
        String url="";
        if(usertype.equals("2"))
            url = "http:dwy.dwhhh.cn/zhy/api/gp?ai="+areaid;
        if(usertype.equals("1"))
            url = "http:dwy.dwhhh.cn/zhy/api/user_out?ph="+phone;
        Log.d(TAG,url);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(),"网络连接失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG,res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
//                    Looper.prepare();
//                    Toast.makeText(getActivity(),"邮件获取成功",Toast.LENGTH_LONG).show();
//                    Looper.loop();
                    Log.d(TAG,json.getString("result").getClass().toString());
                    JSONArray ret = json.getJSONArray("result");

                    for(int i=0;i<ret.size();i++){
                        String woc = ret.get(i).toString();
                        com.alibaba.fastjson.JSONObject wonm = JSONObject.parseObject(woc);
                        String id = wonm.getString("id");
                        String uname = wonm.getString("uname");
                        String uphone = wonm.getString("uphone");
                        String areaid = wonm.getString("areaid");
                        String uaddr = wonm.getString("uaddr");
                        String gname = wonm.getString("gname");
                        String gphone = wonm.getString("gphone");
                        String gaddr = wonm.getString("gaddr");
                        String time = wonm.getString("time");
                        String state = wonm.getString("state");
                        String poster = wonm.getString("poster");
                        Outorder od = new Outorder(id,uname,uphone,areaid,uaddr,gname,gphone,gaddr,time,state,poster);
                        oo.add(od);
                    }
//                    orderAdapter.notifyDataSetChanged();
                    Log.d(TAG,oo.get(0).toString());

                }
                else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),"快递获取失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
        return oo;
    }

    public List<Person> getPerson(){
        List<Person> dd = new ArrayList<Person>();
        String aid = SharePreUtils.getArea(getActivity());
        String url = "http://dwy.dwhhh.cn/zhy/api/gper?tp=2&aid="+aid;
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getActivity(),"网络连接失败",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("firstfragment-------res",res);
                com.alibaba.fastjson.JSONObject json = JSONObject.parseObject(res);
                if(json.getString("result").length()>5){
//                    Looper.prepare();
//                    Toast.makeText(getActivity(),"邮件获取成功",Toast.LENGTH_LONG).show();
//                    Looper.loop();
                    Log.d("firstFragemnt--res",json.getString("result").getClass().toString());
                    JSONArray ret = json.getJSONArray("result");

                    for(int i=0;i<ret.size();i++){
                        String woc = ret.get(i).toString();
                        com.alibaba.fastjson.JSONObject wonm = JSONObject.parseObject(woc);
                        String userphone = wonm.getString("userphone");
                        String username = wonm.getString("username");
                        String userpass = wonm.getString("userpass");
                        String usertype = wonm.getString("usertype");
                        String useraddr = wonm.getString("useraddr");
                        String areaid = wonm.getString("areaid");
                        Person od = new Person(userphone,username,userpass,usertype,useraddr,areaid);
                        Log.d(TAG,od.toString());
                        dd.add(od);
                    }
//                    orderAdapter.notifyDataSetChanged();
                    Log.d("firstfragment",dd.get(0).toString());

                }
                else{
                    Looper.prepare();
                    Toast.makeText(getActivity(),"用户获取失败",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

            }
        });
        return  dd;
    }

    Dialog_out dialog_out;
    private void  newDialog(Outorder od,String typeee){
        dialog_out = new Dialog_out(od,typeee);
        final  View view = dialog_out.getView();
        dialog_out.show(getChildFragmentManager(),"dialog_out");
    }
}
