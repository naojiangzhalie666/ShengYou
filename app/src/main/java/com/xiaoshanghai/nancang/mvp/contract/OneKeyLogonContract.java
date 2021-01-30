package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

public interface OneKeyLogonContract {

    interface View extends BaseView {

        /**
         * 登录成功
         *
         * @param data
         */
        void onSuccess(LogonResult data);

        /**
         * 获取Sig 成功
         * @param data
         * @param sig
         */
        void sigSuccess(LogonResult data, String sig);

        /**
         * 请求登录接口失败
         *
         * @param msg
         */
        void onError(String msg);


    }

    interface Presenter {

        /**
         * 一键登录
         *
         * @param phoneNum
         */
        void oneKeyLogin(String phoneNum);

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


    interface Model {

        /**
         * 一键登录
         *
         * @param phoneNum
         * @return
         */
        HttpObservable<BaseResponse<LogonResult>> oneKeyLogin(String phoneNum);

        /**
         * 获取userSig
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> getUserSig(String userId);


    }

}
