package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.ChatContract
import com.xiaoshanghai.nancang.mvp.model.ChatModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GiftAllResult

class ChatPresenter : BasePresenter<ChatContract.View>(), ChatContract.Presenter {

    val mode by lazy { ChatModel() }

    override fun getGift() {
        mode.getGift()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<GiftAllResult>() {
                    override fun success(bean: GiftAllResult?, response: BaseResponse<GiftAllResult>?) {
                        view.onGiftSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun getSelfNoble() {
        mode.getNoble()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onNobleSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}