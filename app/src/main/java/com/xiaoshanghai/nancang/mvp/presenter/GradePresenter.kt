package com.xiaoshanghai.nancang.mvp.presenter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.xiaoshanghai.nancang.base.BaseActivity
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.GradeContract
import com.xiaoshanghai.nancang.mvp.ui.adapter.GradeIndicatorAdapter
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.MyCharmLvFragment
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.MyUserLvFragment
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.util.*

class GradePresenter:BasePresenter<GradeContract.View>(),GradeContract.Presenter {

    private val CHANNELS = arrayOf("用户等级", "魅力等级")

    private val mFragmetns: MutableList<Fragment> = ArrayList()

    private val mDataList = Arrays.asList(*CHANNELS)

    override fun initFragment(viewPager: ViewPager, index: MagicIndicator) {

        val fragments = initFragments(mFragmetns)

        val adapter = HomeRadioPageAdapter((view as BaseActivity).supportFragmentManager, fragments)

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

        val myUserLvFragment = MyUserLvFragment.newInstance()
        val myCharmLvFragment = MyCharmLvFragment.newInstance()
        fragments.add(myUserLvFragment)
        fragments.add(myCharmLvFragment)

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
        commonNavigator.adapter = GradeIndicatorAdapter(viewPager, mDataList)
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
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                fragmentContainerHelper.handlePageSelected(position)
            }
        })
    }
}