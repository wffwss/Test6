package com.gemptc.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    MediaPlayer mMediaPlayer;
    MyBinder mBinder;

    //IBinder接口中抽象方法太多，使用其子类
    class MyBinder extends Binder{
        //android中四大组件禁止使用构造器初始化,所以这里提供一个共有的方法用来获取当前组件的对象
        public MyService getMyService(){
            return MyService.this;
        }
    }



    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //第一次启动，只会调用一次
        mMediaPlayer = MediaPlayer.create(MyService.this,R.raw.hero);
    }
    //startService启动服务生命周期只会调用onCreate-->onStartCommand-->onDestroy
    //onStartCommand每当调用一次startService就会调用一次此方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //服务已经启动
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }
   //bindService启动服务生命周期只会调用onCreate-->onBind-->onUnBind-->onDestroy
    //onBind方法只会调用一次
    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new MyBinder();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        //销毁服务
        super.onDestroy();
        if (mMediaPlayer != null){
            mMediaPlayer.release();
        }
    }
    //模拟服务下载文件进度
    public String getResult(){
        return "已经下载完成60%";
    }

}
