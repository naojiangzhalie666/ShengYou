package com.xiaoshanghai.nancang.mvp.presenter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.callback.HomeSortCallback;
import com.xiaoshanghai.nancang.mvp.contract.Squarev1Constract;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.SquareIndexAdapter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.square.RecommendFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.square.SquareV1Fragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

public class Squarev1Presenter extends BasePresenter<Squarev1Constract.View> implements Squarev1Constract.Presenter {

    @Override
    public void initFragment(MagicIndicator index, ViewPager viewPager) {
        RecommendFragment recommendFragment = new RecommendFragment();
//        AttentionFragment attentionFragment = new AttentionFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(recommendFragment);
//        fragments.add(attentionFragment);

        FragmentManager supportFragmentManager = ((SquareV1Fragment) getView()).getChildFragmentManager();

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(supportFragmentManager, fragments);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        List<String> tables = new ArrayList<>();
        tables.add("推荐");
        tables.add("关注");


        CommonNavigator commonNavigator = new CommonNavigator(((SquareV1Fragment) getView()).getActivity());
        commonNavigator.setAdapter(new SquareIndexAdapter(tables, index, new HomeSortCallback<String>() {

            @Override
            public void onOpenSuccess(String result, int index) {
                viewPager.setCurrentItem(index);
            }

        }));

        index.setNavigator(commonNavigator);

        ViewPagerHelper.bind(index, viewPager);
    }
}
