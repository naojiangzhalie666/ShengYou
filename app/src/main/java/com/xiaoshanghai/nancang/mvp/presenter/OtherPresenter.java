package com.xiaoshanghai.nancang.mvp.presenter;

import android.content.Context;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseApplication;
import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.OtherContract;
import com.xiaoshanghai.nancang.mvp.model.OtherLoginModel;
import com.xiaoshanghai.nancang.mvp.ui.activity.login.OtherLoginAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public class OtherPresenter extends BasePresenter<OtherContract.View> implements OtherContract.Presenter {

    private OtherLoginModel model;

    public OtherPresenter() {
        model = new OtherLoginModel();
    }

    @Override
    public void getLoginCode(String phoneNum) {

        model.getStatusCode(phoneNum)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<String>() {

                    @Override
                    protected void success(String bean, BaseResponse<String> response) {
                        if (bean.equals("1")) {
                            getView().loginCodeSuccess();
                        } else {
                            getView().loginCodeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                        }

                    }

                    @Override
                    protected void error(String msg) {
                        getView().loginCodeError(((Context) getView()).getResources().getString(R.string.send_sms_error));
                    }
                });
    }

    @Override
    public void login(String phoneNum, String code) {
        getView().showLoading();
        model.loginCode(phoneNum, code, BaseApplication.city,BaseApplication.latitude,BaseApplication.longitude)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<LogonResult>() {

                    @Override
                    protected void success(LogonResult bean, BaseResponse<LogonResult> response) {
                        getView().hideLoading();
                        LogonResult data = response.getData();

                        if (data != null) {
//                            if (LoginStatus.getValue(data.getStatus()).equals(LoginStatus.SUCCESS)) {
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
    public void psdLogin() {
        getView().psdLogin();
    }

    @Override
    public void resgister() {
        getView().resgister();

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

//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserId(data.getUser().getId());
//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserSig(sig);

        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance((OtherLoginAct) getView());
        trtcVoiceRoom.login(Constant.IM_APP_KEY, data.getUser().getId(), sig, (code, msg) -> {
            if (code == -1) {
                if (callBack!=null) {
                    callBack.onError("登录失败",code,msg);
                }
            } else {
//                if (TUIKitConfigs.getConfigs().getGeneralConfig().isSupportAVCall()) {
//                    UserModel self = new UserModel();
//                    self.userId = data.getUser().getId();
//                    self.userSig = sig;
//                    ProfileManager.getInstance().setUserModel(self);
//                    AVCallManager.getInstance().init(((OtherLoginAct) getView()).getApplicationContext());
//                }
                callBack.onSuccess(null);
            }
        });

//        TUIKit.login(data.getUser().getId(), sig, callBack);
    }
}
