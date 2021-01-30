package com.xiaoshanghai.nancang.mvp.presenter;

import com.xiaoshanghai.nancang.base.BasePresenter;
import com.xiaoshanghai.nancang.mvp.contract.LoginContract;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    @Override
    public void oneKeyLogon() {
//        getView().oneKeyLogon("15342597207");
//         RxPermissions rxPermissions = new RxPermissions((LoginActivity) getView());
//
//        PermissionUtil.readPhonestate(new PermissionUtil.RequestPermission() {
//            @Override
//            public void onRequestPermissionSuccess() {
//                String phoneNum = DeviceUtils.getPhoneNum((Context) getView());
//                if (!TextUtils.isEmpty(phoneNum)) {
                    getView().oneKeyLogon("");
//                }
//            }
//
//            @Override
//            public void onRequestPermissionFailure(List<String> permissions) {
//
//            }
//
//            @Override
//            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
//
//            }
//        }, rxPermissions);

    }

    @Override
    public void otherLogon() {
        getView().otherLogon();
    }

    @Override
    public void weixinLogon() {
        getView().weixinLogon();
    }

    @Override
    public void privacyAgreement() {
        getView().privacyAgreement();
    }

    @Override
    public void userAgreement() {
        getView().userAgreement();
    }

}
