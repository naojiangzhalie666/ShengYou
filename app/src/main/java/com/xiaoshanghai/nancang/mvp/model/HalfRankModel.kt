package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.HalfRankConstract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HalfRoomRankResult

class HalfRankModel:HalfRankConstract.Model {
    override fun getHalfRoomRank(roomId: String?): HttpObservable<BaseResponse<HalfRoomRankResult>> {
        return HttpClient.getApi().getHalfRoomRank(roomId)
    }
}