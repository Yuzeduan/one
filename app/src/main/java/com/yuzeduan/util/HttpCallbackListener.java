package com.yuzeduan.util;

/**
 * Created by YZD on 2018/5/18.
 */

/**
 * 进行网络获取时候的回调接口,通过具体的实现类,可实现获取网络的信息的回调进行处理
 */
public interface HttpCallbackListener {
    void onFinish(String response);
}
