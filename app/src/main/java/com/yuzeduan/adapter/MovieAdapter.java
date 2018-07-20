package com.yuzeduan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import java.util.List;

public class MovieAdapter extends CommonAdapter<MovieList> {
    @Override
    public void convert(final ViewHolder viewHolder, MovieList item) {
        ImageHttpUtil.setImage(item.getmImgUrl(), new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                viewHolder.setBitmap(R.id.iv_movie, bitmap);
            }
        });
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
