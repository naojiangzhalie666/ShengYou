package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.HalfRankConstract
import com.xiaoshanghai.nancang.mvp.model.HalfRankModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HalfRoomRankResult

class HalfRankPresenter : BasePresenter<HalfRankConstract.View>(), HalfRankConstract.Presenter {

    private val model: HalfRankModel by lazy { HalfRankModel() }

    override fun getRoomRank(roomId: String?) {
        model.getHalfRoomRank(roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HalfRoomRankResult>() {
                    override fun success(bean: HalfRoomRankResult?, response: BaseResponse<HalfRoomRankResult>?) {
                        view.rankSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}