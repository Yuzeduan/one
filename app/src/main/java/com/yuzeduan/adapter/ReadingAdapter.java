package com.yuzeduan.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import java.util.List;

public class ReadingAdapter extends CommonAdapter<ReadingMusicList>{
    @Override
    public void convert(final ViewHolder viewHolder, ReadingMusicList item) {
        ImageHttpUtil.setImage(item.getmImgUrl(), new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                viewHolder.setBitmap(R.id.iv_reading, bitmap);
            }
        });
        viewHolder.setText(R.id.tv_title, item.getmTitle());
        viewHolder.setText(R.id.tv_forward, item.getmForword());
        viewHolder.setText(R.id.tv_date, item.getmForword());
        viewHolder.setText(R.id.tv_author_name, "文 / "+ item.getmAuthor().getmUserName());
        viewHolder.setText(R.id.tv_desc,item.getmAuthor().getmDesc());
    }

    public ReadingAdapter(Context context, List<ReadingMusicList> list, int itemLayoutId){
        super(context, list, itemLayoutId);
    }
}
