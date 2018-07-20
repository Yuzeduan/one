package com.yuzeduan.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.TextView;

import com.yuzeduan.adapter.CommentAdapter;
import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.Constant;
import com.yuzeduan.bean.Movie;
import com.yuzeduan.model.CommentCallback;
import com.yuzeduan.model.CommentModel;
import com.yuzeduan.model.MovieContentCallback;
import com.yuzeduan.model.MovieContentModel;

import java.util.List;

/**
 * 用于展示影视详细的界面
 * 通过接收上一活动传来的影视详情具体id,进行影视详情和该影视评论的信息的获取和展示
 */
public class MovieContentActivity extends AppCompatActivity {
    private RecyclerView mRvMovieComment;// 展示影视评论列表的控件
    private TextView mTvTitle, mTvAuthor, mTvDate;
    private WebView mWvContent;
    private String mItemId;
    private MovieContentModel mMovieContentModel = new MovieContentModel();
    private CommentModel commentModel = new CommentModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_content);
        mTvTitle = (TextView)findViewById(R.id.movie_tv_title);
        mTvAuthor = (TextView)findViewById(R.id.movie_tv_author);
        mWvContent = findViewById(R.id.movie_wv_content);
        mTvDate = (TextView)findViewById(R.id.movie_tv_date);
        mRvMovieComment = findViewById(R.id.movie_rv_comment);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvMovieComment.setLayoutManager(manager);
        mRvMovieComment.setFocusable(false);
        //隐藏主标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();  // 获取上一活动传来的Intent对象
        mItemId = intent.getStringExtra("id");  // 从Intent对象中获取具体的id
        String contentAddress = Constant.createMovieUrl(mItemId);
        String commentAddress = Constant.createMovieCommentUrl(mItemId);
        setMovieView(contentAddress);
        setMovieCommentView(commentAddress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 展示影视详情界面的方法
     * @param address 表示获取影视详情的api地址
     */
    public void setMovieView(String address){
        mMovieContentModel.getMovieData(mItemId, address, new MovieContentCallback() {
            @Override
            public void onFinish(Movie movie) {
                mTvTitle.setText(movie.getmTitle());
                mTvAuthor.setText("文 | " + movie.getmAuthor().getmUserName());
                mWvContent.loadDataWithBaseURL(null, movie.getmContent(), null,null,null);
                mTvDate.setText(movie.getmInputDate());
            }
        });
    }

    /**
     * 显示影视评论的界面
     * @param address 表示获取影视评论的api地址
     */
    public void setMovieCommentView(String address){
        commentModel.queryCommentData(address, new CommentCallback() {
            @Override
            public void onFinish(List<Comment> list) {
                CommentAdapter adapter = new CommentAdapter(MovieContentActivity.this, list, R.layout.comment_item);
                mRvMovieComment.setAdapter(adapter);
            }
        });
    }
}
