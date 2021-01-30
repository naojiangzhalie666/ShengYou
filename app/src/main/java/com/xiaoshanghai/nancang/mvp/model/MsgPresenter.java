package com.xiaoshanghai.nancang.mvp.model;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.callback.HomeSortCallback;
import com.xiaoshanghai.nancang.mvp.contract.MsgContract;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.SquareIndexAdapter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.ContactPersonFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.MsgFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.NewsFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MsgPresenter extends BasePresenter<MsgContract.View> implements MsgContract.Presenter {

    private static final String[] CHANNELS = new String[]{"消息","联系人"};

    private List<Fragment> mFragmetns = new ArrayList<>();

    private List<String> mDataList = Arrays.asList(CHANNELS);


    @Override
    public void initFragments(MagicIndicator indicator, ViewPager viewPager) {

        List<Fragment> fragments = initFragments(mFragmetns);

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(((MsgFragment) getView()).getChildFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mFragmetns.size());

        initMagicIndicator(indicator, viewPager, ((MsgFragment) getView()).getActivity());

    }

    /**
     * 初始化Fragment
     *
     * @param fragments
     * @return
     */
    private List<Fragment> initFragments(List<Fragment> fragments) {

        NewsFragment newsFragment = NewsFragment.newInstance();
        ContactPersonFragment contactPersonFragment = ContactPersonFragment.newInstance();
        fragments.add(newsFragment);
        fragments.add(contactPersonFragment);

        return fragments;

    }


    /**
     * 设置fragmentViewPager指示器
     *
     * @param magicIndicator
     * @param viewPager
     * @param context
     */
    private void initMagicIndicator(MagicIndicator magicIndicator, ViewPager viewPager, Context context) {

        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new SquareIndexAdapter(mDataList,magicIndicator,new HomeSortCallback<String>(){

            @Override
            public void onOpenSuccess(String result, int index) {
                viewPager.setCurrentItem(index);
            }

        }));

        magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


}
