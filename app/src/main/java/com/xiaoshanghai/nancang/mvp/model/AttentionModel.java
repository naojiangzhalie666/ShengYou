package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.AttentionContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;

import java.util.List;

public class AttentionModel implements AttentionContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFollow(String current, String size) {
        return HttpClient.getApi().getMyFollow(current, size);
    }
}
