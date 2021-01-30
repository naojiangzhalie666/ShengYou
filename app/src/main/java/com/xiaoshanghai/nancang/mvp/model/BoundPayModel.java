package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.BoundPayContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BindEntity;

public class BoundPayModel implements BoundPayContract.Model {
    @Override
    public HttpObservable<BaseResponse<BindEntity>> getBoundPay() {
        return HttpClient.getApi().getBoundPay();
    }
}
