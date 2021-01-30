package com.xiaoshanghai.nancang.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseView;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface MyOutfitConstract {

    interface View extends BaseView{

    }

    interface Presenter {
        /**
         * 初始化fragment
         * @param viewPager
         * @param indicator
         */
        void initFragments(ViewPager viewPager, MagicIndicator indicator);
    }

    interface Model {

    }

}
