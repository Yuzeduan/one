package com.yuzeduan.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuzeduan.bean.Inset;
import com.yuzeduan.util.OneApplication;

import java.util.ArrayList;

/**
 * 插画数据库信息的增加和查询操作
 * Created by YZD on 2018/5/24.
 */

public class InsetDao {
    private OneDatabaseHelper dbHelper = new OneDatabaseHelper(OneApplication.getContext(), "OneData.db", null, 1);
    private SQLiteDatabase writedb = dbHelper.getWritableDatabase();
    private SQLiteDatabase readdb = dbHelper.getReadableDatabase();

    /**
     * 添加插画信息进数据库
     * @param inset 表示封装了添加数据的插画对象
     */
    public void addInset(Inset inset){
        ContentValues values = new ContentValues();
        values.put("title", inset.getmTitle());
        values.put("img_url", inset.getmImgUrl());
        values.put("content", inset.getmContent());
        values.put("image_author", inset.getmImageAuthor());
        values.put("hp_author", inset.getmHpAuthor());
        values.put("last_update_date", inset.getmLastUpdateDate());
        writedb.insert("inset", null, values);
    }

    /**
     *进行插画的查询,且返回结果按照id降序排列
     * @return 若查询成功,则返回存放了查找的插画对象的容器,否则,返回null
     */
    public ArrayList<Inset> findInset(){
        ArrayList<Inset> list = new ArrayList<>();
        Cursor cursor = readdb.query("inset", null, null, null, null ,null, "id desc","15");
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("img_url"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String imageAuthor = cursor.getString(cursor.getColumnIndex("image_author"));
                String hpAuthor = cursor.getString(cursor.getColumnIndex("hp_author"));
                String lastUpdateDate = cursor.getString(cursor.getColumnIndex("last_update_date"));
                Inset inset = new Inset();
                inset.setmTitle(title);
                inset.setmImgUrl(imgUrl);
                inset.setmContent(content);
                inset.setmImageAuthor(imageAuthor);
                inset.setmHpAuthor(hpAuthor);
                inset.setmLastUpdateDate(lastUpdateDate);
                list.add(inset);
            }while (cursor.moveToNext());
            cursor.close();
            return list;
        }
        cursor.close();
        return null;
    }
}
