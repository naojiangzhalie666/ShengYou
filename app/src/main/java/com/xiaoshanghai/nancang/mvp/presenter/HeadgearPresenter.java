package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.callback.OnCarTestDive;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.contract.HeadgearContract;
import com.xiaoshanghai.nancang.mvp.model.HeadgearModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HeadgearAct;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.CarShopFragment;
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.HeadwearStoreFragment;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HeadgearPresenter extends BasePresenter<HeadgearContract.View> implements HeadgearContract.Presenter {

    private HeadgearModel model;

    public HeadgearPresenter() {
        this.model = new HeadgearModel();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initFragments(ViewPager mViewPager, TextView tvHeadgear, TextView tvCar, MineReslut mResult,String chatType,String groupId, OnCarTestDive onCarTestDive) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MINE_RESLUT,mResult);
        bundle.putString(Constant.CHAT_TYPE,chatType);
        bundle.putString(Constant.PUBLIC_CHAT_GROUP,groupId);

        HeadwearStoreFragment headwearStoreFragment = HeadwearStoreFragment.newInstance();
        headwearStoreFragment.setArguments(bundle);
        headwearStoreFragment.setOnCarTestDive(onCarTestDive);


        CarShopFragment carShopFragment = CarShopFragment.newInstance();
        carShopFragment.setArguments(bundle);
        carShopFragment.setOnCarTestDive(onCarTestDive);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(headwearStoreFragment);
        fragments.add(carShopFragment);

        HomeRadioPageAdapter fragmentAdapter = new HomeRadioPageAdapter(((HeadgearAct) getView()).getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(fragmentAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvHeadgear.setSelected(true);
                    tvCar.setSelected(false);
                } else if (position == 1) {
                    tvHeadgear.setSelected(false);
                    tvCar.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
    public void stopAnimation(SVGAImageView mSvga) {
        mSvga.stopAnimation(true);
    }

    @Override
    public void getMine( String userId) {
        model.getMine(userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<MineReslut>() {
                    @Override
                    protected void success(MineReslut bean, BaseResponse<MineReslut> response) {
                        getView().MineSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);
                    }
                });
    }
}
