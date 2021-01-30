package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.NobleSettingContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class NobleSettingModel :NobleSettingContract.Model{
    override fun enterRoomStatus(userId: String?, status: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().enterRoomStatus(userId, status)
    }

}