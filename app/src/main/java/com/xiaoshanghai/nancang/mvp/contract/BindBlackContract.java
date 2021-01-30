package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public interface BindBlackContract {

    interface View extends BaseView {

        /**
         * 获取验证码成功
         */
        void codeSuccess();

        /**
         * 获取验证码失败
         */
        void codeError(String msg);

        void onBindSuccess(int status);

        void onBindError(String msg);


    }

    interface Presenter {

        /**
         * 获取验证码
         */
        void getCode(String phone);

        /**
         * 绑定银行卡号
         * @param account
         * @param bankType
         * @param bankName
         * @param realName
         * @param code
         */
        void bindBlackNum(String account, String bankType,String bankName,String realName,String code);


    }

    interface Model {

        /**
         * 通过手机号获取验证码
         * @param phone
         * @return
         */
        HttpObservable<BaseResponse<String>> getStatusCode(String phone);

        /**
         * 银行卡号绑定
         * @param type
         * @param account
         * @param bankType
         * @param bankName
         * @param realName
         * @param code
         * @return
         */
        HttpObservable<BaseResponse<Integer>> bindBlackNum(String type, String account, String bankType,String bankName,String realName,String code);

    }

}
