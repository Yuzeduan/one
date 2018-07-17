package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YZD on 2018/5/18.
 */

public class ReadingMusicList {
    @SerializedName("id")
    private String mId;
    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("forward")
    private String mForword;
    @SerializedName("img_url")
    private String mImgUrl;
    @SerializedName("last_update_date")
    private String mLastUpdateDate;
    @SerializedName("author")
    private Author mAuthor;

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

    public String getmForword() {
        return mForword;
    }

    public void setmForword(String mForword) {
        this.mForword = mForword;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public String getmLastUpdateDate() {
        return mLastUpdateDate;
    }

    public void setmLastUpdateDate(String mLastUpdateDate) {
        this.mLastUpdateDate = mLastUpdateDate;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public Author getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(Author mAuthor) {
        this.mAuthor = mAuthor;
    }
}
