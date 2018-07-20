package com.yuzeduan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzeduan.adapter.CommentAdapter;
import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.Constant;
import com.yuzeduan.bean.Music;
import com.yuzeduan.model.CommentCallback;
import com.yuzeduan.model.CommentModel;
import com.yuzeduan.model.MusicContentCallback;
import com.yuzeduan.model.MusicContentModel;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;
import com.yuzeduan.util.WebViewImageUtil;

import java.util.List;

/**
 * 用于展示音乐详情的界面
 * 通过接收上一活动传来的音乐详情具体id,进行音乐详情和该音乐评论的信息的获取和展示
 */
public class MusicContentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;  // 展示音乐评论列表的控件
    private TextView mTvMusicTitle, mTvStoryTitle, mTvStoryAuthorName, mTvMusicUserName,
            mTvDate;
    private WebView mWvContent;
    private ImageView mIvCover;
    private String mItemId;
    private MusicContentModel mMusicContentModel = new MusicContentModel();
    private CommentModel commentModel = new CommentModel();

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
        mWvContent = findViewById(R.id.music_wv_content);
        mRecyclerView = (RecyclerView) findViewById(R.id.music_rv_comment);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setFocusable(false);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Intent intent = getIntent();
        mItemId = intent.getStringExtra("id");
        String contentAddress = Constant.createMusicUrl(mItemId);
        String commentAddress = Constant.createMusicCommentUrl(mItemId);
        setMusicView(contentAddress);
        setMusicCommentView(commentAddress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 进行音乐详情的展示
     * @param address 表示获取数据的api地址
     */
    public void setMusicView(String address){
        mMusicContentModel.getMusicData(mItemId, address, new MusicContentCallback() {
            @Override
            public void onFinish(Music music) {
                mTvMusicTitle.setText("歌曲 | " + music.getmMusicTitle());
                mTvStoryTitle.setText(music.getmStoryTitle());
                mTvStoryAuthorName.setText("文 | " + music.getmStoryAuthor().getmAuthorName());
                mTvMusicUserName.setText("歌曲 | " + music.getmAuthor().getmUserName());
                mTvDate.setText(music.getmLastUpdateDate());
                String content = WebViewImageUtil.getNewContent(music.getmStoryContent());
                mWvContent.loadDataWithBaseURL(null, content, null, null, null);
                ImageHttpUtil.setImage(music.getmCover(), new ImageCallback() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        mIvCover.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }


    /**
     * 进行音乐评论界面的展示
     * @param address 表示获取数据用的api地址
     */
    public void setMusicCommentView(String address){
        commentModel.queryCommentData(address, new CommentCallback() {
            @Override
            public void onFinish(List<Comment> list) {
                CommentAdapter adapter = new CommentAdapter(MusicContentActivity.this, list, R.layout.comment_item);
                mRecyclerView.setAdapter(adapter);
            }
        });
    }
}
