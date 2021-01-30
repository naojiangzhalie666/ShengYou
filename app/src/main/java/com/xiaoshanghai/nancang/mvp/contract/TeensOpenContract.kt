package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface TeensOpenContract {

    interface View : BaseView {

        fun onSuccess(status: String)

        fun onError(msg: String)

    }

    interface Presenter {

        /**
         * 开启青少年模式
         */
        fun openTeens(uesrId: String, password: String)

    }

    interface Model {
        /**
         * 开启青少年模式
         */
        fun startTeens(userId: String, password: String, status: String): HttpObservable<BaseResponse<String>>

    }

}