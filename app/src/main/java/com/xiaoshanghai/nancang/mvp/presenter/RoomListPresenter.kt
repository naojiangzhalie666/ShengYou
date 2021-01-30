package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.RoomListConstart
import com.xiaoshanghai.nancang.mvp.model.RoomListModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.UserRankResult

class RoomListPresenter : BasePresenter<RoomListConstart.View>(), RoomListConstart.Presenter {

    private val model: RoomListModel by lazy { RoomListModel() }

    override fun getUserRank(roomId: String?, type: Int?) {

        model.getUserRank(roomId, type)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<MutableList<UserRankResult>>() {
                    override fun success(bean: MutableList<UserRankResult>?, response: BaseResponse<MutableList<UserRankResult>>?) {
                        view.userRankSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}