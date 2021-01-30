package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.AccountContract;
import com.xiaoshanghai.nancang.mvp.model.AccountModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.AccountLoginAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public class AccountPresenter extends BasePresenter<AccountContract.View> implements AccountContract.Presenter {

    private AccountModel model;

    public AccountPresenter() {
        model = new AccountModel();
    }

    @Override
    public void login(String phoneNumber, String psd) {
        getView().showLoading();
        model.loginPsd(phoneNumber, psd)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<LogonResult>() {

                    @Override
                    protected void success(LogonResult bean, BaseResponse<LogonResult> response) {
                        getView().hideLoading();
                        LogonResult data = response.getData();
                        if (data != null) {

//                            if (LoginStatus.getValue(data.getStatus()).equals(LoginStatus.SUCCESS)){
//                                String strUser = new Gson().toJson(data.getUser());
//                                SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
//                                SPUtils.getInstance().put(SpConstant.APP_TOKEN, data.getToken());
//                            }

                            getView().loginSuccess(data);
                        } else {
                            getView().loginError(((Context) getView()).getResources().getString(R.string.login_error));
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().loginError(msg);

                    }
                });
    }

    @Override
    public void getUserSig(LogonResult data) {
        getView().showLoading();
        model.getUserSig(data.getUser().getId())
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {
                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        getView().hideLoading();
                        getView().sigSuccess(data, bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().loginError(msg);
                    }
                });
    }

    @Override
    public void loginIm(LogonResult data, String sig, IUIKitCallBack callBack) {

        SPUtils.getInstance().put(SpConstant.APP_SIG, sig);

        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance((AccountLoginAct) getView());
        trtcVoiceRoom.login(Constant.IM_APP_KEY, data.getUser().getId(), sig, (code, msg) -> {
            if (code == -1) {
                if (callBack != null) {
                    callBack.onError("登录失败", code, msg);
                }
            } else {
                callBack.onSuccess(null);
            }
        });
    }
}
