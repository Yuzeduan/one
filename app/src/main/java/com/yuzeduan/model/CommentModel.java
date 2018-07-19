package com.yuzeduan.model;

import com.yuzeduan.bean.Comment;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;

import java.util.List;

public class CommentModel {
    private List<Comment> mCommentList;

    public void queryCommentData(String url, final CommentCallback listener){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                mCommentList = ParseJSONUtil.parseComment(response);
                listener.onFinish(mCommentList);
            }
        });
    }
}
