package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.OnLineContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult

class OnLineModel:OnLineContract.Model {
    override fun getOnLineUser(current: String?, size: String?, roomId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>> {
        return HttpClient.getApi().getOnLineUser(current, size, roomId)
    }

}