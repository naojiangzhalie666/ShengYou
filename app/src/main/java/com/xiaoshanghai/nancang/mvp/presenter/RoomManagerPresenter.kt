package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.RoomManagerContract
import com.xiaoshanghai.nancang.mvp.model.RoomManagerModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.RoomManagerEntity

class RoomManagerPresenter : BasePresenter<RoomManagerContract.View>(), RoomManagerContract.Presenter {

    private val model by lazy { RoomManagerModel() }

    override fun getRoomManager(roomId: String?) {
        model.getManager(roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<MutableList<RoomManagerEntity>>() {
                    override fun success(bean: MutableList<RoomManagerEntity>?, response: BaseResponse<MutableList<RoomManagerEntity>>?) {
                        view.onRoomManagerSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun removeRoomManager(roomId: String?, user: RoomManagerEntity?) {

        model.removeManager(roomId, user?.userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onRemoveSuccess(bean, user)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}