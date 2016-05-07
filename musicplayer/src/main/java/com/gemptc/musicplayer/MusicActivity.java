package com.gemptc.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gemptc.beans.Music;

import java.io.IOException;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REFRESHSEEKBAR = 1;
    //声明上下去切换按钮、播放暂停按钮、当前时间和总时间显示文本、进度条控件
    ImageButton mPreImageButton, mNextImageButton, mPlayAndPauseImageButton;
    TextView mCurrentTimeTextView, mTotleTimeTextView;
    SeekBar mSeekBar;

    Music mMusic;
    ArrayList<Music> mList;
    int mIndex;
    int mTotalTime;//音乐播放总时长

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initViews();
        addListeners();
        initData();
        resetMediaPlayer();
        play();
    }

    private void initData() {
        Intent intent = getIntent();
        //取得上一个界面传递过来的集合和索引
        mList = (ArrayList<Music>) intent.getSerializableExtra("list");
        mIndex = intent.getIntExtra("index",0);
        mMusic = mList.get(mIndex);
        //初始化媒体播放引擎
        mMediaPlayer = new MediaPlayer();
    }

    private void resetMediaPlayer() {

        //设置媒体播放引擎处于空闲状态
        mMediaPlayer.reset();
    }

    private void play() {
        //设置媒体文件路径来源，让媒体播放引擎处于初始化状态
        String url = mMusic.getUrl();
        try {
            mMediaPlayer.setDataSource(url);
            //设置媒体数据加载过程，让mediaplayer处于准备状态
            mMediaPlayer.prepare();
            //准备好后开始播放
            mMediaPlayer.start();
            //当音乐开始播放后，及时刷新进度条
            refreshSeekBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case REFRESHSEEKBAR:
                    //开始刷新seekbar
                    //计算seekbar的位置，当前播放时长除以总播放时长再乘以进度条的长度
                    int currentTime = mMediaPlayer.getCurrentPosition();
                    int totleTime = mMediaPlayer.getDuration();
                    int progress = currentTime*100/totleTime;
                    mSeekBar.setProgress(progress);
                    setTimes(currentTime,totleTime);

                    mHandler.sendEmptyMessageDelayed(REFRESHSEEKBAR,1000);
                    break;

            }
        }
    };
    public void setTimes(int currentTime,int totleTime){
        //得到总的秒数
        double totalTimeS=totleTime/1000.0;
        //得到总的分钟数
        int minute=(int)totalTimeS/60;
        int mm= (int) (totalTimeS%60);
        if(minute<10){
            if(mm<10){
                mTotleTimeTextView.setText("0"+minute+"分0"+mm+"秒");
            }else{
                mTotleTimeTextView.setText("0"+minute+"分"+mm+"秒");
            }
        }else{
            if(mm<10){
                mTotleTimeTextView.setText(minute+"分0"+mm+"秒");
            }else{
                mTotleTimeTextView.setText(minute+"分"+mm+"秒");
            }
        }


        //得到总的秒数
        double currentTimeS=currentTime/1000.0;
        //得到总的分钟数
        int minute2=(int)currentTimeS/60;
        int mm2= (int) (currentTimeS%60);


        if(minute2<10){
            if(mm2<10){
                mCurrentTimeTextView.setText("0"+minute2+"分0"+mm2+"秒");
            }else{
                mCurrentTimeTextView.setText("0"+minute2+"分"+mm2+"秒");
            }
        }else{
            if(mm2<10){
                mCurrentTimeTextView.setText(minute2+"分0"+mm2+"秒");
            }else{
                mCurrentTimeTextView.setText(minute2+"分"+mm2+"秒");
            }
        }

    }
    private void refreshSeekBar() {
        //开始播放音乐时，刷新进度条
        mHandler.sendEmptyMessage(REFRESHSEEKBAR);
    }

    private void initViews() {
        mPreImageButton = (ImageButton) findViewById(R.id.pre);
        mPlayAndPauseImageButton = (ImageButton) findViewById(R.id.play);
        mNextImageButton = (ImageButton) findViewById(R.id.next);
        mCurrentTimeTextView = (TextView) findViewById(R.id.currentTime);
        mTotleTimeTextView = (TextView) findViewById(R.id.totalTime);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
    }

    private void addListeners() {
        mPreImageButton.setOnClickListener(this);
        mPlayAndPauseImageButton.setOnClickListener(this);
        mNextImageButton.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //拖动结束以后跳转到对应位置开始播放
                //获取当前拖动位置，在0-100之间
                int currentProgress = seekBar.getProgress();
                //获得当前播放音乐总时长,单位ms
                mTotalTime = mMusic.getDuration();
                //计算音乐应该播放时长
                int currentTime = currentProgress*mTotalTime/100;
                //设置播放器在拖动的位置开始播放
                mMediaPlayer.seekTo(currentTime);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pre:
                //上一曲
                if (mIndex == 0){
                    show("已经是第一首歌曲");
                    return;
                }
                mIndex--;
                mMusic = mList.get(mIndex);
                resetMediaPlayer();
                play();
                break;
            case R.id.play:
                //播放和暂停切换
                if (mMediaPlayer != null  && mMediaPlayer.isPlaying()){
                    //播放转暂停
                    mMediaPlayer.pause();
                    //暂停的时候禁止刷新进度条
                    mHandler.removeMessages(REFRESHSEEKBAR);
                    mPlayAndPauseImageButton.setImageResource(R.drawable.ic_media_pause);
                } else {
                    //暂停转播放
                    mMediaPlayer.start();
                    mPlayAndPauseImageButton.setImageResource(R.drawable.ic_media_play);
                }
                break;
            case R.id.next:
                //下一曲
                if (mIndex == mList.size() - 1){
                    show("已经是最后首歌曲");
                    return;
                }
                mIndex++;
                mMusic = mList.get(mIndex);
                resetMediaPlayer();
                play();
                break;
        }
    }


    //释放媒体资源，结束播放

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null){
            mHandler.removeMessages(REFRESHSEEKBAR);
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void show(String text){
        Toast.makeText(MusicActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
