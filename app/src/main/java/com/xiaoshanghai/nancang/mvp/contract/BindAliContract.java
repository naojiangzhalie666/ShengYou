package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public interface BindAliContract {

    interface View extends BaseView {

        /**
         * 获取验证码成功
         */
        void codeSuccess();

        /**
         * 获取验证码失败
         */
        void codeError(String msg);

        /**
         * 成功
         * @param status
         */
        void onWeChatSuccess(int status);

        /**
         * 出问题
         * @param msg
         */
        void onWeChatError(String msg);

    }

    interface Presenter {

        /**
         * 获取验证码
         */
        void getCode(String phone);


        void bindWeChat(String account,String realName,String code);

    }

    interface Model {

        /**
         * 通过手机号获取验证码
         * @param phone
         * @return
         */
        HttpObservable<BaseResponse<String>> getStatusCode(String phone);

        /**
         * 绑定微信账号
         * @param type
         * @param account
         * @param realName
         * @param code
         * @return
         */
        HttpObservable<BaseResponse<Integer>> bindWeChat(String type, String account,String realName,String code);

    }

}
