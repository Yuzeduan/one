package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

public class StoryAuthor {
    @SerializedName("user_name")
    private String mAuthorName;

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }
}
