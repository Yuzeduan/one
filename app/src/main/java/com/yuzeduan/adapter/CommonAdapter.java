package com.yuzeduan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 构建公共的适配器
 * @param <T> 表示子项的类型
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas;
    private int mItemLayoutId;

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, mItemLayoutId, position);
        convert(viewHolder, getItem(position));
        return viewHolder.getmConvertView();
    }

    public CommonAdapter(Context mContext, List<T> mDatas, int mItemLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemLayoutId = mItemLayoutId;
    }

    /**
     * 抽象出来的填充控件的方法
     * @param viewHolder
     * @param item
     */
    public abstract void convert(ViewHolder viewHolder, T item);
}
