package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.AttentionSelectContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;

import java.util.List;

public class AttentionSelectModel implements AttentionSelectContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFollow(String current, String size) {
        return HttpClient.getApi().getMyFollow(current, size);
    }
}
