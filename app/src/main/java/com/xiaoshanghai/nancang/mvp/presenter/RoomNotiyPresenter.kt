package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.RoomNotiyContract
import com.xiaoshanghai.nancang.mvp.model.RoomNotifyModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class RoomNotiyPresenter : BasePresenter<RoomNotiyContract.View>(), RoomNotiyContract.Presenter {

    private val model: RoomNotifyModel by lazy { RoomNotifyModel() }

    override fun editRoomNotify(roomId: String?, noticeTitle: String?, noticeComment: String?) {
        model.editRoomNotify(roomId, noticeTitle, noticeComment)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


}