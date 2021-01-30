package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomTypeContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeSortResult

class RoomTypeModel:RoomTypeContract.Model {

    override fun getHomeSort(): HttpObservable<BaseResponse<MutableList<HomeSortResult>>> {
        return HttpClient.getApi().homeSort
    }

    override fun modifyRoomType(roomId: String?, typeId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().modifyRoomType(roomId,typeId)
    }
}