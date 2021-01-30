package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.RoomGiftContract
import com.xiaoshanghai.nancang.mvp.model.RoomGiftModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class RoomGiftPresenter : BasePresenter<RoomGiftContract.View>(), RoomGiftContract.Presetner {

    val model: RoomGiftModel by lazy { RoomGiftModel() }

    override fun getMyGoldNum() {
        model.getMyGoldNum()
                ?.execOnThread(view.getActLifeRecycle(), object : HttpObserver<Double?>() {
                    override fun success(bean: Double?, response: BaseResponse<Double?>?) {
                        if (bean != null) {
                            view.onGoldSuccess(bean)
                        } else {
                            view.onError("请求错误")
                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })

    }

    override fun getMyChili() {
        model.getMyChili()
                ?.execOnThread(view.getActLifeRecycle(), object : HttpObserver<Double>() {
                    override fun success(bean: Double?, response: BaseResponse<Double>?) {
                        if (bean != null) {
                            view.onChiliSuccess(bean)
                        } else {
                            view.onError("请求错误")
                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}