package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.BuddyContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;

import java.util.List;

public class BuddyModel implements BuddyContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFriends(String current, String size) {
        return HttpClient.getApi().getMyFriends(current, size);
    }
}
