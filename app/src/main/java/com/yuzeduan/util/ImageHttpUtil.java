package com.yuzeduan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 从网络上获取图片并缓存,以及通过回调将图片进行展示
 * Created by YZD on 2018/5/26.
 */
public class ImageHttpUtil {
    private static LruCacheUtil mLruCacheUtil = new LruCacheUtil();

    /**
     * 从网络上下载图片,并缓存到LruCache中
     * @param image_path 表示下载图片的地址,同时也是LruCache中的键
     * @param callBack 用于展示图片的回调接口
     */
    public static void loadImage(final String image_path, final ImageCallback callBack) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bitmap bitmap = (Bitmap) msg.obj;
                callBack.getBitmap(bitmap);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(image_path);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                    mLruCacheUtil.addBitmapToCache(image_path, bitmap);  // 用LruCache缓存Bitmap对象
                    CacheUtil.saveBitmap(image_path ,bitmap);  // 用SharedPreferences缓存Bitmap
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
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

    /**
     * 加载图片到界面时进行判断是否存在一级缓存,若有,则调用一级缓存,
     * 若无一级缓存,则判断是否存在二级缓存,若有,则调用二级缓存,若无,则从网络中获取
     * @param image_path 表示下载图片的地址,同时也是LruCache中的键
     * @param callBack 用于展示图片的回调接口
     */
    public static void setImage(String image_path, ImageCallback callBack){
        Bitmap bitmap;
        if((bitmap = mLruCacheUtil.getBitmapFromCache(image_path)) != null){
            callBack.getBitmap(bitmap);
        }
        else if((bitmap = CacheUtil.isLoadBitmap(image_path)) != null){
            callBack.getBitmap(bitmap);
        }
        else {
            loadImage(image_path, callBack);
        }
    }
}
