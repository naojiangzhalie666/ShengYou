package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.SystemNoticContract
import com.xiaoshanghai.nancang.mvp.model.SystemNoticModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.SystemNotic
import com.scwang.smartrefresh.layout.api.RefreshLayout

class SystemNoticPresenter: BasePresenter<SystemNoticContract.View>(),SystemNoticContract.Presenter {

    var initPage = 1
    var mPage = initPage
    private var size = 10

    private val model by lazy { SystemNoticModel() }

    override fun getSystemNoic(refresh: RefreshLayout?) {
        model.getSystemNotic(mPage.toString() + "", size.toString() + "")
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<HomeRoomResult<MutableList<SystemNotic>>>(){
                    override fun success(bean: HomeRoomResult<MutableList<SystemNotic>>?, response: BaseResponse<HomeRoomResult<MutableList<SystemNotic>>>?) {
                        val records = bean?.records
//                        if (records != null) {
                            if (mPage == initPage) {

                                view.refreshSuccess(refresh, records)

                            } else {

                                view.loadMoreSuccess(refresh, records)

                            }
                            if (records?.size!! > 0) mPage++
//                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}