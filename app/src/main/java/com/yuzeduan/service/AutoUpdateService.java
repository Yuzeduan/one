package com.yuzeduan.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.yuzeduan.bean.InsetId;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.HttpUtil;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.SyncHttpUtil;

import java.util.ArrayList;
import java.util.Iterator;

import static com.yuzeduan.activity.MainActivity.INSET;
import static com.yuzeduan.activity.MainActivity.MOVIE;
import static com.yuzeduan.activity.MainActivity.MUSIC;
import static com.yuzeduan.activity.MainActivity.READING;
import static com.yuzeduan.bean.Constant.INSETID_URL;
import static com.yuzeduan.bean.Constant.MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.READINGLIST_URL;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateDatas(READINGLIST_URL, READING);
        updateDatas(MUSICLIST_URL, MUSIC);
        updateDatas(MOVIELIST_URL, MOVIE);
        updateDatas(INSETID_URL, INSET);
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 24 * 60 * 60 * 1000; // 一天的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    public void updateDatas(String url, final int flag) {
        if (flag == INSET) {
            String idResponse = SyncHttpUtil.getJSON(url);
            ArrayList<InsetId> idList = ParseJSONUtil.parseInsetId(idResponse);
            Iterator<InsetId> it = idList.iterator();
            InsetId insetId;
            // 对存放了插画id对象的容器进行遍历,获取每个插画详细对象,并将其存放在容器中,传入适配器
            while (it.hasNext()) {
                insetId = it.next();
                String id = insetId.getmInsetId();
                String insetAddress = "http://v3.wufazhuce.com:8000/api/hp/detail/" + id + "?version=3.5.0&platform=android";
                String insetResponse = SyncHttpUtil.getJSON(insetAddress);
                ParseJSONUtil.parseInset(insetResponse);
            }
        } else {
            HttpUtil.getJSON(url, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    if (flag == READING) {
                        ParseJSONUtil.parseReadingMusicList(response, READING);
                    } else if (flag == MUSIC) {
                        ParseJSONUtil.parseReadingMusicList(response, MUSIC);
                    } else {
                        ParseJSONUtil.parseMovieList(response);
                    }
                }
            });
        }
    }
}
