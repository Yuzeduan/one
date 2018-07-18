package com.yuzeduan.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yuzeduan.activity.R;
import com.yuzeduan.adapter.InsetAdapter;
import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.Inset;
import com.yuzeduan.db.InsetDao;
import com.yuzeduan.model.CallbackListener;
import com.yuzeduan.model.ListDataModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.INSET;
import static com.yuzeduan.bean.Constant.INSETID_URL;
import static com.yuzeduan.bean.Constant.NEW_INSETID_URL;

public class InsetFragment extends Fragment{
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<Inset> mInsetList;
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
        InsetDao insetDao = new InsetDao();
        mInsetList = insetDao.findInset();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if (mInsetList != null) {
            InsetAdapter adapter = new InsetAdapter(getActivity(), mInsetList, R.layout.inset_item);
            mListView.setAdapter(adapter);
        }
        else{
            listDataModel.queryDataFromServer(INSETID_URL, INSET, new CallbackListener() {
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
                listDataModel.queryDataFromServer(NEW_INSETID_URL, INSET, new CallbackListener() {
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
