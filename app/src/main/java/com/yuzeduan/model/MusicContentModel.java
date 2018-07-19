package com.yuzeduan.model;

import com.yuzeduan.bean.Music;
import com.yuzeduan.db.MusicDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;

public class MusicContentModel {

    public void getMusicData(String itemId, String url, MusicContentCallback callback){
        MusicDao musicDao = new MusicDao();
        Music music = musicDao.findMusic(itemId);
        if(music != null){
            callback.onFinish(music);
        }else{
            queryMusicData(itemId, url, callback);
        }
    }

    public void queryMusicData(final String itemId, final String url, final MusicContentCallback callback){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseMusic(response);
                getMusicData(itemId, url, callback);
            }
        });
    }
}
