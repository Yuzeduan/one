package com.yuzeduan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzeduan.activity.MovieContentActivity;
import com.yuzeduan.activity.R;
import com.yuzeduan.adapter.CommonAdapter;
import com.yuzeduan.adapter.MovieAdapter;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.model.MovieListCallback;
import com.yuzeduan.model.MovieListModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.NEW_MOVIELIST_URL;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class MovieFragment extends BaseFragment{
    private View mView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<MovieList> mMovieList;
    private MovieListModel mMovieListModel = new MovieListModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment, container, false);
        }
        mRecyclerView = mView.findViewById(R.id.main_rv_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mSwipeRefresh = mView.findViewById(R.id.swipe_refresh);
        isPrepared = true;
        refreshView();
        return mView;
    }

    public void setView(){
        if(isPrepared && isVisible && isFirst){
            mMovieListModel.getMovieListData(MOVIELIST_URL, new MovieListCallback() {
                @Override
                public void onRefresh() {
                }

                @Override
                public void onFinish(List<MovieList> list) {
                    mMovieList = list;
                    MovieAdapter adapter = new MovieAdapter(getActivity(), mMovieList, R.layout.movie_item);
                    adapter.setmOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClickListener(int position) {
                            MovieList item = mMovieList.get(position);
                            String itemId = item.getmItemId();
                            Intent intent = new Intent(getActivity(), MovieContentActivity.class);
                            intent.putExtra("id", itemId);
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setAdapter(adapter);
                }
            });
        }
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
