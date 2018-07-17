package com.yuzeduan.adapter;

import android.content.Context;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.Comment;

import java.util.List;

public class CommentAdapter extends CommonAdapter<Comment> {
    @Override
    public void convert(ViewHolder viewHolder, Comment item) {
        viewHolder.setText(R.id.tv_user_name, item.getmUser().getmUserName());
        viewHolder.setText(R.id.tv_created_time, item.getmCreateTime());
        viewHolder.setText(R.id.tv_content, item.getmContent());
    }

    public CommentAdapter(Context mContext, List<Comment> mDatas, int mItemLayoutId) {
        super(mContext, mDatas, mItemLayoutId);
    }
}
