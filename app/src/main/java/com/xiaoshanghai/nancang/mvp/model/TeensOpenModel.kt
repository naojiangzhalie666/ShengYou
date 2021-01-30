package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.TeensOpenContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class TeensOpenModel : TeensOpenContract.Model {
    override fun startTeens(userId: String, password: String, status: String): HttpObservable<BaseResponse<String>>
            = HttpClient.getApi().startTeens(userId, password, status)

}