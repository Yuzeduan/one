package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YZD on 2018/5/19.
 */

public class Reading {
    @SerializedName("content_id")
    private String mItemId;
    @SerializedName("hp_title")
    private String mTitle;
    @SerializedName("hp_author")
    private String mAuthorName;
    @SerializedName("hp_content")
    private String mContent;
    @SerializedName("last_update_date")
    private String mLastUpdateDate;

    public String getmItemId() {
        return mItemId;
    }

    public void setmItemId(String mItemId) {
        this.mItemId = mItemId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public void setmAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmLastUpdateDate() {
        return mLastUpdateDate;
    }

    public void setmLastUpdateDate(String mLastUpdateDate) {
        this.mLastUpdateDate = mLastUpdateDate;
    }
}
