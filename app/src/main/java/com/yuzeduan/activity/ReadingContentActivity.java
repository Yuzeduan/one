package com.yuzeduan.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzeduan.adapter.CommentAdapter;
import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.Constant;
import com.yuzeduan.bean.Reading;
import com.yuzeduan.model.CommentCallback;
import com.yuzeduan.model.CommentModel;
import com.yuzeduan.model.ReadingContentCallback;
import com.yuzeduan.model.ReadingContentModel;

import java.util.List;

/**
 * 展示阅读详情和阅读评论的界面
 * 通过接收上一活动传来的阅读详情具体id,进行阅读详情和该阅读评论的信息的获取和展示
 */
public class ReadingContentActivity extends AppCompatActivity {
    private ListView mLvReadingComment;  // 展示阅读评论列表的控件
    private TextView mTvTitle, mTvAuthor, mTvContent, mTvDate;
    private String mItemId;
    private ReadingContentModel mReadingContentModel = new ReadingContentModel();
    private CommentModel commentModel = new CommentModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_content);
        mTvTitle = (TextView)findViewById(R.id.reading_tv_title);
        mTvAuthor = (TextView)findViewById(R.id.reading_tv_author);
        mTvContent = (TextView)findViewById(R.id.reading_tv_content);
        mTvDate = (TextView)findViewById(R.id.reading_tv_date);
        mLvReadingComment = (ListView)findViewById(R.id.reading_lv_comment);
        mLvReadingComment.setFocusable(false);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("id");
        String contentAddress = Constant.createReadingUrl(mItemId);
        String commentAddress = Constant.createReadingCommentUrl(mItemId);
        setReadingView(contentAddress);
        setReadingCommentView(commentAddress);
    }

    /**
     * 展示阅读详情的界面
     * @param address 获取数据的api地址
     */
    public void setReadingView(String address){
        mReadingContentModel.getReadingData(mItemId, address, new ReadingContentCallback() {
            @Override
            public void onFinish(Reading reading) {
                mTvTitle.setText(reading.getmTitle());
                mTvAuthor.setText("文 | " + reading.getmAuthorName());
                Spanned spanned = Html.fromHtml(reading.getmContent());
                mTvContent.setText(spanned);
                mTvDate.setText(reading.getmLastUpdateDate());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 阅读评论的展示
     * @param address 获取数据的api地址
     */
    public void setReadingCommentView(String address){
        commentModel.queryCommentData(address, new CommentCallback() {
            @Override
            public void onFinish(List<Comment> list) {
                CommentAdapter adapter = new CommentAdapter(ReadingContentActivity.this, list, R.layout.comment_item);
                mLvReadingComment.setAdapter(adapter);
            }
        });
    }
}
