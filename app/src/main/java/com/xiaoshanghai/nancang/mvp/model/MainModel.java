package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MainContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class MainModel implements MainContract.Model {

    @Override
    public HttpObservable<BaseResponse<Integer>> exitRoom(String roomId) {
        return HttpClient.getApi().exitRoom(roomId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getTeensStatus(String userId) {
        return HttpClient.getApi().getTeenStatus(userId);
    }
}
