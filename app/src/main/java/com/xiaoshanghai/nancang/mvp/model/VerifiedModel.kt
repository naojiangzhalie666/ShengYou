package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.VerifiedContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class VerifiedModel:VerifiedContract.Model {
    override fun queryVerified(userId: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().queryVerified(userId)
    }

    override fun verified(userId: String, userRealName: String, userIdentifica: String): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().verified(userId, userRealName, userIdentifica)
    }
}