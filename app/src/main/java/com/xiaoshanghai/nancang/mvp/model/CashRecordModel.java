package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.CashRecordContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.CashEntity;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class CashRecordModel implements CashRecordContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<CashEntity>>>> getCashList(String current, String size, String userId,String date) {
        return HttpClient.getApi().getCashList(current, size, userId,date);
    }
}
