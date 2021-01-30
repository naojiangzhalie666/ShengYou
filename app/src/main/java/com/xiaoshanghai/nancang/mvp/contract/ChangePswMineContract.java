package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public interface ChangePswMineContract {


    interface View extends BaseView {
        void changeSuccess();

        void changeError(String message);

        void getCodeSuccess();

        void getCodeError(String msg);

        void resetPswSuccess();

        void resetPswError(String msg);

    }

    interface Presenter {
        /**
         * @param type    1验证码修改密码 2旧密码修改密码
         * @param pswNew  新密码
         * @param smsCode 验证码
         * @param pswOld  旧密码
         */
        void changePsw(String type, String pswNew, String smsCode, String pswOld);


        void getCode(String  phone);
    }


    interface Model {
        HttpObservable<BaseResponse<Integer>> changePswMine(String type, String pswNew, String smsCode, String pswOld);


        /**
         * 通过手机号获取验证码
         * @param phone
         * @return
         */
        HttpObservable<BaseResponse<String>> getStatusCode(String phone);

    }
}
