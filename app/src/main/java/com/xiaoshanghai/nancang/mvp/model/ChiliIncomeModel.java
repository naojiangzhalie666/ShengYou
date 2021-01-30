package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.ChiliIncomeContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.ChiliIncomeResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class ChiliIncomeModel implements ChiliIncomeContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>>> getMyChiliIncomeList(String current, String size, String date) {
        return HttpClient.getApi().getMyChiliIncomeList(current,size,date);
    }
}
