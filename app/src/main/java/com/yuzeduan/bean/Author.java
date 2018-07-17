package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

public class Author {
    @SerializedName("user_name")
    private String mUserName;
    @SerializedName("desc")
    private String mDesc;

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }
}
