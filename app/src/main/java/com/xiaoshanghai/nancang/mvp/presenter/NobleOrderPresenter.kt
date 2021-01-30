package com.xiaoshanghai.nancang.mvp.presenter

import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import com.alipay.sdk.app.PayTask
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.NobleOrderContract
import com.xiaoshanghai.nancang.mvp.model.NobleOrderModel
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.NobleOrderAct
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI

class NobleOrderPresenter : BasePresenter<NobleOrderContract.View>(), NobleOrderContract.Presenter {

    val model: NobleOrderModel by lazy { NobleOrderModel() }

    override fun getMyGoldNum() {
        model.getMyGoldNum()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Double>() {

                    override fun success(bean: Double, response: BaseResponse<Double>) {
                        if (bean != null) {
                            view.goldNumSuccess(bean)
                        } else {
                            view.onError("请求错误")
                        }
                    }

                    override fun error(msg: String) {
                        view.onError(msg)
                    }


                })
    }

    override fun getNoble() {
        model.getNoble()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {

                    override fun success(bean: Int, response: BaseResponse<Int>) {
                        if (bean != null) {
                            view.nobleSuccess(bean)
                        } else {
                            view.onError("请求错误")
                        }

                    }

                    override fun error(msg: String) {
                        view.onError(msg)
                    }

                })
    }

    override fun goldBuy(nobleId: String) {

        view.showLoading()

        model.goldBuyNoble(nobleId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int, response: BaseResponse<Int>) {


                        if (bean != null) {

                            view.hideLoading()
                            view.goldBuyNobleSuccess(bean)
                        } else {
                            view.onError("请求错误")
                        }
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }

    override fun aliBuy(payTargetId: String) {
        view.showLoading()
        model.buyNoble("1", payTargetId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<BuyGoldResult>() {
                    override fun success(bean: BuyGoldResult, response: BaseResponse<BuyGoldResult>?) {
                        view.hideLoading()
                        view.aliBuySuccess(bean)
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }

    override fun wxBuy(payTargetId: String) {
        view.showLoading()
        model.buyNoble("2", payTargetId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<BuyGoldResult>() {
                    override fun success(bean: BuyGoldResult, response: BaseResponse<BuyGoldResult>?) {
                        view.hideLoading()
                        view.wxBuySuccess(bean)
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }

    override fun aliPay(handler: Handler, orderString: String, what: Int) {
        if (TextUtils.isEmpty(orderString)) return

        val payRunnable = Runnable {
            val alipay = PayTask(view as NobleOrderAct)
            val result = alipay.payV2(orderString, true)
            Log.i("msp", result.toString())
            val msg = Message()
            msg.what = what
            msg.obj = result
            handler.sendMessage(msg)
        }

        // 必须异步调用

        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()

    }

    override fun weCharPay(api: IWXAPI, result: BuyGoldResult) {

        val req = PayReq()
        req.appId = result.appid
        req.partnerId = result.partnerid
        req.prepayId = result.prepayid
        req.nonceStr = result.noncestr
        req.timeStamp = result.timestamp
        req.packageValue = result.packageX
        req.sign = result.sign
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req)
    }
}