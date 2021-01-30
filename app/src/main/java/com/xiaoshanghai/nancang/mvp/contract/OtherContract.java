package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public interface OtherContract {
    interface View extends BaseView{

        /**
         * 登录成功
         */
        void loginSuccess(LogonResult bean);

        /**
         * 登录错误
         */
        void loginError(String msg);

        /**
         * 获取验证码成功
         */
        void loginCodeSuccess();

        /**
         * 获取Sig 成功
         * @param data
         * @param sig
         */
        void sigSuccess(LogonResult data, String sig);


        /**
         * 获取验证码失败
         */
        void loginCodeError(String msg);

        /**
         * 密码登录
         */
        void psdLogin();

        /**
         * 立即注册
         */
        void resgister();

    }

    interface Presenter {

        /**
         * 获取验证码
         * @param phoneNum
         */
        void getLoginCode(String phoneNum);

        /**
         * 登录
         * @param phoneNum 电话号码
         * @param code  验证码
         */
        void login(String phoneNum,String code);

        /**
         * 密码登录
         */
        void psdLogin();

        /**
         * 立即注册
         */
        void resgister();

        /**
         * 通过本地userId 获取userSig
         *
         * @param data
         */
        void getUserSig(LogonResult data);

        /**
         * 登录腾讯IM
         * @param data
         * @param sig
         */
        void loginIm(LogonResult data, String sig, IUIKitCallBack callBack);


    }

    interface Model{

        /**
         * 通过手机号获取验证码
         * @param phone
         * @return
         */
        HttpObservable<BaseResponse<String>> getStatusCode(String phone);

        /**
         * 通过手机号和验证码登录
         * @param phone
         * @param code
         * @return
         */
        HttpObservable<BaseResponse<LogonResult>> loginCode(String phone, String code, String city, String latitude, String longitude);


        /**
         * 获取userSig
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> getUserSig(String userId);


    }
}
