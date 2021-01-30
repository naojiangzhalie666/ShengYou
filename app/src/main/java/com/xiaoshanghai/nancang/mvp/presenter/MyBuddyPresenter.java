package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseActivity;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.callback.OnFriendsSelectCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.MyBuddyContract;
import com.xiaoshanghai.nancang.mvp.model.FriendsSelectionModel;
import com.xiaoshanghai.nancang.mvp.ui.adapter.DynamicIndicatorAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.FollowFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.MyBuddyFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.MyFansFragment;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBuddyPresenter extends BasePresenter<MyBuddyContract.View> implements MyBuddyContract.Presenter {

    private static final String[] CHANNELS = new String[]{"好友", "粉丝", "关注"};

    private List<Fragment> mFragmetns = new ArrayList<>();

    private List<String> mDataList = Arrays.asList(CHANNELS);

    private FriendsSelectionModel model;

    public MyBuddyPresenter() {

        model = new FriendsSelectionModel();

    }

    @Override
    public void initFragment(MagicIndicator indicator, ViewPager viewPager, OnFriendsSelectCallback callback,String status) {

        List<Fragment> fragments = initFragments(mFragmetns,callback,status);

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(((BaseActivity) getView()).getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mFragmetns.size());

        initMagicIndicator(indicator, viewPager, ((BaseActivity) getView()));
    }

    @Override
    public void giveAway(String deckId, String userId) {
        getView().showLoading();
        model.buyDeck(deckId,userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().hideLoading();
                        getView().giveAwaySuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });
    }

    /**
     * 初始化Fragment
     *
     * @param fragments
     * @param status
     * @return
     */
    private List<Fragment> initFragments(List<Fragment> fragments, OnFriendsSelectCallback callback, String status) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.BUDDY_KEY,status);

        MyBuddyFragment buddyFragment = MyBuddyFragment.newInstance();
        buddyFragment.setArguments(bundle);
        buddyFragment.setOnFriendsSelectCallback(callback);

        MyFansFragment fansFragment = MyFansFragment.newInstance();
        fansFragment.setArguments(bundle);
        fansFragment.setOnFriendsSelectCallback(callback);

        FollowFragment myAttentionFragment = FollowFragment.newInstance();
        myAttentionFragment.setArguments(bundle);
        myAttentionFragment.setOnFriendsSelectCallback(callback);

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
