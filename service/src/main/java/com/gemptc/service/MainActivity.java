package com.gemptc.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    MyService.MyBinder mBinder;
    boolean isBind = false;//标志位，用来标注当前服务是否被绑定
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;//说明服务已经被绑定
            //一个回调方法，一旦Service中onBind方法返回了结果，就回调此方法，第二个service就是onBind方法的返回值
            mBinder = (MyService.MyBinder) service;
            //通过MyBinder内部类中的共有方法获取服务的一个对象，进而可以获取服务的属性和方法
            MyService myService = mBinder.getMyService();
            //获取到服务下载进度
            String result = myService.getResult();
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //意外情况发生，导致服务突然中断，回调的方法，例如服务抛出异常，或者系统杀死服务进程
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View view) {
        //启动服务：服务本身不能自启动，需要由其他组件启动
        intent = new Intent(MainActivity.this,MyService.class);
        startService(intent);
    }

    public void stopService(View view) {
        stopService(new Intent(MainActivity.this,MyService.class) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void bindService(View view) {
        //绑定服务：第一个参数，意图，第二个参数，用于获取服务返回值IBinder对象，通过此对象，不能为null
        //第三个是个标志位，一般用Service.BIND_AUTO_CREATE,用途是服务没有创建时自动创建服务
        intent = new Intent(MainActivity.this,MyService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    public void unBindService(View view) {
        //解除绑定,如果服务已经被解除绑定，则不可以再次解除绑定
        if (isBind){
            //只有服务在绑定的时候才解除绑定
            unbindService(conn);
            isBind = false;
        }

    }
}
