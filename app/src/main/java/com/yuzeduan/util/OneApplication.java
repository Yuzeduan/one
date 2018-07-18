package com.yuzeduan.util;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 获得全局的context对象
 * Created by YZD on 2018/5/18.
 */

public class OneApplication extends Application {
    private static Context context;
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        requestQueue = Volley.newRequestQueue(this);
    }

    public static Context getContext(){
        return context;
    }
}
