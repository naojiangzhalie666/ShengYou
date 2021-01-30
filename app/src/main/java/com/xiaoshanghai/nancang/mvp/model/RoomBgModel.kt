package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomBgConstract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class RoomBgModel: RoomBgConstract.Model {

    override fun setRoomBg(roomId: String?, userId: String?, imgId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().setRoomBg(roomId, userId, imgId)
    }

    override fun getNoble(): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().noble
    }

}