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
import com.yuzeduan.bean.Movie;
import com.yuzeduan.db.MovieDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.HttpUtil;
import com.yuzeduan.util.ParseJSONUtil;

import java.util.ArrayList;

/**
 * 用于展示影视详细的界面
 * 通过接收上一活动传来的影视详情具体id,进行影视详情和该影视评论的信息的获取和展示
 */
public class MovieContentActivity extends AppCompatActivity {
    private ListView mLvMovieComment;  // 展示影视评论列表的控件
    private TextView mTvTitle, mTvAuthor, mTvContent, mTvDate;
    private String mItemId;

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
        String contentAddress = "http://v3.wufazhuce.com:8000/api/movie/"+mItemId+"/story/1/0?platform=android";
        String commentAddress = " http://v3.wufazhuce.com:8000/api/comment/praiseandtime/movie/"+mItemId+"/0?&platform=android";
        setMovieView(contentAddress);
        setMovieCommentView(commentAddress);
    }

    /**
     * 展示影视详情界面的方法
     * @param address 表示获取影视详情的api地址
     */
    public void setMovieView(String address){
        MovieDao movieDao = new MovieDao();
        Movie movie = movieDao.findMovie(mItemId);
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if(movie != null){
            mTvTitle.setText(movie.getmTitle());
            mTvAuthor.setText("文 | " + movie.getmAuthorName());
            Spanned spanned = Html.fromHtml(movie.getmContent());
            mTvContent.setText(spanned);
            mTvDate.setText(movie.getmInputDate());
        }
        else{
            queryFromServer(address);
        }
    }

    /**
     * 显示影视评论的界面
     * @param address 表示获取影视评论的api地址
     */
    public void setMovieCommentView(String address){
        // 采用异步信息处理机制,从子线程获取数据,在主线程进行解析并展示
        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                String response = (String) msg.obj;
                ArrayList<Comment> commentList = ParseJSONUtil.parseComment(response);
                CommentAdapter adapter = new CommentAdapter(MovieContentActivity.this, R.layout.comment_item, commentList);
                mLvMovieComment.setAdapter(adapter);
            }
        };

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
     * 从服务器中获取数据
     * @param address 表示获取数据的api地址
     */
    public void queryFromServer(final String address){
        HttpUtil.getJSON(address, new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseMovie(response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取数据后重新调用展示数据的方法,此时数据库中已有缓存
                        setMovieView(address);
                    }
                });
            }
        });
    }
}
