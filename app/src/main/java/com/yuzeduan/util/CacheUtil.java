package com.yuzeduan.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 创建SharedPreferences用于存放从网络请求中获取的JSON数据和图片,以及判断是否有本地缓存
 * Created by YZD on 2018/5/22.
 */

public class CacheUtil {
    private static SharedPreferences JSONSharedPreferences = OneApplication.getContext().getSharedPreferences("JSONData", Context.MODE_APPEND);
    private static SharedPreferences.Editor JSONEditor = JSONSharedPreferences.edit();
    private static SharedPreferences imageSharedPreferences = OneApplication.getContext().getSharedPreferences("imageData", Context.MODE_APPEND);
    private static SharedPreferences.Editor imageEditor = imageSharedPreferences.edit();


    /**
     * 实现从api中获取的JSON数据的本地缓存
     * @param key 表示SharedPreferences的key
     * @param value 表示存在SharedPreferences的值
     */
    public static void saveJSON(String key, String value){
        JSONEditor.putString(key, value);
        JSONEditor.commit();
    }

    /**
     * 根据key判断是否存在本地缓存
     * @param address 表示本地缓存的key,即图片的获取地址
     * @return 如果本地缓存存在,则返回缓存的JSON数据,否则,返回null
     */
    public static String isLoadJSON(String address){
        String temp = JSONSharedPreferences.getString(address, "");
        if(!temp.equals("")){
            return temp;
        }
        else{
            return null;
        }
    }

    /**
     * 实现从网络上获取的图片的二级缓存,
     * @param key 表示imageSharedPreferences中的key
     * @param bitmap 表示要存储的Bitmap对象
     */
    public static void saveBitmap(String key, Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String imageBase64 = new String(Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT));
        // 将二进制数据转换成字符串
        imageEditor.putString(key, imageBase64);
        imageEditor.commit();
    }

    /**
     * 判断二级图片缓存中是否有缓存
     * @param address 表示imageSharedPreferences中的key.即图片获取的地址
     * @return 若有缓存则返回缓存中的Bitmap对象,若无,则返回null
     */
    public static Bitmap isLoadBitmap(String address) {
        String temp = imageSharedPreferences.getString(address, "");
        if (!temp.equals("")) {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
            Bitmap bitmap = BitmapFactory.decodeStream(bais);
            return bitmap;
        }
        return null;
    }
}
