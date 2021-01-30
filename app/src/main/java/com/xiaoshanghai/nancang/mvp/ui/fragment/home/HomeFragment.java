package com.xiaoshanghai.nancang.mvp.ui.fragment.home;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseMvpFragment;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.helper.EnterRoomHelp;
import com.xiaoshanghai.nancang.mvp.contract.HomeFragmentContract;
import com.xiaoshanghai.nancang.mvp.presenter.HomeFragmentPresenter;
import com.xiaoshanghai.nancang.mvp.ui.activity.main.HomeSeachAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.main.SignInAct;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.WebActivity;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.net.bean.BannerResult;
import com.xiaoshanghai.nancang.net.bean.HomeSortResult;
import com.xiaoshanghai.nancang.utils.ActStartUtils;
import com.youth.banner.Banner;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentContract.View {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.indicator)
    MagicIndicator indicator;

    private List<Fragment> mFragments;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPresenter.attachView(this);
        mPresenter.getBanner(mBanner);
//        mPresenter.getSort(indicator, viewPager);
//
//        mBanner.addBannerLifecycleObserver(this);

    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.getSort(indicator, viewPager);
        mBanner.addBannerLifecycleObserver(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBanner != null)
            mBanner.destroy();
    }

    @Override
    public void bannerClick(BannerResult result) {
//        BannerResult bannerResult = adapter.getData(position);
        Bundle userBundel = new Bundle();
        userBundel.putString(Constant.WEB_URL, result.getLinkUrl());
        userBundel.putString(Constant.WEB_TITLE, result.getBannerName());
        ActStartUtils.startAct(getActivity(), WebActivity.class, userBundel);
    }

    @Override
    public void sortClick(int index) {
        viewPager.setCurrentItem(index);

    }

    @Override
    public void sortSuccess(List<HomeSortResult> beans) {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        } else {
            mFragments.clear();
        }


        for (HomeSortResult homeSortResult : beans) {
            HomeRadioFragment homeRadioFragment = HomeRadioFragment.newInstance(homeSortResult);
            mFragments.add(homeRadioFragment);
        }


        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(supportFragmentManager, mFragments);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mFragments.size());

        ViewPagerHelper.bind(indicator, viewPager);

    }

    @Override
    public void sortError() {

    }

    @OnClick({R.id.rl_seach, R.id.iv_sign_in, R.id.iv_create_room})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_seach:
                ActStartUtils.startAct(getActivity(), HomeSeachAct.class);
                break;
            case R.id.iv_sign_in:
                ActStartUtils.startAct(getActivity(), SignInAct.class);
                break;
            case R.id.iv_create_room:
                EnterRoomHelp.createRoom(getActivity(), this);
                break;
        }
    }
}
