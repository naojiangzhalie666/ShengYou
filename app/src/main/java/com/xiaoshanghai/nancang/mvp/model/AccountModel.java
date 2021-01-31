package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.base.BaseApplication;
import com.xiaoshanghai.nancang.mvp.contract.AccountContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.LogonResult;
import com.yhao.floatwindow.FloatWindow;

public class AccountModel implements AccountContract.Model {
    @Override
    public HttpObservable<BaseResponse<LogonResult>> loginPsd(String phone, String password) {
        return HttpClient.getApi().loginPsd(phone,password, BaseApplication.city,BaseApplication.latitude, BaseApplication.longitude);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getUserSig(String userId) {
        return HttpClient.getApi().getUserSig(userId);
    }
}
