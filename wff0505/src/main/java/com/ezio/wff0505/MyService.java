package com.ezio.wff0505;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    Thread mThread;
    Boolean mBoolean;
    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mThread = new Thread() {
            @Override
            public void run() {
                while (mBoolean) {
                    try {
                        Log.e("run: ",System.currentTimeMillis()+"" );
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mBoolean = true;
        mThread.start();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBoolean = false;
        mThread.interrupt();
    }
}
