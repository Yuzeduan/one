package com.yuzeduan.model;

import com.yuzeduan.bean.Movie;
import com.yuzeduan.db.MovieDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;

public class MovieContentModel {
    public void getMovieData(String itemId, String url, MovieContentCallback callback){
        MovieDao movieDao = new MovieDao();
        Movie movie = movieDao.findMovie(itemId);
        if(movie != null){
            callback.onFinish(movie);
        }else{
            queryMovieData(itemId, url, callback);
        }
    }

    public void queryMovieData(final String itemId, final String url, final MovieContentCallback callback){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseMovie(response);
                getMovieData(itemId, url, callback);
            }
        });
    }
}
