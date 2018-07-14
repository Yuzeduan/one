package com.yuzeduan.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.yuzeduan.activity.MainActivity;
import com.yuzeduan.activity.R;

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private DownloadBinder mBinder = new DownloadBinder();
    private DownloadListener listener = new DownloadListener(){
        @Override
        public void onProgress(int progress) {
            NotificationManager manger = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            manger.notify(1, getNotification("Downloading...", progress));
        }

        @Override
        public void onSuccess() {
            NotificationManager manger = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            downloadTask = null;
            stopForeground(true);
            manger.notify(1, getNotification("Download Success", -1));
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class DownloadBinder extends Binder{

        public void startDownload(String url){
            if(downloadTask == null){
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(url);
                startForeground(1, getNotification("Downloading...",0));
            }
        }
    }

    public Notification getNotification(String title, int progress){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.one);
        builder.setContentTitle(title);
        builder.setContentIntent(pi);
        if(progress > 0){
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        else{
            builder.setAutoCancel(true);
        }
        return builder.build();
    }
}
