package com.yuzeduan.model;

import com.yuzeduan.bean.Comment;

import java.util.List;

public interface CommentCallback {
    void onFinish(List<Comment> list);
}
