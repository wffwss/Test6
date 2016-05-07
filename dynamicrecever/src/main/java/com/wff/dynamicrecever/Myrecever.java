package com.wff.dynamicrecever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by wss on 2016/5/6.
 */
public class Myrecever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(action.equals("wang.fang.fang")){
            String name=intent.getStringExtra("myName");
            Log.e("broder","动态接收到的广播内容"+name);
        }

    }
}
