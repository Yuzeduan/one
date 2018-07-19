package com.yuzeduan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuzeduan.activity.MovieContentActivity;
import com.yuzeduan.activity.R;
import com.yuzeduan.adapter.MovieAdapter;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.model.MovieListCallback;
import com.yuzeduan.model.MovieListModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.NEW_MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class MovieFragment extends Fragment{
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<MovieList> mMovieList;
    private MovieListModel mMovieListModel = new MovieListModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        mListView = view.findViewById(R.id.main_lv_list);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        setView();
        refreshView();
        return view;
    }

    public void setView(){
        mMovieListModel.getMovieListData(MOVIELIST_URL, new MovieListCallback() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onFinish(List<MovieList> list) {
                mMovieList = list;
                MovieAdapter adapter = new MovieAdapter(getActivity(), mMovieList, R.layout.movie_item);
                mListView.setAdapter(adapter);
                // 给列表设置点击事件监听器,获取子项的具体item_id,传给下一个活动
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MovieList movie = mMovieList.get(position);
                        String item_id = movie.getmItemId();
                        Intent intent = new Intent(getActivity(), MovieContentActivity.class);
                        intent.putExtra("id", item_id);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void refreshView(){
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                mMovieListModel.queryMovieListData(NEW_MOVIELIST_URL, REFRESH_DATA, new MovieListCallback() {
                    @Override
                    public void onRefresh() {
                        setView();
                    }

                    @Override
                    public void onFinish(List<MovieList> list) {
                    }
                });
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}
