package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.LotteryContract
import com.xiaoshanghai.nancang.mvp.model.LotteryModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.LotteryPirzeResult
import com.xiaoshanghai.nancang.net.bean.PrizeResult

class LotteryPresenter:BasePresenter<LotteryContract.View>(),LotteryContract.Presenter {

    private val model by lazy { LotteryModel() }

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

    override fun getMyGold() {
        model.getMyGoldNum()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<Double>() {
                    override fun success(bean: Double?, response: BaseResponse<Double>?) {
                        view.onGoldSuccess(bean!!)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun lottery(type: Int, kind: Int) {
        model.lottery(type,kind)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<LotteryPirzeResult>(){
                    override fun success(bean: LotteryPirzeResult?, response: BaseResponse<LotteryPirzeResult>?) {
                        view.onLotterySuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)

                    }

                })
    }
}