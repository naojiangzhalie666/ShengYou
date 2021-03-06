package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.GoldGiftOutlayContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult

class GoldGiftOutlayModel : GoldGiftOutlayContract.Model{
    override fun getGoldGiftOutlayList(current: String?, size: String?, date: String?): HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult?>?>?>?>? {
        return HttpClient.getApi().getGoldGiftOutlayList(current, size, date);
    }
}
