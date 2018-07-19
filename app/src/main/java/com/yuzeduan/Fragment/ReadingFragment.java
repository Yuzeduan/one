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

import com.yuzeduan.activity.R;
import com.yuzeduan.activity.ReadingContentActivity;
import com.yuzeduan.adapter.ReadingAdapter;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.model.ReadingListCallback;
import com.yuzeduan.model.ReadingListModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.READINGLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_READINGLIST_URL;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class ReadingFragment extends Fragment{
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<ReadingMusicList> mReadingList;
    private ReadingListModel mReadingListModel = new ReadingListModel();

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

    public void setView() {
        mReadingListModel.getReadingListData(READINGLIST_URL, new ReadingListCallback() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onFinish(List<ReadingMusicList> list) {
                mReadingList = list;
                ReadingAdapter adapter = new ReadingAdapter(getActivity(), mReadingList, R.layout.reading_item);
                mListView.setAdapter(adapter);
                // 给列表设置点击事件监听器,获取子项的具体item_id,传给下一个活动
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ReadingMusicList reading = mReadingList.get(position);
                        String item_id = reading.getmItemId();
                        Intent intent = new Intent(getActivity(), ReadingContentActivity.class);
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
                 mReadingListModel.queryReadingListData(NEW_READINGLIST_URL, REFRESH_DATA, new ReadingListCallback() {
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
