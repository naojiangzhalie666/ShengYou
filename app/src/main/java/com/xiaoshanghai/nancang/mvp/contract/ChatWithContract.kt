package com.xiaoshanghai.nancang.mvp.contract

import androidx.viewpager.widget.ViewPager
import com.xiaoshanghai.nancang.base.BaseView
import net.lucode.hackware.magicindicator.MagicIndicator

interface ChatWithContract {

    interface View: BaseView {

    }

    interface Presenter {

        fun initFragment(indicator: MagicIndicator?, viewPager: ViewPager?)

    }

    interface Model {

    }
}