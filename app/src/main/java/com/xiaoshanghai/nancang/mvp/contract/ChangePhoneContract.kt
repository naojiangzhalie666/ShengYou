package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

interface ChangePhoneContract {

    interface View:BaseView {

        /**
         * 获取验证码成功
         */
        fun codeSuccess(code: String?)

        /**
         * 获取验证码失败
         */
        fun codeError(msg: String?)

        fun onChangeSuccess(status:String?)

        fun onError(msg:String?)


    }

    interface Presenter {

        fun getStatusCode(phone: String?)

        fun changePhone(phone: String?,userId: String?,code: String?)

    }

    interface Model {
        /**
         * 通过手机号获取验证码
         * @param phone
         * @return
         */
        fun getStatusCode(phone: String?): HttpObservable<BaseResponse<String>>

        fun changePhone(phone: String?, userId: String?, code: String?): HttpObservable<BaseResponse<String>>
    }
}