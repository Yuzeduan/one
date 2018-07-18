package com.yuzeduan.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.yuzeduan.model.ListDataModel;

import static com.yuzeduan.bean.Constant.INSET;
import static com.yuzeduan.bean.Constant.NEW_INSETID_URL;
import static com.yuzeduan.bean.Constant.MOVIE;
import static com.yuzeduan.bean.Constant.NEW_MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.MUSIC;
import static com.yuzeduan.bean.Constant.NEW_MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.READING;
import static com.yuzeduan.bean.Constant.NEW_READINGLIST_URL;

public class AutoUpdateService extends Service {
    private ListDataModel listDataModel = new ListDataModel();

    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        listDataModel.queryDataFromServer(NEW_READINGLIST_URL, READING, null);
        listDataModel.queryDataFromServer(NEW_MUSICLIST_URL, MUSIC, null);
        listDataModel.queryDataFromServer(NEW_MOVIELIST_URL, MOVIE, null);
        listDataModel.queryDataFromServer(NEW_INSETID_URL, INSET, null);
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 24 * 60 * 60 * 1000; // 一天的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }
}
