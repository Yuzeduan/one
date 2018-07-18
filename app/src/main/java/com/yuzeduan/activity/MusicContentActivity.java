package com.yuzeduan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.yuzeduan.bean.Constant;
import com.yuzeduan.bean.Music;
import com.yuzeduan.db.MusicDao;
import com.yuzeduan.model.CallbackListener;
import com.yuzeduan.model.CommentModel;
import com.yuzeduan.model.ContentModel;
import com.yuzeduan.util.ImageCallback;
import com.yuzeduan.util.ImageHttpUtil;
import java.util.List;

import static com.yuzeduan.bean.Constant.MUSIC;
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
    private ContentModel contentModel = new ContentModel();
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
        mTvContent = (TextView)findViewById(R.id.music_tv_content);
        mLvMusicComment = (ListView)findViewById(R.id.music_lv_comment) ;
        mLvMusicComment.setFocusable(false);  // 取消listView的焦点
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
    public void setMusicView(final String address){
        MusicDao musicDao = new MusicDao();
        Music music = musicDao.findMusic(mItemId);
        // 判断数据库是否有缓存,如果有缓存,则直接从数据库获取数据并展示,若无,则从服务器获取数据
        if(music != null){
            mTvMusicTitle.setText("歌曲 | " + music.getmMusicTitle());
            mTvStoryTitle.setText(music.getmStoryTitle());
            mTvStoryAuthorName.setText("文 | " + music.getmStoryAuthor().getmAuthorName());
            mTvMusicUserName.setText("歌曲 | " + music.getmAuthor().getmUserName());
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
            contentModel.queryContentData(address, MUSIC, new CallbackListener() {
                @Override
                public void onFinish() {
                    setMusicView(address);
                }
                @Override
                public void onStringFinish(List<Comment> list) {

                }
            });
        }
    }


    /**
     * 进行音乐评论界面的展示
     * @param address 表示获取数据用的api地址
     */
    public void setMusicCommentView(String address){
        commentModel.queryCommentData(address, new CallbackListener() {
            @Override
            public void onFinish() {
            }

            @Override
            public void onStringFinish(List<Comment> list) {
                CommentAdapter adapter = new CommentAdapter(MusicContentActivity.this, list, R.layout.comment_item);
                mLvMusicComment.setAdapter(adapter);
            }
        });
    }

}
