package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.TeensContract
import com.xiaoshanghai.nancang.mvp.model.TeenModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class TeensPresenter :BasePresenter<TeensContract.View>(),TeensContract.Presenter {


    private val model:TeenModel by lazy { TeenModel() }

    override fun getTeenStatus(userId: String) {
        model.getTeenStatus(userId)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.teenSuccess(bean!!)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg!!)
                    }

                })
    }
}