package com.yuzeduan.adapter;

/**
 * 用于显示影视列表的适配器
 * Created by YZD on 2018/5/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<MovieList> {
    private int mResourceId;

    public MovieAdapter(Context context, int textViewResourceId, List list) {
        super(context, textViewResourceId, list);
        mResourceId = textViewResourceId;
    }

    public View getView (int position, View converView, ViewGroup parent){
        final MovieList movie = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if(converView == null){
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)view.findViewById(R.id.iv_movie);
            viewHolder.tvTitle = (TextView)view.findViewById(R.id.tv_title);
            viewHolder.tvForward = (TextView)view.findViewById(R.id.tv_forward);
            viewHolder.tvDate = (TextView)view.findViewById(R.id.tv_date);
            viewHolder.tvSubtitle = (TextView)view.findViewById(R.id.tv_subtitle);
            viewHolder.tvAuthorName = (TextView)view.findViewById(R.id.tv_author_name);
            viewHolder.tvDesc = (TextView)view.findViewById(R.id.tv_desc);
            view.setTag(viewHolder);
        }
        else{
            view = converView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tvTitle.setText(movie.getmTitle());
        viewHolder.tvForward.setText(movie.getmForword());
        viewHolder.tvDate.setText(movie.getmLastUpdateDate());
        viewHolder.tvAuthorName.setText("文 / "+ movie.getmUserName());
        viewHolder.tvDesc.setText(movie.getmDesc());
        viewHolder.tvSubtitle.setText("-- "+movie.getmSubtitle());
        ImageHttpUtil.setImage(movie.getmImgUrl(), new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                viewHolder.image.setImageBitmap(bitmap);
            }
        });
        return view;
    }

    class ViewHolder{
        ImageView image;
        TextView tvTitle;
        TextView tvForward;
        TextView tvDate;
        TextView tvSubtitle;
        TextView tvAuthorName;
        TextView tvDesc;
    }
}