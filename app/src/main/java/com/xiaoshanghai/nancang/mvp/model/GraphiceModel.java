package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.GraphiceContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

import java.util.Map;

import okhttp3.RequestBody;

public class GraphiceModel implements GraphiceContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> releaseFriends(String topicContent, Map<String, RequestBody> params) {
        return HttpClient.getApi().releaseFriends(topicContent, params);
    }
}
