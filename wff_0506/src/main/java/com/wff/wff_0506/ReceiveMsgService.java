package com.wff.wff_0506;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 王芳芳
 *         时间：2016/5/7
 */
public class ReceiveMsgService extends Service {
    /**
     * 需要在Activity中得到网络状态，就是需要在接收到网络状态改变的广播的时候，要能够与Activity进行交互，
     * 通知Activity当前的网络状态，这就需要写一个Service，并且绑定到Activity，
     * 把广播监听到的实时的网络状态返回给Activity。
     */


    private Binder binder = new MyBinder();
    private boolean isContected = false;

    // 实时监听网络状态改变
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Timer timer = new Timer();
                timer.schedule(new QunXTask(getApplicationContext()), new Date());
            }
        }
    };

    private GetConnectState onGetConnectState;

    public interface GetConnectState {
        // 网络状态改变之后，通过此接口的实例通知当前网络的状态，此接口在Activity中注入实例对象
        public void GetState(boolean isConnected);
    }

    public void setOnGetConnectState(GetConnectState onGetConnectState) {
        this.onGetConnectState = onGetConnectState;
    }

    class QunXTask extends TimerTask {
        private Context context;

        public QunXTask(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            //当有网络连接的时候
            if (isNetworkConnected(context)) {
                isContected = true;
            } else {
                isContected = false;
            }
            if (onGetConnectState != null) {
                onGetConnectState.GetState(isContected);
                Log.i("mylog", "通知网络状态改变:" + isContected);
            }
        }

        //判断是否有网络连接
        private boolean isNetworkConnected(Context context) {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
            return false;
        }

    }
    //android中四大组件禁止使用构造器初始化,所以这里提供一个共有的方法用来获取当前组件的对象
    public class MyBinder extends Binder {
        public ReceiveMsgService getService() {
            return ReceiveMsgService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        // 注册广播
        IntentFilter mFilter = new IntentFilter();

        // 添加接收网络连接状态改变的Action
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver); // 取消注册
        }

    }
}
