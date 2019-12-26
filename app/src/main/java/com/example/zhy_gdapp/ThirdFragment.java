package com.example.zhy_gdapp;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.zhy_gdapp.adminac.AddposterActivity;
import com.example.zhy_gdapp.adminac.ChangepassActivity;
import com.example.zhy_gdapp.utils.SharePreUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ThirdFragment extends Fragment implements View.OnClickListener{
    private String TAG = "ThirdFragment";
    TextView tv_name;
    ProgressBar pb;
    Button bt_post,bt_send,bt_add,bt_change,bt_exit,bt_outs,bt_up;
    private static final int EXTERNAL_STORAGE_REQ_CODE = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View messageLayout = inflater.inflate(R.layout.three_fragment,container,false);
        initView(messageLayout);
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_REQ_CODE);
        }
        return messageLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void initView(View v){

        tv_name = (TextView) v.findViewById(R.id.thfr_tv_name);
        pb = (ProgressBar) v.findViewById(R.id.yi_pb);

        bt_up = (Button) v.findViewById(R.id.yi_btup);
        bt_up.setOnClickListener(this);


        bt_post = (Button) v.findViewById(R.id.thfr_tv_post);
        bt_send = (Button) v.findViewById(R.id.thfr_bt_send);
        bt_add = (Button) v.findViewById(R.id.thfr_bt_two);
        bt_outs = (Button) v.findViewById(R.id.thfr_bt_outs);

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
            bt_outs.setVisibility(View.VISIBLE);
        }

        bt_change = (Button) v.findViewById(R.id.thfr_bt_th);
        bt_exit = (Button) v.findViewById(R.id.thfr_bt_exit);

        bt_outs.setOnClickListener(this);
        bt_post.setOnClickListener(this);
        bt_send.setOnClickListener(this);
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
            case R.id.thfr_tv_post:
                startActivity(new Intent(getActivity(),User_sendActivity.class));
                break;
            case R.id.thfr_bt_send:
                startActivity(new Intent(getActivity(),Poster_sendActivity.class));
                break;
            case R.id.thfr_bt_exit:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
            case R.id.thfr_bt_two:
                startActivity(new Intent(getActivity(), AddposterActivity.class));
                break;
            case R.id.thfr_bt_th:
                startActivity(new Intent(getActivity(), ChangepassActivity.class));
                break;
            case R.id.thfr_bt_outs:
                startActivity(new Intent(getActivity(),PoseroutActivity.class));
                break;
            case R.id.yi_btup:
            {
                pb.setVisibility(View.VISIBLE);
                updateApk("http://file.dwhhh.cn/zhyapp.apk");
            }
            break;
        }
    }


    String mypath;
    private void updateApk(String url) {
        Log.d("update-url",url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long allsize = response.body().contentLength();
                InputStream is = response.body().byteStream();
                int len = 0;
                long sum = 0;
                File file = new File(Environment.getExternalStorageDirectory(), "zhy.apk");
                mypath = file.getAbsolutePath();
                Log.d("update-apk","-==========");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                while((len = is.read(buf))!= -1) {
                    fos.write(buf,0,len);
                    sum += len;
                    int progress = (int) (sum * 1.0f / allsize * 100);
                    pb.setProgress(progress);
                }
                fos.flush();
                fos.close();
                is.close();
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        installApk(getActivity(), mypath);
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException ex) {

            }
        });
    }

    private void installApk(Context context, String apkpath) {
//        try{
        Uri uri  =null;
        File file = new File(apkpath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {//7.0 Android N

            //com.xxx.xxx.fileprovider为上述manifest中provider所配置相同

            uri = FileProvider.getUriForFile(context, "com.example.zhy_gdapp.fileprovider", new File(apkpath));



            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//7.0以后，系统要求授予临时uri读取权限，安装完毕以后，系统会自动收回权限，该过程没有用户交互

        } else {//7.0以下

            uri = Uri.fromFile(new File(apkpath));

            intent.setAction(Intent.ACTION_VIEW);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
//            if(Build.VERSION.SDK_INT >= (Build.VERSION_CODES.LOLLIPOP +3)) {
//            Toast.makeText(Yourself.this, "Android N", Toast.LENGTH_LONG).show();
//            }else {
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//            }
        context.startActivity(intent);
//        }catch (Exception e) {
//            Toast.makeText(context, "failed file parsing", Toast.LENGTH_LONG).show();
//        }
    }
}