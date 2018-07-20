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

import com.yuzeduan.activity.R;
import com.yuzeduan.activity.ReadingContentActivity;
import com.yuzeduan.adapter.CommonAdapter;
import com.yuzeduan.adapter.ReadingAdapter;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.model.ReadingListCallback;
import com.yuzeduan.model.ReadingListModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.READINGLIST_URL;
import static com.yuzeduan.bean.Constant.NEW_READINGLIST_URL;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class ReadingFragment extends BaseFragment{
    private View mView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<ReadingMusicList> mReadingList;
    private ReadingListModel mReadingListModel = new ReadingListModel();

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
        setView();
        refreshView();
        return mView;
    }

    public void setView() {
        if(isVisible && isPrepared){
            mReadingListModel.getReadingListData(READINGLIST_URL, new ReadingListCallback() {
                @Override
                public void onRefresh() {
                }

                @Override
                public void onFinish(List<ReadingMusicList> list) {
                    mReadingList = list;
                    ReadingAdapter adapter = new ReadingAdapter(getActivity(), mReadingList, R.layout.reading_item);
                    adapter.setmOnItemClickListener(new CommonAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClickListener(int position) {
                            ReadingMusicList item = mReadingList.get(position);
                            String item_id = item.getmItemId();
                            Intent intent = new Intent(getActivity(), ReadingContentActivity.class);
                            intent.putExtra("id", item_id);
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
