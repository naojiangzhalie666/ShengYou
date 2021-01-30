package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.VerifiedContract
import com.xiaoshanghai.nancang.mvp.model.VerifiedModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class VerifiedPresenter : BasePresenter<VerifiedContract.View>(), VerifiedContract.Presenter {

    val model by lazy { VerifiedModel() }

    override fun queryVerified(userId: String) {

        view.showLoading()

        model.queryVerified(userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String, response: BaseResponse<String>) {
                        view.hideLoading()
                        view.querySuccess(bean)
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }

    override fun verified(userId: String, userRealName: String, userIdentifica: String) {
        view.showLoading()
        model.verified(userId, userRealName, userIdentifica)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String, response: BaseResponse<String>) {
                        view.hideLoading()
                        view.verifiedSuccess(bean)
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }
}