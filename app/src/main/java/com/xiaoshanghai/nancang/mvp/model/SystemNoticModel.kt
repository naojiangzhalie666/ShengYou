package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.SystemNoticContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.SystemNotic

class SystemNoticModel: SystemNoticContract.Model {
    override fun getSystemNotic(current: String?, size: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<SystemNotic>>>> {
        return HttpClient.getApi().getSystemNotic(current,size)
    }

}