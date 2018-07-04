package com.yuzeduan.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 实现图片缓存和从缓存中获取图片
 * Created by YZD on 2018/5/26.
 */

public class LruCacheUtil {
    private static LruCache<String, Bitmap> mCache;

    public LruCacheUtil(){
        //获取最大可用内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/ 1024);
        //设置缓存的大小
        int cacheSize = maxMemory / 8;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    /**
     * 将图片加入到缓存中
     * @param url 表示该Bitmap在LruCache中的键,即图片的下载路径
     * @param bitmap 表示要缓存的Bitmap对象
     */
    public void addBitmapToCache(String url, Bitmap bitmap){
        if (getBitmapFromCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }

    /**
     *从缓存中获取Bitmap
     * @param url 表示LruCache的键值,即图片的下载路径
     * @return 如果存在缓存则返回键对应的Bitmap对象,否则返回null
     */
    public Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }
}
