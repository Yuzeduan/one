package com.yuzeduan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuzeduan.bean.Movie;
import com.yuzeduan.util.OneApplication;

/**
 * 进行数据库中影视详情的数据的增加和查询操作
 * Created by YZD on 2018/5/24.
 */

public class MovieDao {
    private OneDatabaseHelper dbHelper = new OneDatabaseHelper(OneApplication.getContext(), "OneData.db", null, 1);
    private SQLiteDatabase writedb = dbHelper.getWritableDatabase();
    private SQLiteDatabase readdb = dbHelper.getReadableDatabase();

    /**
     * 进行影视详情数据的增添操作
     * @param movie 表示封装了添加数据的影视详情对象
     */
    public void addMovie(Movie movie){
        ContentValues values = new ContentValues();
        values.put("item_id", movie.getmItemId());
        values.put("title", movie.getmTitle());
        values.put("content", movie.getmContent());
        values.put("input_date", movie.getmInputDate());
        values.put("author_name", movie.getmAuthorName());
        writedb.insert("movie", null, values);
    }

    /**
     * 根据传入的item_id进行查询数据库中的影视详情信息
     * @param id 表示根据进行查询的id,对应movie表中的item_id字段
     * @return 若查询成功,则返回封装了数据影视详情对象,否则,返回null
     */
    public Movie findMovie(String id){
        Movie movie = new Movie();
        String selection = "item_id = ?";
        String[] selectionArgs = new  String[]{ id };
        Cursor cursor = readdb.query("movie", null, selection, selectionArgs, null ,null, null);
        if(cursor.moveToFirst()){
            String itemId = cursor.getString(cursor.getColumnIndex("item_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String inputDate = cursor.getString(cursor.getColumnIndex("input_date"));
            String authorName = cursor.getString(cursor.getColumnIndex("author_name"));
            movie.setmItemId(itemId);
            movie.setmTitle(title);
            movie.setmContent(content);
            movie.setmInputDate(inputDate);
            movie.setmAuthorName(authorName);
            cursor.close();
            return movie;
        }
        cursor.close();
        return null;
    }
}
