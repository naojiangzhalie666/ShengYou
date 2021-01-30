package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.SignInContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.SignDayEntity;
import com.xiaoshanghai.nancang.net.bean.SignRewardEntity;

import java.util.List;

public class SignInModel implements SignInContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<SignRewardEntity>>> getSignReward() {
        return HttpClient.getApi().getSignReward();
    }

    @Override
    public HttpObservable<BaseResponse<SignDayEntity>> getSignDay() {
        return HttpClient.getApi().getSignDay();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> sign() {
        return HttpClient.getApi().sign();
    }
}
