package com.wff.dynamicrecever;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Myrecever mMyrecever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyrecever =new Myrecever();
        register();

    }

    private void register() {
        IntentFilter mFilter=new IntentFilter();
        mFilter.addAction("wang.fang.fang");
        registerReceiver(mMyrecever,mFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    private void unregister() {
        if(mMyrecever !=null){
            unregisterReceiver(mMyrecever);
        }
    }
}
