package com.yuzeduan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.util.OneApplication;

import java.util.ArrayList;

/**
 * 对数据库中的音乐列表的数据进行增添和查询操作
 * Created by YZD on 2018/5/25.
 */

public class MusicListDao {
    private OneDatabaseHelper dbHelper = new OneDatabaseHelper(OneApplication.getContext(), "OneData.db", null, 1);
    private SQLiteDatabase writedb = dbHelper.getWritableDatabase();
    private SQLiteDatabase readdb = dbHelper.getReadableDatabase();

    /**
     * 添加音乐列表的数据进数据库中
     * @param music 表示封装了添加数据的音乐列表的对象
     */
    public void addMusicList(ReadingMusicList music){
        ContentValues values = new ContentValues();
        values.put("item_id", music.getmItemId());
        values.put("title", music.getmTitle());
        values.put("forword", music.getmForword());
        values.put("img_url", music.getmImgUrl());
        values.put("last_update_date", music.getmLastUpdateDate());
        values.put("user_name", music.getmUserName());
        values.put("desc", music.getmDesc());
        writedb.insert("music_list", null, values);
    }

    /**
     * 进行数据库中的音乐列表数据的查询,且返回结果是按照id降序排列
     * @return 若查询成功,则返回存放了查询到的音乐列表对象的容器,否则,返回null
     */
    public ArrayList<ReadingMusicList> findMusicList(){
        ArrayList<ReadingMusicList> list = new ArrayList<>();
        Cursor cursor = readdb.query("music_list", null, null, null, null ,null, "id desc");
        if(cursor.moveToFirst()){
            do{
                String itemId = cursor.getString(cursor.getColumnIndex("item_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String forword = cursor.getString(cursor.getColumnIndex("forword"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("img_url"));
                String lastUpdateDate = cursor.getString(cursor.getColumnIndex("last_update_date"));
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                ReadingMusicList music = new ReadingMusicList();
                music.setmItemId(itemId);
                music.setmTitle(title);
                music.setmForword(forword);
                music.setmImgUrl(imgUrl);
                music.setmLastUpdateDate(lastUpdateDate);
                music.setmUserName(userName);
                music.setmDesc(desc);
                list.add(music);
            }while (cursor.moveToNext());
            cursor.close();
            return list;
        }
        cursor.close();
        return null;
    }
}
