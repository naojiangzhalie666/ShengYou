package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface VerifiedContract {

    interface View : BaseView {

        /**
         * 查询是否实名认证成功
         */
        fun querySuccess(status: String)

        /**
         * 实名认证请求成功
         */
        fun verifiedSuccess(status:String)

        /**
         * 接口请求失败
         */
        fun onError(msg: String)

    }

    interface Presenter {

        /**
         * 查询是否实名认证
         */
        fun queryVerified(userId: String)

        /**
         * 实名认证
         */
        fun verified(userId: String, userRealName: String, userIdentifica: String)

    }

    interface Model {

        /**
         * 查询是否实名认证
         */
        fun queryVerified(userId: String?): HttpObservable<BaseResponse<String>>

        /**
         * 实名认证
         */
        fun verified(userId: String, userRealName: String,userIdentifica: String): HttpObservable<BaseResponse<String>>

    }

}