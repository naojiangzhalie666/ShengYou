package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.RoomSettingContract
import com.xiaoshanghai.nancang.mvp.model.RoomSettingModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class RoomSettingPresenter : BasePresenter<RoomSettingContract.View>(), RoomSettingContract.Presenter {

    private val model by lazy { RoomSettingModel() }

    override fun modifyRoomName(roomId: String?, roomName: String?) {
        model?.modifyName(roomId, roomName)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.roomNameSuccess(bean, roomName)

                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun modifyRoomPsw(roomId: String, type: String, password: String?) {
        model.modifyPsw(roomId, type, password)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.roomPswSuccess(bean, type == "1", password)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


    override fun setGpSwitch(roomId: String, type: Boolean) {
        model.setPublic(roomId, if (type) 1 else 0)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.isOpenGpSuccess(bean!!, type)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun pickSwitch(roomId: String?, type: Int?, isPick: Boolean?) {
        model.pickSwitch(roomId, type.toString())
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onPickSwitch(bean, isPick)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


}