package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.TeensContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class TeenModel : TeensContract.Model {
    override fun getTeenStatus(userId: String): HttpObservable<BaseResponse<String>> = HttpClient.getApi().getTeenStatus(userId)
}