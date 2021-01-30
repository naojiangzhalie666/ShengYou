package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomMyGiftConstract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult

class RoomMyGiftModel:RoomMyGiftConstract.Model {

    override fun getPackageGift(): HttpObservable<BaseResponse<MutableList<RoomGiftResult>>> {
        return HttpClient.getApi().packageGift
    }

    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myGoldNum
    }

    override fun getMyChili(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myChili
    }

    override fun getTotalValue(): HttpObservable<BaseResponse<Int?>?>? {
        return HttpClient.getApi().totalValue
    }

}