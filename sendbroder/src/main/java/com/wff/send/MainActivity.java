package com.wff.send;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void send(View view) {
        Intent intent=new Intent();
        intent.setAction("fang.fang.wang");
     //   intent.setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);挂后台接收不到消息
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("myName","王芳芳");
        startService(intent);

    }
}
