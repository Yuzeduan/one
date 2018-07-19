package com.yuzeduan.model;

import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.db.ReadingListDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;

import java.util.List;

import static com.yuzeduan.bean.Constant.READING;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class ReadingListModel {

    public void getReadingListData(String url, ReadingListCallback callback){
        ReadingListDao readingListDao = new ReadingListDao();
        List<ReadingMusicList> readingList = readingListDao.findReadingList();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if(readingList != null){
            callback.onFinish(readingList);
        }
        else {
            queryReadingListData(url, 0, callback);
        }
    }

    public void queryReadingListData(final String url, final int flag, final ReadingListCallback callback){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseReadingMusicList(response, READING);
                if(callback != null){
                    if(flag == REFRESH_DATA){
                        callback.onRefresh();
                    } else {
                        getReadingListData(url, callback);
                    }
                }
            }
        });
    }
}
