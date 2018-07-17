package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YZD on 2018/5/19.
 */

public class Comment {
    @SerializedName("content")
    private String mContent;
    @SerializedName("created_at")
    private String mCreateTime;
    @SerializedName("user")
    private Author mUser;

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public Author getmUser() {
        return mUser;
    }

    public void setmUser(Author mUser) {
        this.mUser = mUser;
    }
}
