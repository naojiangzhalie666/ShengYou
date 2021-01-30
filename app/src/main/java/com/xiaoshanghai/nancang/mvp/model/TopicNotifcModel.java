package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.TopicNotifcContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.TopicMsg;

import java.util.List;

public class TopicNotifcModel implements TopicNotifcContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<TopicMsg>>>> getTopicMsg(String current, String size, String userId) {
        return HttpClient.getApi().getTopicMsg(current, size, userId);
    }
}
