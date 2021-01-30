package com.xiaoshanghai.nancang.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.opensource.svgaplayer.SVGAImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface HomePageConract {

    interface View extends BaseView {

        /**
         * 初始化
         */
        void init();

        /**
         * 获取个人基本资料成功
         *
         * @param result
         */
        void mineSuccess(MineReslut result);

        /**
         * 查询关注接口请求成功
         * @param status
         */
        void queryFollowSuccess(Integer status);

        /**
         * 关注接口请求成功
         * @param status
         */
        void followSuccess(String status);

        /**
         * 取消关注接口请求成功
         * @param status
         */
        void unfollowSuccess(String status);

        /**
         * 接口请求失败
         *
         * @param msg
         */
        void onError(String msg);

        /**
         * 是否拉黑
         * @param status
         */
        void onBlackSuccess(String status);


        void onAddAndRemoveBlackSuccess(String status, Integer resultStatus);

    }

    interface Presenter {

        /**
         * 初始化
         */
        void init();

        /**
         * 加载动画
         *
         * @param animation
         * @param mSvga
         */
        void animation(String animation, SVGAImageView mSvga);

        /**
         * 获取个人基本资料
         *
         * @param userId
         */
        void getMine(String userId);

        /**
         * 初始化Fragment
         */
        void initFragment(MagicIndicator indicator, ViewPager viewPager, String userId, MineReslut result);

        /**
         * 查询是否关注
         * @param userId
         */
        void queryFollow(String userId);

        /**
         * 关注
         * @param userId
         */
        void follow(String userId);

        /**
         * 取消关注
         * @param userId
         */
        void unfollow(String userId);

        /**
         * 是否拉黑
         * @param userId
         */
        void isBlack(String userId);

        /**
         * 添加或者移除黑名单
         */
        void addBlackList(String status,String userId);

        /**
         * 移除黑名单
         */
        void removeBlackList(String status, String userId);


    }

    interface Model {

        /**
         * 获取个人基本资料
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<MineReslut>> getMine(String userId);

        /**
         * 查询是否关注某用户
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<Integer>> queryFollow(String userId);

        /**
         * 关注&取消关注
         * 这里当关注使用
         *
         * @param userId
         * @param status
         * @return
         */
        HttpObservable<BaseResponse<String>> follow(String userId, String status);

        /**
         * 取消关注
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> unfollow(String userId);

        /**
         * 是否拉黑
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> isBlack(String userId);

        /**
         * 添加和移除黑名单
         */
        HttpObservable<BaseResponse<Integer>> addBlackList(String blacklistType,String currentId, String blacklistUserId);

        /**
         * 移除黑名单
         */
        HttpObservable<BaseResponse<Integer>> removeBlackList(String blacklistType, String currentId, String blacklistUserId);

    }
}
