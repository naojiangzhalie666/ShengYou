package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.SignDayEntity;
import com.xiaoshanghai.nancang.net.bean.SignRewardEntity;

import java.util.List;

public interface SignInContract {

    interface View extends BaseView {

        void onSignRewardSuccess(List<SignRewardEntity> results);

        void onSignDaySuccess(SignDayEntity signDayResult);

        void onSginSuccess(Integer status);

        void onError(String msg);

    }

    interface Presenter {

       void getSignReward();

       void getSignDay();

       void sign();

    }

    interface Model {

        HttpObservable<BaseResponse<List<SignRewardEntity>>> getSignReward();

        HttpObservable<BaseResponse<SignDayEntity>> getSignDay();

        HttpObservable<BaseResponse<Integer>> sign();

    }

}
