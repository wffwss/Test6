package com.ezio.wff0505;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ezio.eziotask.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startService(View view) {
        startService(new Intent(MainActivity.this, MyService.class));
    }

    public void stopService(View view) {
        stopService(new Intent(MainActivity.this, MyService.class));
    }
}
