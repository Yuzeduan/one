package com.yuzeduan.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.yuzeduan.db.ReadingDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.HttpUtil;
import com.yuzeduan.util.ParseJSONUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 展示阅读详情和阅读评论的界面
 * 通过接收上一活动传来的阅读详情具体id,进行阅读详情和该阅读评论的信息的获取和展示
 */
public class ReadingContentActivity extends AppCompatActivity {
    private ListView mLvReadingComment;  // 展示阅读评论列表的控件
    private TextView mTvTitle, mTvAuthor, mTvContent, mTvDate;
    private String mItemId;
    private Handler mHandler = new CommentHandler(this);

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
        ReadingDao readingDao = new ReadingDao();
        Reading reading = readingDao.findReading(mItemId);
        //  // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if(reading != null){
            mTvTitle.setText(reading.getmTitle());
            mTvAuthor.setText("文 | " + reading.getmAuthorName());
            Spanned spanned = Html.fromHtml(reading.getmContent());
            mTvContent.setText(spanned);
            mTvDate.setText(reading.getmLastUpdateDate());
        }
        else {
            queryFromServer(address);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public static class CommentHandler extends Handler{
        private WeakReference<ReadingContentActivity> mActivity;

        public CommentHandler(ReadingContentActivity activity){
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            String response = (String) msg.obj;
            ArrayList<Comment> commentList = ParseJSONUtil.parseComment(response);
            CommentAdapter adapter = new CommentAdapter(mActivity.get(), commentList,R.layout.comment_item);
            mActivity.get().mLvReadingComment.setAdapter(adapter);
        }
    }

    /**
     * 阅读评论的展示
     * @param address 获取数据的api地址
     */
    public void setReadingCommentView(String address){

        HttpUtil.getJSON(address, new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {
                Message message = new Message();
                message.obj = response;
                mHandler.sendMessage(message);
            }
        });
    }

    /**
     * 从服务器获取数据
     * @param address 用于获取数据的api地址
     */
    public void queryFromServer(final String address){
        HttpUtil.getJSON(address, new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseReading(response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取数据后重新调用展示数据的方法,此时数据库中已有缓存
                        setReadingView(address);
                    }
                });
            }
        });
    }
}
