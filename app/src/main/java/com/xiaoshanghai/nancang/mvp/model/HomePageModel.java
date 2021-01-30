package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.HomePageConract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MineReslut;

public class HomePageModel implements HomePageConract.Model {
    @Override
    public HttpObservable<BaseResponse<MineReslut>> getMine(String userId) {
        return HttpClient.getApi().getMine(userId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> queryFollow(String userId) {
        return HttpClient.getApi().queryFollow(userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> follow(String userId, String status) {
        return HttpClient.getApi().follow(userId,status);
    }

    @Override
    public HttpObservable<BaseResponse<String>> unfollow(String userId) {
        return HttpClient.getApi().unfollow(userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> isBlack(String userId) {
        return HttpClient.getApi().isBlack(userId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> addBlackList(String blacklistType, String currentId, String blacklistUserId) {
        return HttpClient.getApi().addBlackList(blacklistType, currentId, blacklistUserId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> removeBlackList(String blacklistType, String currentId, String blacklistUserId) {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId);
    }
}
