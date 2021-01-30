package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.GoldRechargeContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult;
import com.xiaoshanghai.nancang.net.bean.GoldResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class GoldRechargeModel implements GoldRechargeContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<GoldResult>>>> getGoldList(String current, String size, String userId) {
        return HttpClient.getApi().getGoldList(current, size, userId);
    }

    @Override
    public HttpObservable<BaseResponse<BuyGoldResult>> buyGold(int payType, String payTargetId) {
        return HttpClient.getApi().buyGold(payType,payTargetId);
    }

    @Override
    public HttpObservable<BaseResponse<Double>> getMyGoldNum() {
        return HttpClient.getApi().getMyGoldNum();
    }
}
