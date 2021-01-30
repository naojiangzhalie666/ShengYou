package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.DiamondIncomeContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.IncomeResult;

import java.util.List;

public class DiamondIncomeModel implements DiamondIncomeContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<IncomeResult>>>> getIncomeList(String current, String size, String date) {
        return HttpClient.getApi().getIncomeList(current, size, date);
    }
}
