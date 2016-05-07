package com.gemptc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gemptc.beans.Music;
import com.gemptc.musicplayer.R;

import java.util.List;

/**
 * Created by mhdong on 2016/5/6.
 */
public class MyAdapter extends BaseAdapter {
    List<Music> mList;
    Context mContext;
    LayoutInflater mInflater;

    public MyAdapter(Context context, List<Music> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.item,null);

        TextView mTitleTextView = (TextView) convertView.findViewById(R.id.title);
        TextView mArtistTextView = (TextView) convertView.findViewById(R.id.artist);

        Music music = mList.get(position);
        String title = music.getTitle();
        String artist = music.getArtist();

        mTitleTextView.setText(title);
        mArtistTextView.setText(artist);

        return convertView;
    }
}
