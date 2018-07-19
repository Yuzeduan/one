package com.yuzeduan.model;

import com.yuzeduan.bean.MovieList;
import com.yuzeduan.db.MovieListDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;

import static com.yuzeduan.bean.Constant.REFRESH_DATA;

import java.util.List;

public class MovieListModel {

    public void getMovieListData(String url, MovieListCallback callback){
        MovieListDao movieListDao = new MovieListDao();
        List<MovieList> movieList= movieListDao.findMovieList();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if(movieList != null){
            callback.onFinish(movieList);
        }else{
            queryMovieListData(url, 0, callback);
        }
    }

    public void queryMovieListData(final String url, final int flag, final MovieListCallback callback){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ParseJSONUtil.parseMovieList(response);
                if(callback != null){
                    if(flag == REFRESH_DATA){
                        callback.onRefresh();
                    }else{
                        getMovieListData(url, callback);
                    }
                }
            }
        });
    }
}
