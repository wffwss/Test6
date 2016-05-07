package com.wff.wff0506;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class NetworkStateService extends Service {
    private static final String mTag="netState";
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo mNetworkInfo;

    private BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                Log.d(mTag,"网络状态已经改变");
                mConnectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                mNetworkInfo=mConnectivityManager.getActiveNetworkInfo();
                if(mNetworkInfo!=null && mNetworkInfo.isAvailable()){
                    String name=mNetworkInfo.getTypeName();
                    Log.d(mTag,"当前网络名称"+name);
                }else {
                    Log.d(mTag,"没有可用网络");
                }
            }
        }
    };

    public NetworkStateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter=new IntentFilter();
        mFilter.addAction("fang.fang.wang");
        registerReceiver(mReceiver,mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
