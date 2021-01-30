package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.GoldGiftIncomeContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class GoldGiftIncomeModel implements GoldGiftIncomeContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getGoldGiftList(String current, String size, String date) {
        return HttpClient.getApi().getGoldGiftList(current, size, date);
    }
}
