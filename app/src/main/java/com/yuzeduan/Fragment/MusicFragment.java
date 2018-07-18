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
import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.db.MusicListDao;
import com.yuzeduan.model.CallbackListener;
import com.yuzeduan.model.ListDataModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.MUSIC;
import static com.yuzeduan.bean.Constant.MUSICLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_MUSICLIST_URL;

public class MusicFragment extends Fragment{
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<ReadingMusicList> mMusicList;
    private ListDataModel listDataModel = new ListDataModel();

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
        MusicListDao musicListDao = new MusicListDao();
        mMusicList = musicListDao.findMusicList();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if (mMusicList != null) {
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
        else{
            listDataModel.queryDataFromServer(MUSICLIST_URL, MUSIC, new CallbackListener() {
                @Override
                public void onFinish() {
                    setView();
                }

                @Override
                public void onStringFinish(List<Comment> list) {
                }
            });
        }
    }

    public void refreshView(){
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                listDataModel.queryDataFromServer(NEW_MUSICLIST_URL, MUSIC, new CallbackListener() {
                    @Override
                    public void onFinish() {
                        setView();
                    }

                    @Override
                    public void onStringFinish(List<Comment> list) {
                    }
                });
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}
