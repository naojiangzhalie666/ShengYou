package com.xiaoshanghai.nancang.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseView;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface ContactContract {

    interface View extends BaseView {

    }

    interface Presenter {

        void initFragment(MagicIndicator indicator, ViewPager viewPager);
    }

}
