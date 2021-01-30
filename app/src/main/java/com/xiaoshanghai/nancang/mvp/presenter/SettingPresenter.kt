package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.SettingContract
import com.xiaoshanghai.nancang.mvp.model.SettingModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.BindEntity
import com.xiaoshanghai.nancang.net.bean.CheckVersionBean

class SettingPresenter:BasePresenter<SettingContract.View>(),SettingContract.Presenter {

    private val model by lazy { SettingModel() }

    override fun getVersion() {
        model.checkVersion()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<CheckVersionBean>(){
                    override fun success(bean: CheckVersionBean?, response: BaseResponse<CheckVersionBean>?) {
                        view.onVersionSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun getBoundPay() {
        view.showLoading()
        model.getBoundPay()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<BindEntity>() {
                    override fun success(bean: BindEntity?, response: BaseResponse<BindEntity>?) {
                        view.hideLoading()
                        view.onBindSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.hideLoading()
                        view.onBindError(msg)
                    }

                })
    }
}