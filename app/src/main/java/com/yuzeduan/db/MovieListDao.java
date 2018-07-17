package com.yuzeduan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuzeduan.bean.Author;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.util.OneApplication;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 对数据库中的影视列表的数据进行增添和查询操作
 * Created by YZD on 2018/5/24.
 */

public class MovieListDao {
    private OneDatabaseHelper dbHelper = new OneDatabaseHelper(OneApplication.getContext(), "OneData.db", null, 1);
    private SQLiteDatabase writedb = dbHelper.getWritableDatabase();
    private SQLiteDatabase readdb = dbHelper.getReadableDatabase();

    /**
     * 添加影视列表的数据进数据库
     * @param list 表示盛放封装了添加数据的影视列表对象的容器
     */
    public void addMovieList(ArrayList<MovieList> list){
        Iterator<MovieList> iterator = list.iterator();
        MovieList movie;
        while(iterator.hasNext()){
            movie = iterator.next();
            ContentValues values = new ContentValues();
            values.put("item_id", movie.getmItemId());
            values.put("title", movie.getmTitle());
            values.put("forword", movie.getmForword());
            values.put("img_url", movie.getmImgUrl());
            values.put("last_update_date", movie.getmLastUpdateDate());
            values.put("subtitle", movie.getmSubtitle());
            values.put("user_name", movie.getmAuthor().getmUserName());
            values.put("desc", movie.getmAuthor().getmDesc());
            writedb.insert("movie_list", null, values);
        }
    }

    /**
     * 进行数据库中影视列表数据的查询操作,且返回的结果按照id降序排列
     * @return 若查询成功,则返回存放了查询到的影视列表对象的容器,否则,返回null
     */
    public ArrayList<MovieList> findMovieList(){
        ArrayList<MovieList> list = new ArrayList<>();
        Cursor cursor = readdb.query("movie_list", null, null, null, null ,null, "id desc", "15");
        if(cursor.moveToFirst()){
            do{
                String itemId = cursor.getString(cursor.getColumnIndex("item_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String forword = cursor.getString(cursor.getColumnIndex("forword"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("img_url"));
                String lastUpdateDate = cursor.getString(cursor.getColumnIndex("last_update_date"));
                String subTitle = cursor.getString(cursor.getColumnIndex("subtitle"));
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                MovieList movie = new MovieList();
                movie.setmItemId(itemId);
                movie.setmTitle(title);
                movie.setmForword(forword);
                movie.setmImgUrl(imgUrl);
                movie.setmLastUpdateDate(lastUpdateDate);
                movie.setmSubtitle(subTitle);
                Author author = new Author();
                author.setmUserName(userName);
                author.setmDesc(desc);
                movie.setmAuthor(author);
                list.add(movie);
            }while (cursor.moveToNext());
            cursor.close();
            return list;
        }
        cursor.close();
        return null;
    }
}
