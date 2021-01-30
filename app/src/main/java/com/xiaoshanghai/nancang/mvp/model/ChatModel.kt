package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.ChatContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GiftAllResult

class ChatModel:ChatContract.Model {
    override fun getGift(): HttpObservable<BaseResponse<GiftAllResult>> {
        return HttpClient.getApi().roomGift
    }

    override fun getNoble(): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().noble
    }

}