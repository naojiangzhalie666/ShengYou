package com.xiaoshanghai.nancang.mvp.contract;

import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.callback.OnCarTestDive;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.opensource.svgaplayer.SVGAImageView;

public interface HeadgearContract {

    interface View extends BaseView {

        /**
         * 获取个人基本资料成功
         * @param result
         */
        void MineSuccess(MineReslut result);

        /**
         * 获取个人基本资料失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        void initFragments(ViewPager mViewPager, TextView tvHeadgear, TextView tvCar, MineReslut mResult,String chatType,String groupId, OnCarTestDive onCarTestDive);

        void animation(String url, SVGAImageView mSvga);

        void stopAnimation(SVGAImageView mSvga);

        /**
         * 获取个人基本资料
         * @param userId
         */
        void getMine(String userId);

    }

    interface Model {

        /**
         * 获取个人基本资料
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<MineReslut>> getMine(String userId);
    }

}
