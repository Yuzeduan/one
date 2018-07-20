package com.yuzeduan.Fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected boolean isVisible;  // 用于判断Fragment是否可见
    protected boolean isPrepared = false;
    protected boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            setView();
            isFirst = false;
        }
    }

    public abstract void setView();
    public abstract void refreshView();
}
