package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.ChiliOutlayContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.ChiliIncomeResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class ChiliOutlayModel implements ChiliOutlayContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>>> getOutlayList(String current, String size, String date) {
        return HttpClient.getApi().getMyChiliOutlayList(current, size, date);
    }
}
