package com.yuzeduan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YZD on 2018/5/18.
 */

public class HttpUtil {
    public static void sendRequestWithHttpURLConnection(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
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
                    CacheUtil.saveJSON(address, response.toString());  // 实现JSON数据的缓存
                    if (listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void getJSON(String address, HttpCallbackListener listener){
        String str;
        if((str = CacheUtil.isLoadJSON(address)) != null){
            listener.onFinish(str);
        }
        else{
            sendRequestWithHttpURLConnection(address,listener);
        }
    }
}

