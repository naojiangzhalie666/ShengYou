package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.GoldExchangeContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class GoldExchangeModel implements GoldExchangeContract.Model {
    @Override
    public HttpObservable<BaseResponse<Double>> getMyGoldNum() {
        return HttpClient.getApi().getMyGoldNum();
    }

    @Override
    public HttpObservable<BaseResponse<Double>> getMyDiamondNum() {
        return HttpClient.getApi().getMyDiamondNum();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> goldChange(String coinNumber) {
        return HttpClient.getApi().goldChange(coinNumber);
    }


}
