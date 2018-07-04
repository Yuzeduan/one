package com.yuzeduan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.Comment;

/**
 * 显示评论列表的适配器
 * Created by YZD on 2018/5/20.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    private int mResourceId;

    public CommentAdapter(Context context, int textViewResourceId, java.util.List list) {
        super(context, textViewResourceId, list);
        mResourceId = textViewResourceId;
    }

    public View getView(int position, View converView, ViewGroup parent) {
        final Comment comment = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (converView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvUserName = (TextView)view.findViewById(R.id.tv_user_name);
            viewHolder.tvCreateTime = (TextView)view.findViewById(R.id.tv_created_time);
            viewHolder.tvContent = (TextView)view.findViewById(R.id.tv_content);
            view.setTag(viewHolder);
        }
        else{
            view = converView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tvUserName.setText(comment.getmUserName());
        viewHolder.tvCreateTime.setText(comment.getmCreateTime());
        viewHolder.tvContent.setText(comment.getmContent());
        return view;
    }

    class ViewHolder{
        TextView tvUserName;
        TextView tvCreateTime;
        TextView tvContent;
    }
}
