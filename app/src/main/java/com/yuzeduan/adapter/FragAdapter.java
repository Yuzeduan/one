package com.yuzeduan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragments;
    private List<String> mTitleList;

    public FragAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> mTitleList) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
