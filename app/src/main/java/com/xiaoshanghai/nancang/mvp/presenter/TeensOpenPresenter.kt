package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.TeensOpenContract
import com.xiaoshanghai.nancang.mvp.model.TeensOpenModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class TeensOpenPresenter : BasePresenter<TeensOpenContract.View>(), TeensOpenContract.Presenter {

    val model by lazy { TeensOpenModel() }

    override fun openTeens(uesrId: String, password: String) {
    view.showLoading()
        model.startTeens(userId = uesrId,password = password,status = "1")
                .execOnThread(view.getActLifeRecycle(), object:HttpObserver<String>(){
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