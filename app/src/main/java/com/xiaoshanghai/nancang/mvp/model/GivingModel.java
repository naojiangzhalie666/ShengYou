package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.GivingContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GiftRecordResult;

public class GivingModel implements GivingContract.Model {
    @Override
    public HttpObservable<BaseResponse<GiftRecordResult>> giftRecord(String userId) {
        return HttpClient.getApi().giftRecord(userId);
    }
}
