package com.xiaoshanghai.nancang.mvp.presenter

import android.content.Context
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.PhoneTestContract
import com.xiaoshanghai.nancang.mvp.model.PhoneTestModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class PhoneTestPresenter :BasePresenter<PhoneTestContract.View>(),PhoneTestContract.Presenter {

    private val model by lazy { PhoneTestModel() }

    override fun getStatusCode(phone: String?) {
        model.getStatusCode(phone)
                .execOnThread(view.getActLifeRecycle<BaseResponse<String>>(), object : HttpObserver<String>() {
                    override fun success(bean: String, response: BaseResponse<String>) {
                        if (bean == "1") {
                            view.codeSuccess(bean)
                        } else {
                            view.codeError((view as Context).resources.getString(R.string.send_sms_error))
                        }
                    }

                    override fun error(msg: String) {
                        view.codeError(msg)
                    }
                })
    }

    override fun checkCode(phone: String?, code: String?) {
        model.checkCode(phone,code)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.onCheckSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}