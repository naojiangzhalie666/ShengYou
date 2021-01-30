package com.xiaoshanghai.nancang.mvp.presenter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.GoldCoinGiftContract
import com.xiaoshanghai.nancang.mvp.model.GoldCoinGiftModel
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.GoldCoinGiftAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyGoldIndicatorAdapter
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.ChiliGiftIncomeFragment
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.GoldGiftIncomeFragment
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.util.*

class GoldCoinGiftPresenter : BasePresenter<GoldCoinGiftContract.View>(), GoldCoinGiftContract.Presenter {

    var model: GoldCoinGiftModel = GoldCoinGiftModel()

    private val CHANNELS = arrayOf("金币礼物", "辣椒礼物")

    private val mFragmetns: MutableList<Fragment> = ArrayList()

    private val mDataList = Arrays.asList(*CHANNELS)


    override fun initFragment(viewPager: ViewPager, index: MagicIndicator) {
        val fragments = initFragments(mFragmetns)

        val adapter = HomeRadioPageAdapter((view as GoldCoinGiftAct).supportFragmentManager, fragments)

        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = mFragmetns.size

        initMagicIndicator(index, viewPager, (view as Context))
    }


    /**
     * 初始化Fragment
     *
     * @param fragments
     * @return
     */
    private fun initFragments(fragments: MutableList<Fragment>): List<Fragment>? {

        val goldGiftIncome = GoldGiftIncomeFragment.newInstance()
        val chiliIncome = ChiliGiftIncomeFragment.newInstance()
        fragments.add(goldGiftIncome)
        fragments.add(chiliIncome)

        return fragments
    }

    /**
     * 设置fragmentViewPager指示器
     *
     * @param magicIndicator
     * @param viewPager
     * @param context
     */
    private fun initMagicIndicator(magicIndicator: MagicIndicator, viewPager: ViewPager, context: Context) {
        val commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = MyGoldIndicatorAdapter(viewPager, mDataList)
        commonNavigator.isAdjustMode = true
        magicIndicator.navigator = commonNavigator
        val titleContainer = commonNavigator.titleContainer // must after setNavigator
        titleContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerDrawable = object : ColorDrawable() {
            override fun getIntrinsicWidth(): Int {
                return UIUtil.dip2px(context, 15.0)
            }
        }
        val fragmentContainerHelper = FragmentContainerHelper(magicIndicator)
        fragmentContainerHelper.setInterpolator(OvershootInterpolator(2.0f))
        fragmentContainerHelper.setDuration(300)
        viewPager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                fragmentContainerHelper.handlePageSelected(position)
            }
        })
    }

}