package com.yuzeduan.model;

import com.yuzeduan.bean.MovieList;

import java.util.List;

public interface MovieListCallback {
    void onRefresh();
    void onFinish(List<MovieList> list);
}
