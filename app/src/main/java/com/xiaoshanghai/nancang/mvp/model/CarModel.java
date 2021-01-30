package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.CarContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;

public class CarModel implements CarContract.Model {
    @Override
    public HttpObservable<BaseResponse<DeckResult>> carRecord(String userId) {
        return HttpClient.getApi().carRecord(userId);
    }
}
