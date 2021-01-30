package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.OtherContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;

public class OtherLoginModel implements OtherContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<LogonResult>> loginCode(String phone, String code,String city,String latitude,String longitude) {
        return HttpClient.getApi().loginCode(phone, code,city,latitude,longitude);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
