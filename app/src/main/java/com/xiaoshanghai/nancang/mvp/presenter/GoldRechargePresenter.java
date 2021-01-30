package com.xiaoshanghai.nancang.mvp.presenter;

import com.alipay.sdk.app.PayTask;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.GoldRechargeContract;
import com.xiaoshanghai.nancang.mvp.model.GoldRechargeModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.GoldRechargeAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult;
import com.xiaoshanghai.nancang.net.bean.GoldResult;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Map;

public class GoldRechargePresenter extends BasePresenter<GoldRechargeContract.View> implements GoldRechargeContract.Presenter {

    private GoldRechargeModel model;

    public GoldRechargePresenter() {

        model = new GoldRechargeModel();

    }

    @Override
    public void getMyGoldNum() {
        model.getMyGoldNum()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<Double>() {
                    @Override
                    protected void success(Double bean, BaseResponse<Double> response) {
                        if (bean != null){
                            getView().myGoldSuccess(bean);
                        } else {
                            getView().onError("请求错误");
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().onError(msg);

                    }
                });
    }

    @Override
    public void getGoldList(String userId) {



        model.getGoldList("1", "100", userId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<HomeRoomResult<List<GoldResult>>>() {
                    @Override
                    protected void success(HomeRoomResult<List<GoldResult>> bean, BaseResponse<HomeRoomResult<List<GoldResult>>> response) {

                        getView().goldListSuccess(bean.getRecords());
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void buyGold(int payType, String goldId, RelativeLayout mRlRecharge) {
        mRlRecharge.setEnabled(false);
        getView().showLoading();
        model.buyGold(payType, goldId)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<BuyGoldResult>() {
                    @Override
                    protected void success(BuyGoldResult bean, BaseResponse<BuyGoldResult> response) {
                        getView().hideLoading();
                        mRlRecharge.setEnabled(true);
                        getView().buyGoldSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        mRlRecharge.setEnabled(true);
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void weCharPay(IWXAPI api, BuyGoldResult result) {
        PayReq req = new PayReq();
        req.appId = result.getAppid();
        req.partnerId = result.getPartnerid();
        req.prepayId = result.getPrepayid();
        req.nonceStr = result.getNoncestr();
        req.timeStamp = result.getTimestamp();
        req.packageValue = result.getPackageX();
        req.sign = result.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    @Override
    public void aliPay(Handler handler, String orderString, int what) {

        if (TextUtils.isEmpty(orderString)) return;

        final Runnable payRunnable = () -> {
            PayTask alipay = new PayTask((GoldRechargeAct) getView());
            Map<String, String> result = alipay.payV2(orderString, true);
            Log.i("msp", result.toString());

            Message msg = new Message();
            msg.what = what;
            msg.obj = result;
            handler.sendMessage(msg);
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

}
