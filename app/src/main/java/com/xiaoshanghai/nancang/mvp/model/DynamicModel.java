package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.DynamicContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class DynamicModel implements DynamicContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(String current, String size, String userId) {
        return HttpClient.getApi().getFriendsCircle(current, size, userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> fabulous(String topicId) {
        return HttpClient.getApi().fabulous(topicId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> deleteTopic(String topicId) {
        return HttpClient.getApi().deleteTopic(topicId);
    }
}
