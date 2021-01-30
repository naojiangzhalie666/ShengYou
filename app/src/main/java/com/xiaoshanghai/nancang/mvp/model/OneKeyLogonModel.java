package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.OneKeyLogonContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;


public class OneKeyLogonModel implements OneKeyLogonContract.Model {
    @Override
    public  HttpObservable<BaseResponse<LogonResult>>  oneKeyLogin(String phoneNum) {
        return HttpClient.getApi().oneKeyLogin(phoneNum);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
