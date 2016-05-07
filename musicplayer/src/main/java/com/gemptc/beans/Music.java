package com.gemptc.beans;

import java.io.Serializable;

/**
 * Created by mhdong on 2016/5/6.
 */
public class Music implements Serializable {
    //以下属性分别表示歌曲的id，名称，演唱者，存储路径，总时长
    private int id;
    private String title;
    private String artist;
    private String url;
    private int duration;

    public Music(String artist, int duration, int id, String title, String url) {
        this.artist = artist;
        this.duration = duration;
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
