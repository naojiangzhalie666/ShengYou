package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.BindEntity
import com.xiaoshanghai.nancang.net.bean.CheckVersionBean

interface SettingContract {

    interface View : BaseView {

        fun onVersionSuccess(version: CheckVersionBean?)

        fun onError(msg: String?)

        fun onBindSuccess(result:BindEntity?)

        fun onBindError(msg:String?)

    }

    interface Presenter {

        fun getVersion()

        fun getBoundPay()

    }

    interface Model {

        fun checkVersion(): HttpObservable<BaseResponse<CheckVersionBean>>

        fun getBoundPay(): HttpObservable<BaseResponse<BindEntity>>

    }

}