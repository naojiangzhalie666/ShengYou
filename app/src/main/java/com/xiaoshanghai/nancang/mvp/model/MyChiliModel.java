package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MyChiliContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class MyChiliModel implements MyChiliContract.Model {
    @Override
    public HttpObservable<BaseResponse<Double>> getMyChili() {
        return HttpClient.getApi().getMyChili();
    }
}
