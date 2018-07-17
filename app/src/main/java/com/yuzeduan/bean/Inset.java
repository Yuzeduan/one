package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

public class Inset {
    @SerializedName("hp_title")
    private String mTitle;
    @SerializedName("hp_img_url")
    private String mImgUrl;
    @SerializedName("hp_content")
    private String mContent;
    @SerializedName("image_authors")
    private String mImageAuthor;
    @SerializedName("hp_author")
    private String mHpAuthor;
    @SerializedName("last_update_date")
    private String mLastUpdateDate;

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmImageAuthor() {
        return mImageAuthor;
    }

    public void setmImageAuthor(String mImageAuthor) {
        this.mImageAuthor = mImageAuthor;
    }

    public String getmHpAuthor() {
        return mHpAuthor;
    }

    public void setmHpAuthor(String mHpAuthor) {
        this.mHpAuthor = mHpAuthor;
    }

    public String getmLastUpdateDate() {
        return mLastUpdateDate;
    }

    public void setmLastUpdateDate(String mLastUpdateDate) {
        this.mLastUpdateDate = mLastUpdateDate;
    }
}
