package com.yuzeduan.util;

import android.app.Application;
import android.content.Context;

/**
 * 获得全局的context对象
 * Created by YZD on 2018/5/18.
 */

public class OneApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
