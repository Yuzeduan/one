package com.yuzeduan.model;

import com.yuzeduan.bean.Comment;

import java.util.List;

public interface CallbackListener {
    void onFinish();
    void onStringFinish(List<Comment> list);
}
