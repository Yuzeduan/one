package com.yuzeduan.activity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuzeduan.adapter.InsetAdapter;
import com.yuzeduan.adapter.MovieAdapter;
import com.yuzeduan.adapter.MusicAdapter;
import com.yuzeduan.adapter.ReadingAdapter;
import com.yuzeduan.bean.Inset;
import com.yuzeduan.bean.InsetId;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.db.InsetDao;
import com.yuzeduan.db.MovieListDao;
import com.yuzeduan.db.MusicListDao;
import com.yuzeduan.db.ReadingListDao;
import com.yuzeduan.service.AutoUpdateService;
import com.yuzeduan.service.DownloadService;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.HttpUtil;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.SyncHttpUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.yuzeduan.bean.Constant.INSETID_URL;
import static com.yuzeduan.bean.Constant.MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_INSETID_URL;
import static com.yuzeduan.bean.Constant.NEW_MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.NEW_MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_READINGLIST_URL;
import static com.yuzeduan.bean.Constant.READINGLIST_URL;


/**
 * app主界面,包含了阅读,音乐,影视,插画四个类型的轮播图
 */
public class MainActivity extends AppCompatActivity {
    public static final int READING = 0;
    public static final int MUSIC = 1;
    public static final int MOVIE = 2;
    public static final int INSET = 3;
    private List<View> mViewList = new ArrayList<>();  // 用于存放View对象
    private ListView mLvReading, mLvMusic, mLvMovie, mLvInset;  // 用于展示列表的控件
    private ArrayList<ReadingMusicList> mReadingList, mMusicList;
    private ArrayList<MovieList> mMovieList;
    private ArrayList<Inset> mInsetList;
    private DownloadService.DownloadBinder downloadBinder;
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
        final ViewPager viewPager = (ViewPager) findViewById(R.id.main_vp);
        // 定义一个viewPager,用于不同类型的界面切换
        View lvReadingList = getLayoutInflater().inflate(R.layout.listview, null);
        View lvMusicList = getLayoutInflater().inflate(R.layout.listview, null);
        View lvMovieList = getLayoutInflater().inflate(R.layout.listview, null);
        View lvInsetList = getLayoutInflater().inflate(R.layout.listview, null);
        mLvReading = (ListView) lvReadingList.findViewById(R.id.main_lv_list);
        mLvMusic = (ListView) lvMusicList.findViewById(R.id.main_lv_list);
        mLvMovie = (ListView) lvMovieList.findViewById(R.id.main_lv_list);
        mLvInset = (ListView) lvInsetList.findViewById(R.id.main_lv_list);
        mViewList.add(lvReadingList);
        mViewList.add(lvMusicList);
        mViewList.add(lvMovieList);
        mViewList.add(lvInsetList);
        // 为viewPager设置适配器
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                //这个方法是返回总共有几个滑动的页面
                return mViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                //该方法判断是否由该对象生成界面。
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //这个方法返回一个对象，该对象表明PagerAdapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
                viewPager.addView(mViewList.get(position));
                return mViewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //这个方法从viewPager中移动当前的view,当我们划过的时候屏幕时候
                viewPager.removeView(mViewList.get(position));
            }
        });
        final Intent downloadIntent= new Intent(this, DownloadService.class);
        startService(downloadIntent);
        bindService(downloadIntent, connection, BIND_AUTO_CREATE);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
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
        setReadingListView();
        setMusicListView();
        setMovieListView();
        setInsetListView();
        refreshList(lvReadingList, READING);
        refreshList(lvMusicList, MUSIC);
        refreshList(lvMovieList, MOVIE);
        refreshList(lvInsetList, INSET);
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

    /**
     * 进行阅读列表的展示并对列表设置点击事件监听器
     */
    public void setReadingListView() {
        ReadingListDao musicListDao = new ReadingListDao();
        mReadingList = musicListDao.findReadingList();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if (mReadingList != null) {
            ReadingAdapter adapter = new ReadingAdapter(this, mReadingList, R.layout.reading_item);
            mLvReading.setAdapter(adapter);
            // 给列表设置点击事件监听器,获取子项的具体item_id,传给下一个活动
            mLvReading.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ReadingMusicList reading = mReadingList.get(position);
                    String item_id = reading.getmItemId();
                    Intent intent = new Intent(MainActivity.this, ReadingContentActivity.class);
                    intent.putExtra("id", item_id);
                    startActivity(intent);
                }
            });
        } else {
            queryFromServer(READINGLIST_URL, READING);
        }
    }

    /**
     * 进行音乐列表的展示并对列表设置点击事件监听器
     */
    public void setMusicListView() {
        MusicListDao musicListDao = new MusicListDao();
        mMusicList = musicListDao.findMusicList();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if (mMusicList != null) {
            MusicAdapter adapter = new MusicAdapter(this, mMusicList, R.layout.music_item);
            mLvMusic.setAdapter(adapter);
            // 给列表设置点击事件监听器,获取子项的具体item_id,传给下一个活动
            mLvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ReadingMusicList music = mMusicList.get(position);
                    String item_id = music.getmItemId();
                    Intent intent = new Intent(MainActivity.this, MusicContentActivity.class);
                    intent.putExtra("id", item_id);
                    startActivity(intent);
                }
            });
        } else {
            queryFromServer(MUSICLIST_URL, MUSIC);
        }
    }

    /**
     * 进行影视列表的展示并对列表设置点击事件监听器
     */
    public void setMovieListView() {
        MovieListDao movieListDao = new MovieListDao();
        mMovieList = movieListDao.findMovieList();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if (mMovieList != null) {
            MovieAdapter adapter = new MovieAdapter(this, mMovieList, R.layout.movie_item);
            mLvMovie.setAdapter(adapter);
            // 给列表设置点击事件监听器,获取子项的具体item_id,传给下一个活动
            mLvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MovieList movie = mMovieList.get(position);
                    String item_id = movie.getmItemId();
                    Intent intent = new Intent(MainActivity.this, MovieContentActivity.class);
                    intent.putExtra("id", item_id);
                    startActivity(intent);
                }
            });
        } else {
            queryFromServer(MOVIELIST_URL, MOVIE);
        }
    }

    /**
     * 进行插画列表的展示
     */
    public void setInsetListView() {
        InsetDao insetDao = new InsetDao();
        mInsetList = insetDao.findInset();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if (mInsetList != null) {
            InsetAdapter adapter = new InsetAdapter(this, mInsetList, R.layout.inset_item);
            mLvInset.setAdapter(adapter);
        } else {
            queryFromServer(INSETID_URL, INSET);
        }
    }

    /**
     * 从服务器中获取数据
     *
     * @param address 表示用于获取数据的api地址
     * @param flag    表示用于判断阅读,影视,音乐,插画对象的标志
     */
    public void queryFromServer(String address, final int flag) {
        final Handler handler = new Handler();
        if (flag == INSET) {
            String idResponse = SyncHttpUtil.getJSON(address);
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
            setInsetListView();
        } else {
            HttpUtil.getJSON(address, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    if (flag == READING) {
                        ParseJSONUtil.parseReadingMusicList(response, READING);
                    } else if (flag == MUSIC) {
                        ParseJSONUtil.parseReadingMusicList(response, MUSIC);
                    } else {
                        ParseJSONUtil.parseMovieList(response);
                    }
                    // 获取数据后重新调用展示数据的方法,此时数据库中已有缓存
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (flag == READING) {
                                setReadingListView();
                            } else if (flag == MUSIC) {
                                setMusicListView();
                            } else {
                                setMovieListView();
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * 对列表进行刷新事件的监听,并刷新列表
     *
     * @param view 表示加载布局所得的View对象
     * @param flag 表示用于判断阅读,影视,音乐,插画对象的标志
     */
    public void refreshList(View view, final int flag) {
        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 调用从服务器获取数据的方法,更新数据,再进行展示
                if (flag == READING) {
                    queryFromServer(NEW_READINGLIST_URL, READING);
                } else if (flag == MUSIC) {
                    queryFromServer(NEW_MUSICLIST_URL, MUSIC);
                } else if (flag == MOVIE) {
                    queryFromServer(NEW_MOVIELIST_URL, MOVIE);
                } else {
                    queryFromServer(NEW_INSETID_URL, INSET);
                }
                swipeRefresh.setRefreshing(false);
            }
        });
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