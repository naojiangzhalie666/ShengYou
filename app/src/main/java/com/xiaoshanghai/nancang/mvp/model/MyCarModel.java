package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MyCarContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.DeckResult;

public class MyCarModel implements MyCarContract.Model {
    @Override
    public HttpObservable<BaseResponse<DeckResult>> getMyCar() {
        return HttpClient.getApi().getMyCar();
    }

    @Override
    public HttpObservable<BaseResponse<String>> useCar(String id) {
        return HttpClient.getApi().useCar(id);
    }
}
