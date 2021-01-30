package com.xiaoshanghai.nancang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;


public class ScrollViewPager extends ViewPager {


    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewPager(Context context) {
        super(context);
    }


    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v != this && v instanceof ViewPager) {
            int currentItem = ((ViewPager) v).getCurrentItem();
            int countItem = ((ViewPager) v).getAdapter().getCount();
            if ((currentItem == (countItem - 1) && dx < 0) || (currentItem == 0 && dx > 0)) {
                return false;
            }
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}