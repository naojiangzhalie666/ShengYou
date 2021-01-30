package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.RegisterContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;

import okhttp3.MultipartBody;

public class RegisterModel implements RegisterContract.Model {
    @Override
    public HttpObservable<BaseResponse<LogonResult>> register(MultipartBody.Part file, String userName, String userPhone, String userBirthday, String userSex, String wechatOpenid) {
        return HttpClient.getApi().register(file, userName, userPhone, userBirthday, userSex, wechatOpenid);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
