package com.yuzeduan.adapter;

import android.content.Context;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.ReadingMusicList;

import java.util.List;

public class MusicAdapter extends CommonAdapter<ReadingMusicList> {
    @Override
    public void convert(ViewHolder viewHolder, ReadingMusicList item) {
        viewHolder.setCircleBitmap(R.id.iv_music, item.getmImgUrl());
        viewHolder.setText(R.id.tv_title, item.getmTitle());
        viewHolder.setText(R.id.tv_forward, item.getmForword());
        viewHolder.setText(R.id.tv_date, item.getmLastUpdateDate());
        viewHolder.setText(R.id.tv_author_name, "文 / " + item.getmAuthor().getmUserName());
        viewHolder.setText(R.id.tv_desc, item.getmAuthor().getmDesc());
    }

    public MusicAdapter(Context mContext, List<ReadingMusicList> mDatas, int mItemLayoutId) {
        super(mContext, mDatas, mItemLayoutId);
    }
}
