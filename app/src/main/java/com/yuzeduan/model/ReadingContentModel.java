package com.yuzeduan.model;

import com.yuzeduan.bean.Reading;
import com.yuzeduan.db.ReadingDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;

public class ReadingContentModel {

    public void getReadingData(String itemId, String url, ReadingContentCallback callback){
        ReadingDao readingDao = new ReadingDao();
        Reading reading = readingDao.findReading(itemId);
        if(reading != null){
            callback.onFinish(reading);
        }else{
            queryReadingData(itemId, url, callback);
        }
    }

    public void queryReadingData(final String itemId, final String url, final ReadingContentCallback callback){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseReading(response);
                getReadingData(itemId, url, callback);
            }
        });
    }
}
