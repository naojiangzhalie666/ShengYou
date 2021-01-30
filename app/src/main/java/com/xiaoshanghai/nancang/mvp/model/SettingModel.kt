package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.SettingContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.BindEntity
import com.xiaoshanghai.nancang.net.bean.CheckVersionBean

class SettingModel:SettingContract.Model {

    override fun checkVersion(): HttpObservable<BaseResponse<CheckVersionBean>> {
        return HttpClient.getApi().checkVersion()
    }

    override fun getBoundPay(): HttpObservable<BaseResponse<BindEntity>> {
        return HttpClient.getApi().boundPay
    }
}