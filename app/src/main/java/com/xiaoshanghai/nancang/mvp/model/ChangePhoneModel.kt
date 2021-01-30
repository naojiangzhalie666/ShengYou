package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.ChangePhoneContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class ChangePhoneModel :ChangePhoneContract.Model {
    override fun getStatusCode(phone: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().getStatusCode(phone)
    }

    override fun changePhone(phone: String?, userId: String?, code: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().changePhone(phone, userId, code)
    }
}