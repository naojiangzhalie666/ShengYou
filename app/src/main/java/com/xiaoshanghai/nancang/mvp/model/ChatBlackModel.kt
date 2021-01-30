package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.ChatBlackContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class ChatBlackModel:ChatBlackContract.Model {
    override fun isBlack(userId: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().isBlack(userId)
    }

    override fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().addBlackList(blacklistType, currentId, blacklistUserId)
    }

    override fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId)
    }

}