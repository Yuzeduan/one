package com.yuzeduan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 构建通用的ViewHolder
 */
public class ViewHolder {
    private int mPosition;
    private SparseArray<View> mViews;
    private View mConvertView;

    /**
     * 创建一个ViewHolder
     * @param context
     * @param parent
     * @param layoutId
     * @param position
     */
    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position){
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return 如果View加载过,则返回ViewHolder进行复用,如果没有,则创建一个ViewHolder
     */
    public static ViewHolder getViewHolder(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position){
        if (convertView == null){
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView(){
        return mConvertView;
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
    public ViewHolder setBitmap(int viewId, String imageUrl){
        final ImageView view = getView(viewId);
        ImageHttpUtil.setImage(imageUrl, new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                view.setImageBitmap(bitmap);
            }
        });
        return this;
    }

    // 为CircleImageView设置图片(Bitmap)
    public ViewHolder setCircleBitmap(int viewId, String imageUrl){
        final CircleImageView view = getView(viewId);
        ImageHttpUtil.setImage(imageUrl, new ImageCallback() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                view.setImageBitmap(bitmap);
            }
        });
        return this;
    }

    public int getmPosition() {
        return mPosition;
    }

    public View getmConvertView(){
        return mConvertView;
    }
}
