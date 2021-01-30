package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.PublicChatContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GiftAllResult;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

public class PublicChatModel implements PublicChatContract.Model {
    @Override
    public HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> getUserInfo(String userId) {
        return HttpClient.getApi().getUserInfo(userId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> queryFollow(String userId) {
        return HttpClient.getApi().queryFollow(userId);
    }

    @Override
    public HttpObservable<BaseResponse<GiftAllResult>> getGift() {
        return HttpClient.getApi().getRoomGift();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> getNoble() {
        return HttpClient.getApi().getNoble();
    }

    @Override
    public HttpObservable<BaseResponse<String>> follow(String userId, String status) {
        return HttpClient.getApi().follow(userId, status);
    }
}
