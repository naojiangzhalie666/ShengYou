package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;


public interface FamilyContract {

    interface View extends BaseView {

        /**
         * 成功获取个人家族信息 result == null ？ 无个人家族 : 有个人家族
         * @param result
         */
        void familySuccess(MyFamilyResult result,RefreshLayout refreshLayout);

        /**
         * 请求失败
         * @param msg
         */
        void onError(String msg,RefreshLayout refreshLayout);

        /**
         * 星推荐获取成功
         * @param resunt
         */
        void recommendSuccess(List<StartRecommendResult> resunt,RefreshLayout refreshLayout);

    }

    interface Presenter {

        /**
         * 根据userid查询我的家族信息
         * @param userId
         */
        void getFamilyInfo(String userId, RefreshLayout refreshLayout);

        /**
         * 获取家族星推荐
         *
         */
        void getStartRecommend(RefreshLayout refreshLayout);

    }

    interface Model {

        /**
         * 根据userid查询我的家族信息
         */
        HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId);

        /**
         * 家族星推荐接口
         */
        HttpObservable<BaseResponse<List<StartRecommendResult>>> getStartRecommend();

    }

}
