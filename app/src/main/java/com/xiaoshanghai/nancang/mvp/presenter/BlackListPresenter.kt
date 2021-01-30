package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.BlackListConstract
import com.xiaoshanghai.nancang.mvp.model.BlackListModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.RoomBlackListEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout

class BlackListPresenter : BasePresenter<BlackListConstract.View>(), BlackListConstract.Presenter {

    private val model by lazy { BlackListModel() }

    var initPage = 1
    var mPage = initPage
    private var size = 10
    private var blactListType = "1"

    override fun getRoomBlackList(refresh: RefreshLayout?, currentId: String?) {
        model.getRoomBlackList(mPage.toString() + "", size.toString() + "", blactListType, currentId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HomeRoomResult<MutableList<RoomBlackListEntity>>>() {
                    override fun success(bean: HomeRoomResult<MutableList<RoomBlackListEntity>>?, response: BaseResponse<HomeRoomResult<MutableList<RoomBlackListEntity>>>?) {
                        val records = bean?.records
                        if (records != null) {
                            if (mPage == initPage) {
                                view.refreshSuccess(refresh, records)
                            } else {
                                view.loadMoreSuccess(refresh, records)
                            }
                            if (records.size > 0) mPage++
                        }
                    }

                    override fun error(msg: String?) {
                        if (refresh != null) {
                            refresh.finishRefresh()
                            refresh.finishLoadMore()
                        }
                        view.onError(msg)
                    }

                })
    }

    override fun removeBlack(roomId: String, blackEntity: RoomBlackListEntity) {
        model.removeBlackList(blactListType, roomId, blackEntity.userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.removeSuccess(bean!!, blackEntity)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}