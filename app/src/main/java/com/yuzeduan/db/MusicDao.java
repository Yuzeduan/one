package com.yuzeduan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuzeduan.bean.Music;
import com.yuzeduan.util.OneApplication;

/**
 * 对数据库中的音乐详情的数据进行增加和查询操作
 * Created by YZD on 2018/5/24.
 */

public class MusicDao {
    private OneDatabaseHelper dbHelper = new OneDatabaseHelper(OneApplication.getContext(), "OneData.db", null, 1);
    private SQLiteDatabase writedb = dbHelper.getWritableDatabase();
    private SQLiteDatabase readdb = dbHelper.getReadableDatabase();

    /**
     * 添加音乐详情的数据进数据库
     * @param music 表示封装了添加数据的音乐详情对象
     */
    public void addMusic(Music music){
        ContentValues values = new ContentValues();
        values.put("item_id", music.getmItemId());
        values.put("music_title", music.getmMusicTitle());
        values.put("cover", music.getmCover());
        values.put("story_title", music.getmStoryTitle());
        values.put("story_content", music.getmStoryContent());
        values.put("last_update_date", music.getmLastUpdateDate());
        values.put("music_user_name", music.getmMusicUserName());
        values.put("story_author_name", music.getmStoryAuthorName());
        writedb.insert("music", null, values);
    }

    /**
     * 根据item_id进行查询数据库中的音乐详情的数据
     * @param id 表示进行查询的条件,其对应music表中的item_id字段
     * @return 若查询成功,则返回对应的音乐详情对象,否则返回null
     */
    public Music findMusic(String id){
        Music music = new Music();
        String selection = "item_id = ?";
        String[] selectionArgs = new  String[]{ id };
        Cursor cursor = readdb.query("music", null, selection, selectionArgs, null ,null, null);
        if(cursor.moveToFirst()){
            String itemId = cursor.getString(cursor.getColumnIndex("item_id"));
            String musicTitle = cursor.getString(cursor.getColumnIndex("music_title"));
            String cover = cursor.getString(cursor.getColumnIndex("cover"));
            String storyTitle = cursor.getString(cursor.getColumnIndex("story_title"));
            String storyContent = cursor.getString(cursor.getColumnIndex("story_content"));
            String lastUpdateDate = cursor.getString(cursor.getColumnIndex("last_update_date"));
            String musicUserName = cursor.getString(cursor.getColumnIndex("music_user_name"));
            String storyAuthorName = cursor.getString(cursor.getColumnIndex("story_author_name"));
            music.setmItemId(itemId);
            music.setmMusicTitle(musicTitle);
            music.setmCover(cover);
            music.setmStoryTitle(storyTitle);
            music.setmStoryContent(storyContent);
            music.setmLastUpdateDate(lastUpdateDate);
            music.setmMusicUserName(musicUserName);
            music.setmStoryAuthorName(storyAuthorName);
            cursor.close();
            return music;
        }
        cursor.close();
        return null;
    }
}
