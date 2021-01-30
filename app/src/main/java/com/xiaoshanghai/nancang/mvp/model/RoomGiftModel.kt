package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomGiftContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class RoomGiftModel:RoomGiftContract.Model {
    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myGoldNum
    }

    override fun getMyChili(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myChili
    }
}