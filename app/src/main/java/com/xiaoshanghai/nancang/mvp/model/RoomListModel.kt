package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomListConstart
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.UserRankResult

class RoomListModel: RoomListConstart.Model {
    override fun getUserRank(roomId: String?, type: Int?): HttpObservable<BaseResponse<MutableList<UserRankResult>>> {
        return HttpClient.getApi().getUserRank(roomId,type)
    }
}