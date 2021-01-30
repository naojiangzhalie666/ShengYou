package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MyDiamondContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class MyDiamondModel implements MyDiamondContract.Model {
    @Override
    public HttpObservable<BaseResponse<Double>> getMyDiamondNum() {
        return HttpClient.getApi().getMyDiamondNum();
    }
}
