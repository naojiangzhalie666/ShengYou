package com.xiaoshanghai.nancang.mvp.presenter;

import android.text.TextUtils;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseApplication;
import com.xiaoshanghai.nancang.base.BaseFragment;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.callback.HomeSortCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.HomeFragmentContract;
import com.xiaoshanghai.nancang.mvp.model.HomeFragmentModel;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeBannerAdapter;
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeIndexAdapter;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BannerResult;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeSortResult;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.List;

public class HomeFragmentPresenter extends BasePresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter {

    private HomeFragmentModel model;

    public HomeFragmentPresenter() {
        model = new HomeFragmentModel();
    }

    @Override
    public void getBanner(Banner mBanner) {
        model.getBanner()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<BannerResult>>() {


                    @Override
                    protected void success(List<BannerResult> banners, BaseResponse<List<BannerResult>> response) {

                        String sig = SPUtils.getInstance().getString(SpConstant.APP_SIG);

                        if (!TextUtils.isEmpty(sig) && SPUtils.getInstance().getUserInfo() != null) {
                            TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(BaseApplication.getApplication());
                            UserBean userInfo = SPUtils.getInstance().getUserInfo();
                            trtcVoiceRoom.login(Constant.IM_APP_KEY, userInfo.getId(), sig, null);
                        }

                        if (banners == null || mBanner == null) return;

                        HomeBannerAdapter adapter = new HomeBannerAdapter(((BaseFragment) getView()).getActivity(), banners);
                        adapter.setOnBannerListener((OnBannerListener<BannerResult>) (data, position) -> getView().bannerClick(data));


                        mBanner.setAdapter(adapter, true)
                                .setIndicator(new CircleIndicator(((BaseFragment) getView()).getActivity()))
                                .start();


                    }

                    @Override
                    protected void error(String msg) {

                    }
                });
    }

    @Override
    public void getSort(MagicIndicator indicator, ViewPager mViewPager) {
        model.getHomeSort()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<HomeSortResult>>() {
                    @Override
                    protected void success(List<HomeSortResult> bean, BaseResponse<List<HomeSortResult>> response) {


                        HomeSortResult sortResult = new HomeSortResult();
                        sortResult.setRoomTypeName("全部");
                        bean.add(0, sortResult);

                        CommonNavigator commonNavigator = new CommonNavigator(((BaseFragment) getView()).getActivity());
                        commonNavigator.setAdapter(new HomeIndexAdapter(bean, indicator, new HomeSortCallback<HomeSortResult>() {

                            @Override
                            public void onOpenSuccess(HomeSortResult result, int index) {
                                getView().sortClick(index);
                            }

                        }));

                        indicator.setNavigator(commonNavigator);

                        getView().sortSuccess(bean);

                    }

                    @Override
                    protected void error(String msg) {

                    }
                });
    }
}
