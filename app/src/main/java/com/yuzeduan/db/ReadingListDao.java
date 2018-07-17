package com.yuzeduan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuzeduan.bean.Author;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.util.OneApplication;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * SQLite中的阅读列表数据的添加和查询
 * Created by YZD on 2018/5/24.
 */

public class ReadingListDao {
    private OneDatabaseHelper dbHelper = new OneDatabaseHelper(OneApplication.getContext(), "OneData.db", null, 1);
    private SQLiteDatabase writedb = dbHelper.getWritableDatabase();
    private SQLiteDatabase readdb = dbHelper.getReadableDatabase();

    /**
     * 添加阅读列表的数据进数据库中
     * @param list 表示盛放封装了添加数据的阅读列表的对象的容器
     */
    public void addReadingList(ArrayList<ReadingMusicList> list){
        Iterator<ReadingMusicList> iterator = list.iterator();
        ReadingMusicList reading;
        while(iterator.hasNext()){
            reading = iterator.next();
            ContentValues values = new ContentValues();
            values.put("item_id", reading.getmItemId());
            values.put("title", reading.getmTitle());
            values.put("forword", reading.getmForword());
            values.put("img_url", reading.getmImgUrl());
            values.put("last_update_date", reading.getmLastUpdateDate());
            values.put("user_name", reading.getmAuthor().getmUserName());
            values.put("desc", reading.getmAuthor().getmDesc());
            writedb.insert("reading_list", null, values);
        }
    }

    /**
     * 进行数据库中的阅读列表数据的查询,且返回结果是按照id降序排列
     * @return 若查询成功,则返回存放了查询到的阅读列表对象的容器,否则,返回null
     */
    public ArrayList<ReadingMusicList> findReadingList(){
        ArrayList<ReadingMusicList> list = new ArrayList<>();
        Cursor cursor = readdb.query("reading_list", null, null, null, null ,null, "id desc","15");
        if(cursor.moveToFirst()){
            do{
                String itemId = cursor.getString(cursor.getColumnIndex("item_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String forword = cursor.getString(cursor.getColumnIndex("forword"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("img_url"));
                String lastUpdateDate = cursor.getString(cursor.getColumnIndex("last_update_date"));
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                ReadingMusicList reading = new ReadingMusicList();
                reading.setmItemId(itemId);
                reading.setmTitle(title);
                reading.setmForword(forword);
                reading.setmImgUrl(imgUrl);
                reading.setmLastUpdateDate(lastUpdateDate);
                Author author = new Author();
                author.setmUserName(userName);
                author.setmDesc(desc);
                reading.setmAuthor(author);
                list.add(reading);
            }while (cursor.moveToNext());
            cursor.close();
            return list;
        }
        cursor.close();
        return null;
    }
}
