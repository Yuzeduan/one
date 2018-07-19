package com.yuzeduan.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.yuzeduan.model.InsetModel;
import com.yuzeduan.model.MovieListModel;
import com.yuzeduan.model.MusicListModel;
import com.yuzeduan.model.ReadingListModel;

import static com.yuzeduan.bean.Constant.NEW_INSETID_URL;
import static com.yuzeduan.bean.Constant.NEW_MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.NEW_MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_READINGLIST_URL;

public class AutoUpdateService extends Service {

    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new ReadingListModel().queryReadingListData(NEW_READINGLIST_URL, 0, null);
        new MusicListModel().queryMusicListData(NEW_MUSICLIST_URL, 0, null);
        new MovieListModel().queryMovieListData(NEW_MOVIELIST_URL, 0, null);
        new InsetModel().queryInsetListData(NEW_INSETID_URL, 0, null);
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour = 24 * 60 * 60 * 1000; // 一天的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }
}
