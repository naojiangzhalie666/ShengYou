package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.RoomBgConstract
import com.xiaoshanghai.nancang.mvp.model.RoomBgModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity

class RoomBgPresenter : BasePresenter<RoomBgConstract.View>(), RoomBgConstract.Presenter {

    private val model by lazy { RoomBgModel() }

    override fun setRoomBg(roomId: String?, userId: String?, roomBg: RoomBgEntity?) {
        view.showLoading()
        model.setRoomBg(roomId, userId, roomBg?.id)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.hideLoading()
                        view.onSuccess(bean,roomBg)
                    }

                    override fun error(msg: String?) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }

    override fun getMyNoble() {

        model.getNoble()
                .execOnThread(view.getActLifeRecycle(),object:HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {

                        view.onNobleSuccess(bean)
                    }

                    override fun error(msg: String?) {

                        view.onError(msg)
                    }

                })
    }
}