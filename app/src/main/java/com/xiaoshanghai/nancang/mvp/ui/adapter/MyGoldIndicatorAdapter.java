package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.List;

public class MyGoldIndicatorAdapter extends CommonNavigatorAdapter {

    private ViewPager mViewPager;
    private List<String> mDataList;

    public MyGoldIndicatorAdapter(ViewPager viewPager, List<String> dataList) {
        this.mViewPager = viewPager;
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
        simplePagerTitleView.setNormalColor(Color.parseColor("#848484"));
        simplePagerTitleView.setSelectedColor(Color.BLACK);
        simplePagerTitleView.setText(mDataList.get(index));
        simplePagerTitleView.setTextSize(16);
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 14));
        linePagerIndicator.setColors(Color.parseColor("#FF7B9B"));
        return linePagerIndicator;
    }

    @Override
    public float getTitleWeight(Context context, int index) {
        return 1.0f;
    }
}
