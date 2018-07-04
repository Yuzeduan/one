package com.yuzeduan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzeduan.adapter.CommentAdapter;
import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.Music;
import com.yuzeduan.db.MusicDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.HttpUtil;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;
import com.yuzeduan.util.ParseJSONUtil;

import java.util.ArrayList;

/**
 * 用于展示音乐详情的界面
 * 通过接收上一活动传来的音乐详情具体id,进行音乐详情和该音乐评论的信息的获取和展示
 */
public class MusicContentActivity extends AppCompatActivity {
    private ListView mLvMusicComment;  // 展示音乐评论列表的控件
    private TextView mTvMusicTitle, mTvStoryTitle, mTvStoryAuthorName, mTvMusicUserName,
            mTvDate, mTvContent;
    private ImageView mIvCover;
    private String mItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_content);
        mIvCover = (ImageView) findViewById(R.id.music_iv_cover);
        mTvMusicTitle = (TextView)findViewById(R.id.music_tv_title);
        mTvStoryTitle = (TextView)findViewById(R.id.music_tv_storytitle);
        mTvStoryAuthorName = (TextView)findViewById(R.id.music_tv_storyauthorname);
        mTvMusicUserName = (TextView)findViewById(R.id.music_tv_musicusername);
        mTvDate = (TextView)findViewById(R.id.music_tv_date);
        mTvContent = (TextView)findViewById(R.id.music_tv_content);
        mLvMusicComment = (ListView)findViewById(R.id.music_lv_comment) ;
        mLvMusicComment.setFocusable(false);  // 取消listView的焦点
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("id");
        String contentAddress = "http://v3.wufazhuce.com:8000/api/music/detail/"+mItemId+"?version=3.5.0&platform=android";
        String commentAddress = " http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/"+mItemId+"/0?platform=android";
        setMusicView(contentAddress);
        setMusicCommentView(commentAddress);
    }

    /**
     * 进行音乐详情的展示
     * @param address 表示获取数据的api地址
     */
    public void setMusicView(String address){
        MusicDao musicDao = new MusicDao();
        Music music = musicDao.findMusic(mItemId);
        // 判断数据库是否有缓存,如果有缓存,则直接从数据库获取数据并展示,若无,则从服务器获取数据
        if(music != null){
            mTvMusicTitle.setText("歌曲 | " + music.getmMusicTitle());
            mTvStoryTitle.setText(music.getmStoryTitle());
            mTvStoryAuthorName.setText("文 | " + music.getmStoryAuthorName());
            mTvMusicUserName.setText("歌曲 | " + music.getmMusicUserName());
            mTvDate.setText(music.getmLastUpdateDate());
            Spanned spanned = Html.fromHtml(music.getmStoryContent());
            mTvContent.setText(spanned);
            ImageHttpUtil.setImage(music.getmCover(), new ImageCallback() {
                @Override
                public void getBitmap(Bitmap bitmap) {
                    mIvCover.setImageBitmap(bitmap);
                }
            });
        }
        else {
            queryFromServer(address);
        }
    }

    /**
     * 进行音乐评论界面的展示
     * @param address 表示获取数据用的api地址
     */
    public void setMusicCommentView(String address){
        // 采用异步信息处理机制,从子线程获取数据后在主线程进行数据的解析和展示
        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                String response = (String) msg.obj;
                ArrayList<Comment> commentList = ParseJSONUtil.parseComment(response);
                CommentAdapter adapter = new CommentAdapter(MusicContentActivity.this, R.layout.comment_item, commentList);
                mLvMusicComment.setAdapter(adapter);
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
     * 从服务器获取数据
     * @param address 表示获取数据所用的api地址
     */
    public void queryFromServer(final String address){
        HttpUtil.getJSON(address, new HttpCallbackListener(){
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseMusic(response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 获取数据后重新调用展示数据的方法,此时数据库中已有缓存
                        setMusicView(address);
                    }
                });
            }
        });
    }
}
