package com.yuzeduan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzeduan.activity.MusicContentActivity;
import com.yuzeduan.activity.R;
import com.yuzeduan.adapter.CommonAdapter;
import com.yuzeduan.adapter.MusicAdapter;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.model.MusicListCallback;
import com.yuzeduan.model.MusicListModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class MusicFragment extends BaseFragment{
    private View mView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<ReadingMusicList> mMusicList;
    private MusicListModel mMusicListModel = new MusicListModel();

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
            mMusicListModel.getMusicListData(MUSICLIST_URL, new MusicListCallback() {
                @Override
                public void onRefresh() {
                }

                @Override
                public void onFinish(List<ReadingMusicList> list) {
                    mMusicList = list;
                    MusicAdapter adapter = new MusicAdapter(getActivity(), mMusicList, R.layout.music_item);
                    adapter.setmOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClickListener(int position) {
                            ReadingMusicList item = mMusicList.get(position);
                            String itemId = item.getmItemId();
                            Intent intent = new Intent(getActivity(), MusicContentActivity.class);
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
                mMusicListModel.queryMusicListData(NEW_MUSICLIST_URL, REFRESH_DATA, new MusicListCallback() {
                    @Override
                    public void onRefresh() {
                        setView();
                    }

                    @Override
                    public void onFinish(List<ReadingMusicList> list) {
                    }
                });
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}
