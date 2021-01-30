package com.xiaoshanghai.nancang.mvp.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class HomeRadioPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public HomeRadioPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
