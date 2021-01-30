package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.LotteryRecordContract
import com.xiaoshanghai.nancang.mvp.model.LotteryRecordModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.AwardsEntity
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class LotteryRecordPresenter:BasePresenter<LotteryRecordContract.View>(),LotteryRecordContract.Presenter {

    private val model by lazy { LotteryRecordModel() }
    override fun getLotterRecord(kind: Int) {
        model.getLotterRecord(kind)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<MutableList<AwardsEntity>>(){
                    override fun success(bean: MutableList<AwardsEntity>?, response: BaseResponse<MutableList<AwardsEntity>>?) {
                        view.onRecordSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}