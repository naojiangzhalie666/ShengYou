package com.xiaoshanghai.nancang.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BannerResult;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeSortResult;
import com.youth.banner.Banner;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.List;

public interface HomeFragmentContract {
    interface View extends BaseView {

        /**
         * banner点击逻辑
         * @param result
         */
      void bannerClick(BannerResult result);

        /**
         * 首页分类点击
         * @param index
         */
      void sortClick(int index);

        /**
         * 获取首页分类成功
         * @param fragments
         */
      void sortSuccess(List<HomeSortResult> fragments);

        /**
         * 获取首页分类失败
         */
      void sortError();

    }

    interface Presenter {
        /**
         * 获取首页banner数据
         * @param mBanner
         */
        void getBanner(Banner mBanner);

        /**
         * 获取首页房间分类
         * @param indicator
         */
        void getSort(MagicIndicator indicator, ViewPager mViewPager);
    }

    interface Model {
        /**
         * 获取首页banner
         * @return
         */
        HttpObservable<BaseResponse<List<BannerResult>>> getBanner();

        /**
         * 首页分类
         * @return
         */
        HttpObservable<BaseResponse<List<HomeSortResult>>> getHomeSort();


    }
}
