package com.gemptc.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ConnectNetActivity extends AppCompatActivity {
    //获取系统服务，查看网络是否连接上
    ConnectivityManager mConnectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_net);
        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        if (info == null){
            show("网络不可用");
        } else {
            if (info.isAvailable()){
                show("网络可用");
            } else {
                show("网络不可用");
            }
        }

    }


    public void show(String text){
        Toast.makeText(ConnectNetActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
