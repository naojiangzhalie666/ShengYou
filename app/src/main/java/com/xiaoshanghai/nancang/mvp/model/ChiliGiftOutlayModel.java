package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.ChiliGiftOutlayContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChiliGiftOutlayModel implements ChiliGiftOutlayContract.Model {

    @Nullable
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getChiliGiftOutlayList(@Nullable String current, @Nullable String size, @Nullable String date) {
        return HttpClient.getApi().getChiliGiftOutlayList(current, size, date);
    }
}
