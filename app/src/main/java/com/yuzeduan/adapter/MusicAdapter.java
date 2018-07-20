package com.yuzeduan.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import java.util.List;

public class MusicAdapter extends CommonAdapter<ReadingMusicList> {
    @Override
    public void convert(final ViewHolder viewHolder, ReadingMusicList item) {
        ImageHttpUtil.setImage(item.getmImgUrl(), new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                viewHolder.setCircleBitmap(R.id.iv_music, bitmap);
            }
        });
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
