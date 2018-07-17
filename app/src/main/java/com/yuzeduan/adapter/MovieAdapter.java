package com.yuzeduan.adapter;

import android.content.Context;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.MovieList;

import java.util.List;

public class MovieAdapter extends CommonAdapter<MovieList> {
    @Override
    public void convert(ViewHolder viewHolder, MovieList item) {
        viewHolder.setBitmap(R.id.iv_movie, item.getmImgUrl());
        viewHolder.setText(R.id.tv_title, item.getmTitle());
        viewHolder.setText(R.id.tv_forward, item.getmForword());
        viewHolder.setText(R.id.tv_date, item.getmLastUpdateDate());
        viewHolder.setText(R.id.tv_subtitle, item.getmSubtitle());
        viewHolder.setText(R.id.tv_author_name, "文 / " + item.getmAuthor().getmUserName());
        viewHolder.setText(R.id.tv_desc, item.getmAuthor().getmDesc());
    }

    public MovieAdapter(Context mContext, List<MovieList> mDatas, int mItemLayoutId) {
        super(mContext, mDatas, mItemLayoutId);
    }
}
