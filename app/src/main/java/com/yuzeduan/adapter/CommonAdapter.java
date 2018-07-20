package com.yuzeduan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 构建公共的适配器
 * @param <T> 表示子项的类型
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private OnItemClickListener mOnItemClickListener;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    mOnItemClickListener.OnItemClickListener(holder.getLayoutPosition());
                }
            });
        }
        convert(holder, mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CommonAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
    }

    /**
     * 抽象出来的填充控件的方法
     * @param viewHolder
     * @param item
     */
    public abstract void convert(ViewHolder viewHolder, T item);

    public interface OnItemClickListener {
        void OnItemClickListener(int position);
    }
}
