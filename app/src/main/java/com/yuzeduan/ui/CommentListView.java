package com.yuzeduan.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 对评论的列表控件进行重写,解决scrollView和listView的嵌套冲突
 * Created by YZD on 2018/5/19.
 */
public class CommentListView extends ListView {
    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *重写父类的onMeasure方法,提供更加准确和有效的测量listView和它的内容
     * @param widthMeasureSpec parent父布局提出的水平的空间要求
     * @param heightMeasureSpec parent父布局提出的垂直的空间要求
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 进行准确的测量,改变listView的高度
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
