package com.yuzeduan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yuzeduan.activity.R;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用于显示音乐列表的适配器
 * Created by YZD on 2018/5/18.
 */

public class MusicAdapter extends ArrayAdapter<ReadingMusicList> {
    private int mResourceId;

    public MusicAdapter(Context context, int textViewResourceId, java.util.List list) {
        super(context, textViewResourceId, list);
        mResourceId = textViewResourceId;
    }

    public View getView (int position, View converView, ViewGroup parent){
        final ReadingMusicList readingMusicList = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if(converView == null){
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (CircleImageView)view.findViewById(R.id.iv_music);
            viewHolder.tvTitle = (TextView)view.findViewById(R.id.tv_title);
            viewHolder.tvForward = (TextView)view.findViewById(R.id.tv_forward);
            viewHolder.tvDate = (TextView)view.findViewById(R.id.tv_date);
            viewHolder.tvAuthorName = (TextView)view.findViewById(R.id.tv_author_name);
            viewHolder.tvDesc = (TextView)view.findViewById(R.id.tv_desc);
            view.setTag(viewHolder);
        }
        else{
            view = converView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tvTitle.setText(readingMusicList.getmTitle());
        viewHolder.tvForward.setText(readingMusicList.getmForword());
        viewHolder.tvDate.setText(readingMusicList.getmLastUpdateDate());
        viewHolder.tvAuthorName.setText("文 / " + readingMusicList.getmUserName());
        viewHolder.tvDesc.setText(readingMusicList.getmDesc());
        ImageHttpUtil.setImage(readingMusicList.getmImgUrl(), new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                viewHolder.image.setImageBitmap(bitmap);
            }
        });
        return view;
    }

    class ViewHolder{
        CircleImageView image;
        TextView tvTitle;
        TextView tvForward;
        TextView tvDate;
        TextView tvAuthorName;
        TextView tvDesc;
    }
}
