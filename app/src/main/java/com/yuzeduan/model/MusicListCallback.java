package com.yuzeduan.model;

import com.yuzeduan.bean.ReadingMusicList;

import java.util.List;

public interface MusicListCallback {
    void onRefresh();
    void onFinish(List<ReadingMusicList> list);
}
