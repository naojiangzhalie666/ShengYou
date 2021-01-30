package com.xiaoshanghai.nancang.mvp.presenter;


import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.mvp.contract.OneKeyLogonContract;
import com.xiaoshanghai.nancang.mvp.model.OneKeyLogonModel;

import com.xiaoshanghai.nancang.mvp.ui.activity.login.OneKeyLoginAct;
import com.xiaoshanghai.nancang.net.HttpObserver;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public class OneKeyLoginPresenter extends BasePresenter<OneKeyLogonContract.View> implements OneKeyLogonContract.Presenter {

    private OneKeyLogonContract.Model model;

    public OneKeyLoginPresenter() {
        model = new OneKeyLogonModel();
    }

    @Override
    public void oneKeyLogin(String phoneNum) {
        getView().showLoading();
        model.oneKeyLogin(phoneNum)
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<LogonResult>() {

                    @Override
                    protected void success(LogonResult bean, BaseResponse<LogonResult> response) {
                        getView().hideLoading();
                        getView().onSuccess(bean);
                    }

                    @Override
                    protected void error(String msg) {
                        getView().hideLoading();
                        getView().onError(msg);
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
                        getView().onError(msg);
                    }
                });
    }

    @Override
    public void loginIm(LogonResult data, String sig, IUIKitCallBack callBack) {

        SPUtils.getInstance().put(SpConstant.APP_SIG, sig);

//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserId(data.getUser().getId());
//        TUIKitConfigs.getConfigs().getGeneralConfig().setUserSig(sig);

        TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance((OneKeyLoginAct) getView());
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
//                    AVCallManager.getInstance().init(((OneKeyLoginAct) getView()).getApplicationContext());
//                }
                callBack.onSuccess(null);
            }
        });

//        TUIKit.login(data.getUser().getId(), sig, callBack);

    }


}
