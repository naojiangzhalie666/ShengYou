package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;

public interface LoginContract {

    interface View extends BaseView {
        /**
         * 一键登录
         */
        void oneKeyLogon(String phoneNumber);

        /**
         * 其他登录
         */
        void otherLogon();

        /**
         * 微信登录
         */
        void weixinLogon();

        /**
         * 隐私协议
         */
        void privacyAgreement();

        /**
         * 用户协议
         */
        void userAgreement();
    }

    interface Presenter {

        /**
         * 一键登录
         */
        void oneKeyLogon();

        /**
         * 其他登录
         */
        void otherLogon();

        /**
         * 微信登录
         */
        void weixinLogon();

        /**
         * 隐私协议
         */
        void privacyAgreement();

        /**
         * 用户协议
         */
        void userAgreement();

    }

    interface Model {

    }
}
