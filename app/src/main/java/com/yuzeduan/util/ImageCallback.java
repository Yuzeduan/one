package com.yuzeduan.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 用于加载图片后,展示在界面的回调接口
 * Created by YZD on 2018/5/18.
 */

/**
 * 用于实现将获取的图片进行展示的回调接口,
 */
public interface ImageCallback {
    void getBitmap(Bitmap bitmap);
}
