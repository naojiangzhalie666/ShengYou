package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.PhoneTestContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class PhoneTestModel : PhoneTestContract.Model{
    override fun getStatusCode(phone: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().getStatusCode(phone)
    }

    override fun checkCode(phone: String?, code: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().checkCode(phone, code)
    }

}