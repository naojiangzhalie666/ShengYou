package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.ContactContract;
import com.xiaoshanghai.nancang.mvp.ui.adapter.DynamicIndicatorAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.BuddyFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.ContactPersonFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.FansFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.msg.MyAttentionFragment;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactPresenter extends BasePresenter<ContactContract.View> implements ContactContract.Presenter {

    private static final String[] CHANNELS = new String[]{"好友", "粉丝", "关注"};

    private List<Fragment> mFragmetns = new ArrayList<>();

    private List<String> mDataList = Arrays.asList(CHANNELS);


    @Override
    public void initFragment(MagicIndicator indicator, ViewPager viewPager) {
        List<Fragment> fragments = initFragments(mFragmetns);

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(((ContactPersonFragment) getView()).getChildFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mFragmetns.size());

        initMagicIndicator(indicator, viewPager, ((ContactPersonFragment) getView()).getActivity());
    }

    /**
     * 初始化Fragment
     *
     * @param fragments
     * @return
     */
    private List<Fragment> initFragments(List<Fragment> fragments) {

        BuddyFragment buddyFragment = BuddyFragment.newInstance();
        FansFragment fansFragment = FansFragment.newInstance();
        MyAttentionFragment myAttentionFragment = MyAttentionFragment.newInstance();
        fragments.add(buddyFragment);
        fragments.add(fansFragment);
        fragments.add(myAttentionFragment);

        return fragments;

    }

    /**
     * 设置fragmentViewPager指示器
     *
     * @param magicIndicator
     * @param viewPager
     * @param context
     */
    private void initMagicIndicator(MagicIndicator magicIndicator, ViewPager viewPager, Context context) {
        CommonNavigator commonNavigator = new CommonNavigator(context);
        commonNavigator.setAdapter(new DynamicIndicatorAdapter(viewPager, mDataList));
        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(context, 15);
            }
        });

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });
    }
}
