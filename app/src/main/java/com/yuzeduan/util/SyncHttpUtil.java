package com.yuzeduan.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 暂时堵塞主线程,进行同步网络操作,获取网络数据,并且将数据进行本地缓存
 * Created by YZD on 2018/5/20.
 */

public class SyncHttpUtil {
    /**
     * 开启子线程,并且暂时堵塞主线程,进行网络操作,获取网络数据,并且将数据进行本地缓存
     * @param address 表示api地址
     * @return 返回从api中获取的JSON数据
     */
    public static String sendRequestWithHttpURLConnection(final String address){
        FutureTask<String> task = new FutureTask<>(new Callable<String>(){
            @Override
            public String call() throws Exception {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                CacheUtil.saveJSON(address, response.toString()); // 实现JSON数据的缓存
                return response.toString();
            }
        });
        new Thread(task).start();
        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 如果本地有缓存,则返回本地缓存的数据,否则,进行网络请求,将请求的数据作为返回值
     * @param address 表示api地址,以及SharePreferences的key
     * @return 返回本地缓存的JSON数据或者从网络请求获得的JSON数据
     */
    public static String getJSON(String address){
        String str;
        if((str = CacheUtil.isLoadJSON(address)) != null){
            return str;
        }
        else{
            str = sendRequestWithHttpURLConnection(address);
            return str;
        }
    }
}
