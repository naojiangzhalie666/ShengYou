package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.FeedBackContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class FeedBackModel:FeedBackContract.Model{
    override fun feedBack(message: String, type: String, num: String, userId: String): HttpObservable<BaseResponse<Int>>? {
        return HttpClient.getApi().feedBack(message,type,num,userId)
    }

}