package com.xiaoshanghai.nancang.mvp.presenter

import android.content.Context
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.ChangePswMineContract
import com.xiaoshanghai.nancang.mvp.model.ChangePswMineModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class ResetPsPresenter : BasePresenter<ChangePswMineContract.View>(), ChangePswMineContract.Presenter {
    private val model by lazy { ChangePswMineModel() }


    override fun getCode(phone: String?) {
        model.getStatusCode(phone)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        if (bean == "1") {
                            view.getCodeSuccess()
                        } else {
                            view.getCodeError((view as Context).resources.getString(R.string.send_sms_error))
                        }
                    }

                    override fun error(msg: String?) {
                        view.getCodeError((view as Context).resources.getString(R.string.send_sms_error))
                    }

                })

    }

    override fun changePsw(type: String?, pswNew: String?, smsCode: String?, pswOld: String?) {
        view.showLoading()
        model.changePswMine(type, pswNew, smsCode, pswOld)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {

                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.hideLoading()
                        if (bean != null) {
                            when (bean) {
                                1 -> view.resetPswSuccess()
                                2 -> view.resetPswError("验证码错误")
                                3 -> view.resetPswError("旧密码错误")
                                else -> view.resetPswError("重置失败")
                            }
                        }
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.resetPswError(msg)
                    }


                })
    }


}
