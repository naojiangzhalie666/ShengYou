package com.xiaoshanghai.nancang.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseView;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface MsgContract {

    interface View extends BaseView {

    }

    interface Presenter {

        void initFragments(MagicIndicator indicator, ViewPager viewPager);

    }

    interface Model {

    }
}
