package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.RechargeRecordContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.CashEntity;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class RechargeRecordModel implements RechargeRecordContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<CashEntity>>>> getGoldRecharge(String current, String size, String userId, String date) {
        return HttpClient.getApi().getGoldRecharge(current, size, userId, date);
    }
}
