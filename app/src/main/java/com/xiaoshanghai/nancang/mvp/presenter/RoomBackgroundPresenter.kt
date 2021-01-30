package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.RoomBackgroundConstant
import com.xiaoshanghai.nancang.mvp.model.RoomBackgroundModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout

class RoomBackgroundPresenter : BasePresenter<RoomBackgroundConstant.View>(), RoomBackgroundConstant.Presenter {

    private val model by lazy { RoomBackgroundModel() }

    var initPage = 1
    var mPage = initPage
    private var size = 10

    override fun getRoomBg(refresh: RefreshLayout?) {
        model.getRoomBg(mPage.toString() + "", size.toString() + "")
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HomeRoomResult<MutableList<RoomBgEntity>>>() {
                    override fun success(bean: HomeRoomResult<MutableList<RoomBgEntity>>?, response: BaseResponse<HomeRoomResult<MutableList<RoomBgEntity>>>?) {
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


}