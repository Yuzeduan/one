package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YZD on 2018/5/19.
 */

public class Movie {
    @SerializedName("movie_id")
    private String mItemId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("content")
    private String mContent;
    @SerializedName("input_date")
    private String mInputDate;
    @SerializedName("user")
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

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmInputDate() {
        return mInputDate;
    }

    public void setmInputDate(String mInputDate) {
        this.mInputDate = mInputDate;
    }

    public Author getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(Author mAuthor) {
        this.mAuthor = mAuthor;
    }
}
