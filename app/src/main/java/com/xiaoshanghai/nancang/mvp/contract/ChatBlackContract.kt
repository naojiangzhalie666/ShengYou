package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import retrofit2.http.Field

interface ChatBlackContract {

    interface View : BaseView {

        fun onBlackSuccess(status: String?)

        fun onError(msg:String?)

        fun onAddAndRemoveBlackSuccess(status: String?, resultStatus: Int?, roomId: String?, userId: String?)
    }

    interface Presenter {

        fun isBlack(userId: String?)

        /**
         * 添加或者移除黑名单
         */
        fun addBlackList(status: String?, roomId: String?, userId: String?)

        /**
         * 移除黑名单
         */
        fun removeBlackList(status: String?, roomId: String?, userId: String?)
    }

    interface Model {

        fun isBlack(@Field("userId") userId: String?): HttpObservable<BaseResponse<String>>
        /**
         * 添加和移除黑名单
         */
        fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 移除黑名单
         */
        fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

    }
}