package com.yuzeduan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuzeduan.bean.Reading;
import com.yuzeduan.util.OneApplication;

import java.util.ArrayList;

/**
 * 对数据库中的阅读详情的数据进行增添操作和查询
 * Created by YZD on 2018/5/24.
 */

public class ReadingDao {
    private OneDatabaseHelper dbHelper = new OneDatabaseHelper(OneApplication.getContext(), "OneData.db", null, 1);
    private SQLiteDatabase writedb = dbHelper.getWritableDatabase();
    private SQLiteDatabase readdb = dbHelper.getReadableDatabase();

    /**
     * 添加阅读详情的数据到数据库中
     * @param reading 表示封装了添加数据的阅读详情对象
     */
    public void addReading(Reading reading){
        ContentValues values = new ContentValues();
        values.put("item_id", reading.getmItemId());
        values.put("title", reading.getmTitle());
        values.put("author_name", reading.getmAuthorName());
        values.put("content", reading.getmContent());
        values.put("last_update_date", reading.getmLastUpdateDate());
        writedb.insert("reading", null, values);
    }

    /**
     * 根据item_id进行查询数据库中符合的阅读详情数据
     * @param id 表示进行查询的id,其对应reading表中的item_id字段
     * @return 若查询成功,返回查到的阅读详情对象,否则,返回null
     */
    public Reading findReading(String id){
        Reading reading = new Reading();
        String selection = "item_id = ?";
        String[] selectionArgs = new  String[]{ id };
        Cursor cursor = readdb.query("reading", null, selection, selectionArgs, null ,null, null);
        if(cursor.moveToFirst()){
            String itemId = cursor.getString(cursor.getColumnIndex("item_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String authorName = cursor.getString(cursor.getColumnIndex("author_name"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String lastUpdateDate = cursor.getString(cursor.getColumnIndex("last_update_date"));
            reading.setmItemId(itemId);
            reading.setmTitle(title);
            reading.setmAuthorName(authorName);
            reading.setmContent(content);
            reading.setmLastUpdateDate(lastUpdateDate);
            cursor.close();
            return reading;
        }
        cursor.close();
        return null;
    }
}
