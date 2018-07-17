package com.yuzeduan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by YZD on 2018/5/19.
 */

public class Music {
    @SerializedName("id")
    private String mItemId;
    @SerializedName("title")
    private String mMusicTitle;
    @SerializedName("cover")
    private String mCover;
    @SerializedName("story_title")
    private String mStoryTitle;
    @SerializedName("story")
    private String mStoryContent;
    @SerializedName("last_update_date")
    private String mLastUpdateDate;
    @SerializedName("author")
    private Author mAuthor;
    @SerializedName("story_author")
    private StoryAuthor mStoryAuthor;

    public String getmItemId() {
        return mItemId;
    }

    public void setmItemId(String mItemId) {
        this.mItemId = mItemId;
    }

    public String getmMusicTitle() {
        return mMusicTitle;
    }

    public void setmMusicTitle(String mMusicTitle) {
        this.mMusicTitle = mMusicTitle;
    }

    public String getmCover() {
        return mCover;
    }

    public void setmCover(String mCover) {
        this.mCover = mCover;
    }

    public String getmStoryTitle() {
        return mStoryTitle;
    }

    public void setmStoryTitle(String mStoryTitle) {
        this.mStoryTitle = mStoryTitle;
    }

    public String getmStoryContent() {
        return mStoryContent;
    }

    public void setmStoryContent(String mStoryContent) {
        this.mStoryContent = mStoryContent;
    }

    public String getmLastUpdateDate() {
        return mLastUpdateDate;
    }

    public void setmLastUpdateDate(String mLastUpdateDate) {
        this.mLastUpdateDate = mLastUpdateDate;
    }

    public Author getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(Author mAuthor) {
        this.mAuthor = mAuthor;
    }

    public StoryAuthor getmStoryAuthor() {
        return mStoryAuthor;
    }

    public void setmStoryAuthor(StoryAuthor mStoryAuthor) {
        this.mStoryAuthor = mStoryAuthor;
    }
}

