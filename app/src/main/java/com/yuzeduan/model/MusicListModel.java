package com.yuzeduan.model;

import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.db.MusicListDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;

import java.util.List;

import static com.yuzeduan.bean.Constant.MUSIC;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class MusicListModel {

    public void getMusicListData(String url, MusicListCallback callback){
        MusicListDao musicListDao = new MusicListDao();
        List<ReadingMusicList> musicList = musicListDao.findMusicList();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if(musicList != null){
            callback.onFinish(musicList);
        }else {
            queryMusicListData(url, 0, callback);
        }
    }

    public void queryMusicListData(final String url, final int flag, final MusicListCallback callback){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseReadingMusicList(response, MUSIC);
                if(callback != null){
                    if(flag == REFRESH_DATA){
                        callback.onRefresh();
                    }else {
                        getMusicListData(url, callback);
                    }
                }
            }
        });
    }
}
