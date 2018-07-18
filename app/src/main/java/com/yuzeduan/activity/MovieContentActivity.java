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
import com.yuzeduan.bean.Movie;
import com.yuzeduan.db.MovieDao;
import com.yuzeduan.model.CallbackListener;
import com.yuzeduan.model.CommentModel;
import com.yuzeduan.model.ContentModel;
import java.util.List;

import static com.yuzeduan.bean.Constant.MOVIE;

/**
 * 用于展示影视详细的界面
 * 通过接收上一活动传来的影视详情具体id,进行影视详情和该影视评论的信息的获取和展示
 */
public class MovieContentActivity extends AppCompatActivity {
    private ListView mLvMovieComment;  // 展示影视评论列表的控件
    private TextView mTvTitle, mTvAuthor, mTvContent, mTvDate;
    private String mItemId;
    private ContentModel contentModel = new ContentModel();
    private CommentModel commentModel = new CommentModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_content);
        mTvTitle = (TextView)findViewById(R.id.movie_tv_title);
        mTvAuthor = (TextView)findViewById(R.id.movie_tv_author);
        mTvContent = (TextView)findViewById(R.id.movie_tv_content);
        mTvDate = (TextView)findViewById(R.id.movie_tv_date);
        mLvMovieComment = (ListView)findViewById(R.id.movie_lv_comment);
        mLvMovieComment.setFocusable(false);
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
    public void setMovieView(final String address){
        MovieDao movieDao = new MovieDao();
        Movie movie = movieDao.findMovie(mItemId);
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if(movie != null){
            mTvTitle.setText(movie.getmTitle());
            mTvAuthor.setText("文 | " + movie.getmAuthor().getmUserName());
            Spanned spanned = Html.fromHtml(movie.getmContent());
            mTvContent.setText(spanned);
            mTvDate.setText(movie.getmInputDate());
        }
        else{
            contentModel.queryContentData(address, MOVIE, new CallbackListener() {
                @Override
                public void onFinish() {
                    setMovieView(address);
                }

                @Override
                public void onStringFinish(List<Comment> list) {
                }
            });
        }
    }

    /**
     * 显示影视评论的界面
     * @param address 表示获取影视评论的api地址
     */
    public void setMovieCommentView(String address){
        commentModel.queryCommentData(address, new CallbackListener() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onStringFinish(List<Comment> list) {
                CommentAdapter adapter = new CommentAdapter(MovieContentActivity.this, list, R.layout.comment_item);
                mLvMovieComment.setAdapter(adapter);
            }
        });
    }
}
