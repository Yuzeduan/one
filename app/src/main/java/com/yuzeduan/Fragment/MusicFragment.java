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

import com.yuzeduan.activity.MusicContentActivity;
import com.yuzeduan.activity.R;
import com.yuzeduan.adapter.MusicAdapter;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.model.MusicListCallback;
import com.yuzeduan.model.MusicListModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class MusicFragment extends Fragment{
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<ReadingMusicList> mMusicList;
    private MusicListModel mMusicListModel = new MusicListModel();

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
        mMusicListModel.getMusicListData(MUSICLIST_URL, new MusicListCallback() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onFinish(List<ReadingMusicList> list) {
                mMusicList = list;
                MusicAdapter adapter = new MusicAdapter(getActivity(), mMusicList, R.layout.music_item);
                mListView.setAdapter(adapter);
                // 给列表设置点击事件监听器,获取子项的具体item_id,传给下一个活动
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ReadingMusicList music = mMusicList.get(position);
                        String item_id = music.getmItemId();
                        Intent intent = new Intent(getActivity(), MusicContentActivity.class);
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
