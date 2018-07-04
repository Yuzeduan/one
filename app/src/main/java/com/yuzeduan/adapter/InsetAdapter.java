package com.yuzeduan.adapter;

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
import com.yuzeduan.bean.Inset;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import java.util.List;

/**
 * 显示插画列表的适配器
 * Created by YZD on 2018/5/18.
 */

public class InsetAdapter extends ArrayAdapter<Inset> {
    private int mResourceId;

    public InsetAdapter(Context context, int textViewResourceId, List list) {
        super(context, textViewResourceId, list);
        mResourceId = textViewResourceId;
    }

    public View getView (int position, View converView, ViewGroup parent){
        final Inset inset = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if(converView == null){
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView)view.findViewById(R.id.tv_title);
            viewHolder.image = (ImageView)view.findViewById(R.id.iv_inset);
            viewHolder.tvImageAuthors = (TextView)view.findViewById(R.id.tv_imageauthors);
            viewHolder.tvContent = (TextView)view.findViewById(R.id.tv_content);
            viewHolder.tvHpAuthors = (TextView)view.findViewById(R.id.tv_hpauthors);
            viewHolder.tvDate = (TextView)view.findViewById(R.id.tv_date);
            view.setTag(viewHolder);
        }
        else{
            view = converView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tvTitle.setText(inset.getmTitle());
        viewHolder.tvImageAuthors.setText("插画 | "+ inset.getmImageAuthor());
        viewHolder.tvContent.setText(inset.getmContent());
        viewHolder.tvHpAuthors.setText("-- " + inset.getmHpAuthor() );
        viewHolder.tvDate.setText(inset.getmLastUpdateDate());
        ImageHttpUtil.setImage(inset.getmImgUrl(), new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                viewHolder.image.setImageBitmap(bitmap);
            }
        });
        return view;
    }

    class ViewHolder{
        TextView tvTitle;
        ImageView image;
        TextView tvImageAuthors;
        TextView tvContent;
        TextView tvHpAuthors;
        TextView tvDate;
    }
}
