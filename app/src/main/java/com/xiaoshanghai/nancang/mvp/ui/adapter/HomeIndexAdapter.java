package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.HomeSortCallback;
import com.xiaoshanghai.nancang.net.bean.HomeSortResult;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.List;

/**
 * Created by lxy on 2019/11/7.
 */

public class HomeIndexAdapter extends CommonNavigatorAdapter {

    private MagicIndicator mIndex;
    private List<HomeSortResult> mProductList;
    private HomeSortCallback mOpencallback;

    public HomeIndexAdapter(List<HomeSortResult> mProductList, MagicIndicator mIndex, HomeSortCallback openCallback) {
        this.mProductList = mProductList;
        this.mIndex = mIndex;
        this.mOpencallback = openCallback;
    }

    @Override
    public int getCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    @SuppressLint("ResourceType")
    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {

        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

        // load custom layout
        View customLayout = LayoutInflater.from(context).inflate(R.layout.view_home_sort, null);
        final TextView titleText = customLayout.findViewById(R.id.title_text);
        titleText.setText(mProductList.get(index).getRoomTypeName());
        commonPagerTitleView.setContentView(customLayout);

        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

            @Override
            public void onSelected(int index, int totalCount) {
                titleText.setTextColor(Color.BLACK);
                titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                titleText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            }

            @Override
            public void onDeselected(int index, int totalCount) {
                titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                titleText.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                titleText.setTextColor(context.getResources().getColor(R.color.color_999999));
            }

            @Override
            public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
//                titleText.setScaleX(1.0f * leavePercent);
//                titleText.setScaleY(1.0f * leavePercent);
            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
//                titleText.setScaleX(1.3f * enterPercent);
//                titleText.setScaleY(1.3f * enterPercent);
            }
        });

        commonPagerTitleView.setOnClickListener(v -> {
            mIndex.onPageSelected(index);
            mIndex.onPageScrolled(index, 0.0F, 0);
            if (mOpencallback != null) {
                mOpencallback.onOpenSuccess(mProductList.get(index),index);
            }
        });

        return commonPagerTitleView;

    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
        indicator.setLineColor(Color.parseColor("#e94220"));
        return null;
    }
}
