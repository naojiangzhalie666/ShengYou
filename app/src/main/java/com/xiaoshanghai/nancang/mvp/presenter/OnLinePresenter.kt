package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.OnLineContract
import com.xiaoshanghai.nancang.mvp.model.OnLineModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.scwang.smartrefresh.layout.api.RefreshLayout

class OnLinePresenter : BasePresenter<OnLineContract.View>(), OnLineContract.Presenter {

    var initPage = 1
    var mPage = initPage
    private var size = 10

    private val model by lazy { OnLineModel() }

    override fun loadOnLineUser(refresh: RefreshLayout?, roomId: String?) {
        model?.getOnLineUser(mPage.toString() + "", size.toString() + "", roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HomeRoomResult<MutableList<OnLineUserResult>>>() {
                    override fun success(bean: HomeRoomResult<MutableList<OnLineUserResult>>?, response: BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>?) {
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