package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.ChatBlackContract
import com.xiaoshanghai.nancang.mvp.model.ChatBlackModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class ChatBlackPresenter:BasePresenter<ChatBlackContract.View>(),ChatBlackContract.Presenter {

   private val model by lazy { ChatBlackModel() }

    override fun isBlack(userId: String?) {
        model.isBlack(userId)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String> () {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.onBlackSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun addBlackList(status: String?, roomId: String?, userId: String?) {
        model.addBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("1", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun removeBlackList(status: String?, roomId: String?, userId: String?) {
        model.removeBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("2", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}