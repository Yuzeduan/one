package com.yuzeduan.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.yuzeduan.Fragment.InsetFragment;
import com.yuzeduan.Fragment.MovieFragment;
import com.yuzeduan.Fragment.MusicFragment;
import com.yuzeduan.Fragment.ReadingFragment;
import com.yuzeduan.adapter.FragAdapter;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.db.ReadingListDao;
import com.yuzeduan.service.AutoUpdateService;
import com.yuzeduan.service.DownloadService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.yuzeduan.bean.Constant.NEW_READINGLIST_URL;

/**
 * app主界面,包含了阅读,音乐,影视,插画四个类型的轮播图
 */
public class MainActivity extends AppCompatActivity {
    private List<Fragment> mFragments;
    private List<ReadingMusicList> mReadingList;
    private DownloadService.DownloadBinder downloadBinder;
    private PagerTabStrip mPagerTabStrip;
    private List<String> mTitleList;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_vp);
        mPagerTabStrip = findViewById(R.id.pager_tab);
        mPagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);   //设置滚动标题字大小
        mFragments =  new ArrayList<>();
        mFragments.add(new ReadingFragment());
        mFragments.add(new MusicFragment());
        mFragments.add(new MovieFragment());
        mFragments.add(new InsetFragment());
        mTitleList = new ArrayList<>();
        mTitleList.add("阅读");
        mTitleList.add("音乐");
        mTitleList.add("影视");
        mTitleList.add("插画");
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        viewPager.setAdapter(adapter);
        final Intent downloadIntent= new Intent(this, DownloadService.class);
        startService(downloadIntent);
        bindService(downloadIntent, connection, BIND_AUTO_CREATE);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(downloadBinder == null){
                    return;
                }
                else{
                    downloadBinder.startDownload(NEW_READINGLIST_URL);
                }
            }
        });
        sendNotification();
        // 开启后台定时刷新数据
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, DownloadService.class);
        unbindService(connection);
        stopService(intent);
    }

    public void sendNotification() {
        ReadingListDao musicListDao = new ReadingListDao();
        mReadingList = musicListDao.findReadingList();
        Iterator<ReadingMusicList> it = mReadingList.iterator();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        ReadingMusicList reading = it.next();
        NotificationManager manger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(reading.getmTitle())
                .setContentText(reading.getmForword())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.one)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        manger.notify(2, notification);
    }
}