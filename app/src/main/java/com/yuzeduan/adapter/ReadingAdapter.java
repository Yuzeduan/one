package com.yuzeduan.adapter;

import android.content.Context;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.ReadingMusicList;

import java.util.List;

public class ReadingAdapter extends CommonAdapter<ReadingMusicList>{
    @Override
    public void convert(ViewHolder viewHolder, ReadingMusicList item) {
        viewHolder.setText(R.id.tv_title, item.getmTitle());
        viewHolder.setBitmap(R.id.iv_reading, item.getmImgUrl());
        viewHolder.setText(R.id.tv_forward, item.getmForword());
        viewHolder.setText(R.id.tv_date, item.getmForword());
        viewHolder.setText(R.id.tv_author_name, item.getmUserName());
        viewHolder.setText(R.id.tv_desc, "文 / "+ item.getmDesc());
    }

    public ReadingAdapter(Context context, List<ReadingMusicList> list, int itemLayoutId){
        super(context, list, itemLayoutId);
    }
}
