package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.NobleSettingContract
import com.xiaoshanghai.nancang.mvp.model.NobleSettingModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class NobleSettingPresenter:BasePresenter<NobleSettingContract.View>(),NobleSettingContract.Presenter {

    private val model by lazy { NobleSettingModel() }

    override fun setRoomStatus(userId: String?, status: String?) {
        model.enterRoomStatus(userId,status)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.onStatusSuccess(status,bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}