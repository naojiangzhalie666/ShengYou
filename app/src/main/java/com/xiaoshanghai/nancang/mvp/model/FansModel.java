package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.FansContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;

import java.util.List;

public class FansModel implements FansContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFans(String current, String size) {
        return HttpClient.getApi().getMyFans(current, size);
    }

    @Override
    public HttpObservable<BaseResponse<String>> follow(String userId, String status) {
        return HttpClient.getApi().follow(userId,status);
    }
}
