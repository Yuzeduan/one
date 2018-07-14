package com.yuzeduan.adapter;

import android.content.Context;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.Inset;

import java.util.List;

public class InsetAdapter extends CommonAdapter<Inset> {
    @Override
    public void convert(ViewHolder viewHolder, Inset item) {
        viewHolder.setText(R.id.tv_title, item.getmTitle());
        viewHolder.setBitmap(R.id.iv_inset, item.getmImgUrl());
        viewHolder.setText(R.id.tv_imageauthors, "插画 | "+ item.getmImageAuthor());
        viewHolder.setText(R.id.tv_content, item.getmContent());
        viewHolder.setText(R.id.tv_hpauthors, "-- " + item.getmHpAuthor());
        viewHolder.setText(R.id.tv_date, item.getmLastUpdateDate());
    }

    public InsetAdapter(Context mContext, List<Inset> mDatas, int mItemLayoutId) {
        super(mContext, mDatas, mItemLayoutId);
    }
}
