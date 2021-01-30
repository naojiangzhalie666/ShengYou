package com.xiaoshanghai.nancang.mvp.presenter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.xiaoshanghai.nancang.base.BaseActivity
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.ChatWithContract
import com.xiaoshanghai.nancang.mvp.ui.adapter.DynamicIndicatorAdapter
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.util.*

class ChatWithPresenter : BasePresenter<ChatWithContract.View>(), ChatWithContract.Presenter {

    private val CHANNELS = arrayOf("消息", "好友", "粉丝", "关注")

    private val mFragmetns: MutableList<Fragment> = ArrayList()

    private val mDataList = Arrays.asList(*CHANNELS)

    override fun initFragment(indicator: MagicIndicator?, viewPager: ViewPager?) {

        val fragments = initFragments(mFragmetns)

        val adapter = HomeRadioPageAdapter((view as BaseActivity).supportFragmentManager, fragments)

        viewPager!!.adapter = adapter
        viewPager!!.offscreenPageLimit = mFragmetns.size

        initMagicIndicator(indicator!!, viewPager!!, (view as Context))
    }

    /**
     * 初始化Fragment
     *
     * @param fragments
     * @return
     */
    private fun initFragments(fragments: MutableList<Fragment>): List<Fragment>? {
        val newsFragment = SpeakFragment.newInstance()
        val buddyFragment = BuddySpeakFragment.newInstance()
        val fansFragment = FansChatFragment.newInstance()
        val myAttentionFragment = SpeakAttentionFragment.newInstance()
        fragments.add(newsFragment)
        fragments.add(buddyFragment)
        fragments.add(fansFragment)
        fragments.add(myAttentionFragment)
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
        commonNavigator.adapter = DynamicIndicatorAdapter(viewPager, mDataList)
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