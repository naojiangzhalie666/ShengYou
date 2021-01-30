package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.LotteryRuleContract
import com.xiaoshanghai.nancang.mvp.model.LotteryRuleModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.PrizeResult

class LotteryRulePresenter:BasePresenter<LotteryRuleContract.View>(),LotteryRuleContract.Presenter {

    private val model by lazy { LotteryRuleModel() }

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