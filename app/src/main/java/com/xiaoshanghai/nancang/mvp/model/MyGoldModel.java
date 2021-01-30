package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MyGoldContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class MyGoldModel implements MyGoldContract.Model {

    @Override
    public HttpObservable<BaseResponse<Double>> getMyGoldNum() {
        return HttpClient.getApi().getMyGoldNum();
    }
}
