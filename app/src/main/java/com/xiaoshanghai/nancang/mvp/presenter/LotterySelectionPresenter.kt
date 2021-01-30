package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.LotterSelectionConstract
import com.xiaoshanghai.nancang.mvp.model.LotterSelectionModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.PrizeResult

class LotterySelectionPresenter : BasePresenter<LotterSelectionConstract.View>(), LotterSelectionConstract.Presenter {

    private val model by lazy { LotterSelectionModel() }

    override fun getPrize(kind: Int) {
        model.getPrize(kind)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<PrizeResult>() {
                    override fun success(bean: PrizeResult?, response: BaseResponse<PrizeResult>?) {
                        view.onPrizeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}