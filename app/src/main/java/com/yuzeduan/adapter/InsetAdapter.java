package com.yuzeduan.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.Inset;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import java.util.List;

public class InsetAdapter extends CommonAdapter<Inset> {
    @Override
    public void convert(final ViewHolder viewHolder, Inset item) {
        ImageHttpUtil.setImage(item.getmImgUrl(), new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                viewHolder.setBitmap(R.id.iv_inset, bitmap);
            }
        });
        viewHolder.setText(R.id.tv_title, item.getmTitle());
        viewHolder.setText(R.id.tv_imageauthors, "插画 | "+ item.getmImageAuthor());
        viewHolder.setText(R.id.tv_content, item.getmContent());
        viewHolder.setText(R.id.tv_hpauthors, "-- " + item.getmHpAuthor());
        viewHolder.setText(R.id.tv_date, item.getmLastUpdateDate());
    }

    public InsetAdapter(Context mContext, List<Inset> mDatas, int mItemLayoutId) {
        super(mContext, mDatas, mItemLayoutId);
    }
}
