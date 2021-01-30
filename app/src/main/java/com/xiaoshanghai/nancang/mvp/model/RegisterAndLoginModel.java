package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.RegisterAndLoginContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class RegisterAndLoginModel implements RegisterAndLoginContract.Model {

    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<String>> checkCode(String phone, String code) {
        return HttpClient.getApi().checkCode(phone,code);
    }
}
