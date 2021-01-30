package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.HomePageConract;
import com.xiaoshanghai.nancang.mvp.model.HomePageModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.DynamicIndicatorAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.CarFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.DynamicFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.GivingFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.MaterialFragment;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePagePresenter extends BasePresenter<HomePageConract.View> implements HomePageConract.Presenter {

    HomePageModel model;

    private static final String[] CHANNELS = new String[]{"动态", "资料", "礼物", "座驾"};

    private List<Fragment> mFragmetns = new ArrayList<>();

    private List<String> mDataList = Arrays.asList(CHANNELS);

    public HomePagePresenter() {
        model = new HomePageModel();
    }

    @Override
    public void init() {
        getView().init();
    }

    @Override
    public void animation(String animation, SVGAImageView mSvga) {
        SVGAParser parser = SVGAParser.Companion.shareParser();
        parser.init((Context) getView());
        try {
//            animation = "http://192.168.0.12:8080/imgs/deck/ce.svga";  //座驾测试地址
            parser.decodeFromURL(new URL(animation), new SVGAParser.ParseCompletion() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    if (mSvga != null) {
                        mSvga.setVideoItem(videoItem);
                        mSvga.stepToFrame(0, true);
                    }
                }

                @Override
                public void onError() {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getMine(String userId) {
        if (TextUtils.isEmpty(userId)) return;
        model.getMine(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MineReslut>() {
                    @Override
                    protected void success(MineReslut bean, BaseResponse<MineReslut> response) {
                        getView().mineSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void initFragment(MagicIndicator indicator, ViewPager viewPager, String userId, MineReslut result) {

        List<Fragment> fragments = initFragments(mFragmetns, userId, result);

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(((HomePageAct) getView()).getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mFragmetns.size());

        initMagicIndicator(indicator, viewPager, (Context) getView());
    }

    /**
     * 查询是否关注
     *
     * @param userId
     */
    @Override
    public void queryFollow(String userId) {
        model.queryFollow(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Integer>() {
                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().queryFollowSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    /**
     * 关注
     *
     * @param userId
     */
    @Override
    public void follow(String userId) {
        getView().showLoading();
        model.follow(userId, "1")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().followSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });

    }

    /**
     * 取消关注
     *
     * @param userId
     */
    @Override
    public void unfollow(String userId) {
        getView().showLoading();
        model.follow(userId, "0")
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().unfollowSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void isBlack(String userId) {
        model.isBlack(userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<String>(){

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().onBlackSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void addBlackList(String status,String userId) {
        model.addBlackList(status, SPUtils.getInstance().getUserInfo().getId(),userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onAddAndRemoveBlackSuccess("1", bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void removeBlackList(String status, String userId) {
        model.removeBlackList(status,SPUtils.getInstance().getUserInfo().getId(),userId)
                .execOnThread(getView().getActLifeRecycle(),new HttpObserver<Integer>(){

                    @Override
                    protected void success(Integer bean, BaseResponse<Integer> response) {
                        getView().onAddAndRemoveBlackSuccess("2", bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }

    /**
     * 初始化Fragment
     *
     * @param fragments
     * @param userId
     * @param result
     * @return
     */
    private List<Fragment> initFragments(List<Fragment> fragments, String userId, MineReslut result) {

        DynamicFragment dynamicFragment = DynamicFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.USER_ID, userId);
        dynamicFragment.setArguments(bundle);
        fragments.add(dynamicFragment);

        MaterialFragment materialFragment = MaterialFragment.newInstance();
        Bundle materialBundle = new Bundle();
        materialBundle.putString(Constant.USER_ID, userId);
        materialBundle.putSerializable(Constant.MINE_RESLUT, result);
        materialFragment.setArguments(materialBundle);
        fragments.add(materialFragment);

        GivingFragment givingFragment = GivingFragment.newInstance();
        Bundle givingBundle = new Bundle();
        givingBundle.putString(Constant.USER_ID, userId);
        givingFragment.setArguments(materialBundle);
        fragments.add(givingFragment);

        CarFragment carFragment = CarFragment.newInstance();

        Bundle carBundle = new Bundle();
        carBundle.putString(Constant.USER_ID, userId);
        carFragment.setArguments(materialBundle);
        fragments.add(carFragment);

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
