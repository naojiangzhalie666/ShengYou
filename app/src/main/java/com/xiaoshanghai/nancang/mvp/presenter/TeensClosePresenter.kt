package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.TeensCloseContract
import com.xiaoshanghai.nancang.mvp.model.TeensCloseModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class TeensClosePresenter :BasePresenter<TeensCloseContract.View>(),TeensCloseContract.Presenter {

    val model by lazy { TeensCloseModel() }

    override fun closeTeens(uesrId: String, password: String) {
        view.showLoading()
        model.startTeens(userId = uesrId,password = password,status = "0")
                .execOnThread(view.getActLifeRecycle(), object: HttpObserver<String>(){
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.hideLoading()
                        view.onSuccess(bean!!)
                    }

                    override fun error(msg: String?) {
                        view.hideLoading()
                        view.onError(msg!!)
                    }

                })

    }
}