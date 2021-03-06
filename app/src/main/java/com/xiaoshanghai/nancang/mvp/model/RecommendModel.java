package com.xiaoshanghai.nancang.mvp.model;
import com.xiaoshanghai.nancang.mvp.contract.RecommendContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.utils.SPUtils;

import java.util.List;

public class RecommendModel implements RecommendContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(String current, String size,String userId,String city) {
        return HttpClient.getApi().getFriendsCircleCity(current, size,userId,city, SPUtils.getInstance().getUserInfo().getUserSex()+"");
    }
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(String current, String size,String userId) {
        return HttpClient.getApi().getFriendsCircle(current, size,userId, SPUtils.getInstance().getUserInfo().getUserSex()+"");
    }
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getAttention(String current, String size) {
        return HttpClient.getApi().getAttention(current,size);
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
