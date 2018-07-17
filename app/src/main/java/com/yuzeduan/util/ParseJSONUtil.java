package com.yuzeduan.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.Inset;
import com.yuzeduan.bean.InsetId;
import com.yuzeduan.bean.Movie;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.bean.Music;
import com.yuzeduan.bean.Reading;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.db.InsetDao;
import com.yuzeduan.db.MovieDao;
import com.yuzeduan.db.MovieListDao;
import com.yuzeduan.db.MusicDao;
import com.yuzeduan.db.MusicListDao;
import com.yuzeduan.db.ReadingDao;
import com.yuzeduan.db.ReadingListDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.yuzeduan.bean.Constant.READING;


/**
 * 解析从api获取的JSON数据,并将解析结果存进数据库中
 * Created by YZD on 2018/5/18.
 */

public class ParseJSONUtil {
    private static Gson gson = new Gson();

    /**
     * 对获取的阅读列表数据或者音乐列表数据进行解析,并将结果封装成对象,存放在容器中返回
     * @param jsonData 表示从api中获取的关于阅读列表和音乐列表的JSON数据
     * @param flag 表示存进来的数据是阅读列表的还是阅读列表的标志
     * @return 返回存放了封装了解析结果数据的对象的容器
     */
    public static ArrayList<ReadingMusicList> parseReadingMusicList(String jsonData, int flag){
        ReadingListDao readingListDao = new ReadingListDao();
        MusicListDao musicListDao = new MusicListDao();
        String data = getJsonData(jsonData);
        ArrayList<ReadingMusicList> list = gson.fromJson(data, new TypeToken<List<ReadingMusicList>>(){}.getType());
        if(flag == READING){
            readingListDao.addReadingList(list);
        }
        else{
            musicListDao.addMusicList(list);
        }
        return list;
    }

    /**
     * 对从api获取的影视列表的JSON数据进行解析,并封装为一个个对象,存放在容器中返回
     * @param jsonData 表示从api中获取的影视列表的JSON数据
     * @return 返回存放了封装了JSON数据解析后的数据Bean对象的容器
     */
    public static ArrayList<MovieList> parseMovieList(String jsonData){
        MovieListDao movieListDao = new MovieListDao();
        String data = getJsonData(jsonData);
        ArrayList<MovieList> list = gson.fromJson(data, new TypeToken<List<MovieList>>(){}.getType());
        movieListDao.addMovieList(list);
        return list;
    }

    /**
     * 对获取的插画id的JSON数据进行解析
     * @param jsonData 从api中获取的存放了10个插画id的JSON数据
     * @return 返回存放了JSON数据进行解析10个获得的插画id封装成Bean对象的容器
     */
    public static ArrayList<InsetId> parseInsetId(String jsonData){
        try {
            ArrayList<InsetId> idList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");
            for(int i = 0; i < data.length(); i++){
                String id = data.getString(i);
                InsetId insetId = new InsetId();
                insetId.setmInsetId(id);
                idList.add(insetId);
            }
            return idList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析关于阅读详情的JSON数据,并将解析结果封装成对象,作为返回值
     * @param jsonData 表示从api中获取的关于阅读的JSON数据
     * @return 返回封装了解析结果数据的对象
     */
    public static Reading parseReading(String jsonData){
        ReadingDao readingDao = new ReadingDao();
        String data = getJsonData(jsonData);
        Reading reading = gson.fromJson(data, Reading.class);
        readingDao.addReading(reading);
        return reading;
    }

    /**
     * 对音乐的JSON数据进行解析,并将解析后的数据封装为一个Bean对象返回
     * @param jsonData 表示从api中获取的音乐的JSON数据
     * @return 返回封装了解析后的数据的对象
     */
    public static Music parseMusic(String jsonData){
        MusicDao musicDao = new MusicDao();
        String data = getJsonData(jsonData);
        Music music = gson.fromJson(data, Music.class);
        musicDao.addMusic(music);
        return music;
    }

    /**
     *解析影视的JSON数据,将其封装为Bean对象,并返回
     * @param jsonData 从api中获取的影视JSON数据
     * @return 返回封装了解析后的影视数据的影视对象
     */
    public static Movie parseMovie(String jsonData) {
        try {
            MovieDao movieDao = new MovieDao();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            String datas = data.getString("data");
            List<Movie> movie = gson.fromJson(datas, new TypeToken<List<Movie>>(){}.getType());
            movieDao.addMovie(movie.get(0));
            return movie.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析插画的JSON数据
     * @param jsonData 表示从api中获取的插画JSON数据
     * @return 将解析的JSON数据封装成一个插画Bean对象,并返回
     */
    public static Inset parseInset(String jsonData){
        InsetDao insetDao = new InsetDao();
        String data = getJsonData(jsonData);
        Inset inset = gson.fromJson(data, Inset.class);
        insetDao.addInset(inset);
        return inset;
    }

    /**
     * 对网络获取的关于评论的JSON数据进行解析,
     * @param jsonData 表示从api中获取的JSON数据
     * @return 解析获得的数据封装为一个个Bean对象,并存放在容器中作为返回值
     */
    public static ArrayList<Comment> parseComment(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            String datas = data.getString("data");
            ArrayList<Comment>list = gson.fromJson(datas, new TypeToken<List<Comment>>(){}.getType());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getJsonData(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return jsonObject.getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
