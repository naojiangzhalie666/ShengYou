package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import okhttp3.MultipartBody;

public interface RegisterContract {

    interface View extends BaseView {
        /**
         * 照相机
         */
        void camera();

        /**
         * 图库
         */
        void gallery();

        /**
         * 设置时间
         *
         * @param date
         */
        void setTime(String date);

        /**
         * 注册成功
         *
         * @param result
         */
        void registerSuccess(LogonResult result);

        /**
         * 获取Sig 成功
         * @param data
         * @param sig
         */
        void sigSuccess(LogonResult data, String sig);

        /**
         * 注册失败
         *
         * @param msg
         */
        void registerError(String msg);

    }

    interface Presenter {
        /**
         * 图片选择
         */
        void selectPhoto();

        /**
         * 时间选择
         */
        void selectTime(String date);

        /**
         * 注册
         *
         * @param file
         * @param userName
         * @param userPhone
         * @param userBirthday
         * @param userSex
         * @param wechatOpenid
         */
        void register(String file, String userName, String userPhone, String userBirthday, String userSex, String wechatOpenid);

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
         * 注册
         * @param file
         * @param userName
         * @param userPhone
         * @param userBirthday
         * @param userSex
         * @param wechatOpenid
         * @return
         */
        HttpObservable<BaseResponse<LogonResult>> register(MultipartBody.Part file,
                                                           String userName,
                                                           String userPhone,
                                                           String userBirthday,
                                                           String userSex,
                                                           String wechatOpenid);

        /**
         * 获取userSig
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> getUserSig(String userId);
    }
}
