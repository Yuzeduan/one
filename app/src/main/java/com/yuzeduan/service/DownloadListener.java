package com.yuzeduan.service;

/**
 * 用于下载过程中状态的监听和回调
 */
public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
}
