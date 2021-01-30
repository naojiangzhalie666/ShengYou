package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.HeadgearContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.MineReslut

class HeadgearModel:HeadgearContract.Model {

    override fun getMine(userId: String?): HttpObservable<BaseResponse<MineReslut>> {
        return HttpClient.getApi().getMine(userId)
    }
}