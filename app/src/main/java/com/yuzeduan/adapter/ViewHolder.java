package com.yuzeduan.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 构建通用的ViewHolder
 */
public class ViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;
    private View mConvertView;

    /**
     * 创建一个ViewHolder
     */
    private ViewHolder(View view){
        super(view);
        mViews = new SparseArray<>();
        mConvertView = view;
    }

    /**
     * 拿到一个ViewHolder对象
     * @param parent
     * @param layoutId
     * @return 如果View加载过,则返回ViewHolder进行复用,如果没有,则创建一个ViewHolder
     */
    public static ViewHolder getViewHolder(ViewGroup parent, int layoutId){
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
        return new ViewHolder(view);
    }


    // 通过相应的id获取控件
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置内容
     * @param viewId 表示控件的id
     * @param text 控件需要显示的内容
     * @return 返回该ViewHolder对象,可产生方法链
     */
    public ViewHolder setText(int viewId, String text){
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    // 为ImageView设置图片(Bitmap)
    public ViewHolder setBitmap(int viewId, Bitmap bitmap){
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    // 为CircleImageView设置图片(Bitmap)
    public ViewHolder setCircleBitmap(int viewId, Bitmap bitmap){
        CircleImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

}
