package com.yuzeduan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 关于数据库的创建和数据库表的创建
 * Created by YZD on 2018/5/24.
 */

public class OneDatabaseHelper extends SQLiteOpenHelper{
    private static final String CREATE_READINGLIST = "create table reading_list("
            + "id integer primary key autoincrement,"
            + "item_id text,"
            + "title text,"
            + "forword text,"
            + "img_url text,"
            + "last_update_date text,"
            + "user_name text,"
            + "desc text)";

    private static final String CREATE_MUSICLIST = "create table music_list("
            + "id integer primary key autoincrement,"
            + "item_id text,"
            + "title text,"
            + "forword text,"
            + "img_url text,"
            + "last_update_date text,"
            + "user_name text,"
            + "desc text)";

    private static final String CREATE_MOVIELIST = "create table movie_list("
            + "id integer primary key autoincrement,"
            + "item_id text,"
            + "title text,"
            + "forword text,"
            + "img_url text,"
            + "last_update_date text,"
            + "subtitle text,"
            + "user_name text,"
            + "desc text)";

    private static final String CREATE_READING = "create table reading("
            + "id integer primary key autoincrement,"
            + "item_id text,"
            + "title text,"
            + "author_name text,"
            + "content text,"
            + "last_update_date text)";

    private static final String CREATE_MUSIC = "create table music("
            + "id integer primary key autoincrement,"
            + "item_id text,"
            + "music_title text,"
            + "cover text,"
            + "story_title text,"
            + "story_content text,"
            + "last_update_date text,"
            + "music_user_name text,"
            + "story_author_name text)";

    private static final String CREATE_MOVIE = "create table movie("
            + "id integer primary key autoincrement,"
            + "item_id text,"
            + "title text,"
            + "content text,"
            + "input_date text,"
            + "author_name text)";

    private static final String CREATE_INSET = "create table inset("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "img_url text,"
            + "content text,"
            + "image_author text,"
            + "hp_author text,"
            + "last_update_date text)";

    public OneDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_READINGLIST);
        db.execSQL(CREATE_MUSICLIST);
        db.execSQL(CREATE_MOVIELIST);
        db.execSQL(CREATE_READING);
        db.execSQL(CREATE_MUSIC);
        db.execSQL(CREATE_MOVIE);
        db.execSQL(CREATE_INSET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
