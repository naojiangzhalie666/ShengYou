package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomManagerContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.RoomManagerEntity

class RoomManagerModel:RoomManagerContract.Model {
    override fun getManager(roomId: String?): HttpObservable<BaseResponse<MutableList<RoomManagerEntity>>> {
        return HttpClient.getApi().getManager(roomId)
    }

    override fun removeManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeManager(roomId, userId)
    }
}