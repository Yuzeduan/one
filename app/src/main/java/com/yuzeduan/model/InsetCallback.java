package com.yuzeduan.model;

import com.yuzeduan.bean.Inset;

import java.util.List;

public interface InsetCallback {
    void onRefresh();
    void onFinish(List<Inset> list);
}
