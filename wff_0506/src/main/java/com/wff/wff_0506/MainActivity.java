package com.wff.wff_0506;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected String TAG = "mylog";
    ReceiveMsgService receiveMsgService;
    private boolean conncetState = false; // 记录当前连接状态

    boolean isBind = false;//标志位，用来标注当前服务是否被绑定

    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
    }
    //绑定服务
    private void bind() {
        Intent intent = new Intent(MainActivity.this, ReceiveMsgService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    //解除绑定
    private void unbind() {
        if (isBind) {
            unbindService(serviceConnection);
            isBind = false;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //一个回调方法，一旦Service中onBind方法返回了结果，就回调此方法，第二个service就是onBind方法的返回值
            isBind = true;
            receiveMsgService = ((ReceiveMsgService.MyBinder) service)
                    .getService();
            receiveMsgService.setOnGetConnectState(new ReceiveMsgService.GetConnectState() { // 添加接口实例获取连接状态
                @Override
                public void GetState(boolean isConnected) {
                    if (conncetState != isConnected) { // 如果当前连接状态与广播服务返回的状态不同才进行通知显示
                        conncetState = isConnected;
                        if (conncetState) {// 已连接
                            handler.sendEmptyMessage(1);
                        } else {// 未连接
                            handler.sendEmptyMessage(2);
                        }
                    }
                }
            });


        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //意外情况发生，导致服务突然中断，回调的方法，例如服务抛出异常，或者系统杀死服务进程
        }
    };

    public void show(String text){
        if ( mToast!=null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:// 已连接
                    show("网络已经连接");
                    break;
                case 2:// 未连接
                    show("网络未连接");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbind();
    }


}
