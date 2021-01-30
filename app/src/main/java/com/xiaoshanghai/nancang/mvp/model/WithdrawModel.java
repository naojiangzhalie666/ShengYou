package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.WithdrawContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BindEntity;
import com.xiaoshanghai.nancang.net.bean.NewGoldResult;

import java.util.List;

public class WithdrawModel implements WithdrawContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<NewGoldResult>>> getCashList() {
        return HttpClient.getApi().getCashList();
    }

    @Override
    public HttpObservable<BaseResponse<BindEntity>> getBoundPay() {
        return HttpClient.getApi().getBoundPay();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> withdraw(String cashConfigId) {
        return HttpClient.getApi().withdraw(cashConfigId);
    }

    @Override
    public HttpObservable<BaseResponse<Double>> getMyDiamondNum() {
        return HttpClient.getApi().getMyDiamondNum();
    }
}
