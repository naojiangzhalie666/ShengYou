package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.DiamondExpensesContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.IncomeResult;

import java.util.List;

public class DiamondExpensesModel implements DiamondExpensesContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<IncomeResult>>>> getIncomeList(String current, String size, String date) {
        return HttpClient.getApi().getOutComeList(current, size, date);
    }
}
