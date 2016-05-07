package com.gemptc.musicplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gemptc.adapter.MyAdapter;
import com.gemptc.beans.Music;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mListView;

    ArrayList<Music> mList = new ArrayList<>();
    MyAdapter mAdapter;

    ContentResolver mResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        initListener();
    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //单击当前音乐，获取其音乐信息
                Music music = mList.get(position);
                //跳转到音乐播放界面
                Intent intent = new Intent(MainActivity.this,MusicActivity.class);
                intent.putExtra("list",mList);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.listview);
    }

    private void initData() {
        mResolver = getContentResolver();
        Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            Music music = new Music(artist,duration,id,title,url);
            mList.add(music);
        }

        mAdapter = new MyAdapter(MainActivity.this,mList);
        mListView.setAdapter(mAdapter);
    }

}
