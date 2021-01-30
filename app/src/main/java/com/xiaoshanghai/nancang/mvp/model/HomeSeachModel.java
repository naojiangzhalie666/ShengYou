package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.HomeSeachContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeSeachEntity;
import com.xiaoshanghai.nancang.net.bean.SeachRoomEntty;

import java.util.List;

public class HomeSeachModel implements HomeSeachContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<SeachRoomEntty>>> getSeachRoomRecord() {
        return HttpClient.getApi().getSeachRoomRecord();
    }

    @Override
    public HttpObservable<BaseResponse<String>> removeEnterRoomRecord(String userId) {
        return HttpClient.getApi().removeEnterRoomRecord(userId);
    }

    @Override
    public HttpObservable<BaseResponse<List<HomeSeachEntity>>> getSeachResult(String searchString) {
        return HttpClient.getApi().getSeachResult(searchString);
    }
}
