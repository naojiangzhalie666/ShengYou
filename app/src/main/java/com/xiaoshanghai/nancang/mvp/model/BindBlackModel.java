package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.BindBlackContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class BindBlackModel implements BindBlackContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> bindBlackNum(String type, String account, String bankType, String bankName, String realName, String code) {
        return HttpClient.getApi().bindBlackNum(type, account, bankType, bankName, realName, code);
    }

}
