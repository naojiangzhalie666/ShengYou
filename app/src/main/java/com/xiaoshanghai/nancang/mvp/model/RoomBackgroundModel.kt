package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomBackgroundConstant
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity

class RoomBackgroundModel: RoomBackgroundConstant.Model {


    override fun getRoomBg(current: String?, size: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<RoomBgEntity>>>> {
        return HttpClient.getApi().getRoomBg(current, size)
    }
}