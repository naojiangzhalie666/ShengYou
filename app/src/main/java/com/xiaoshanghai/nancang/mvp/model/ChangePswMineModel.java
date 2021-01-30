package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.ChangePswMineContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class ChangePswMineModel implements ChangePswMineContract.Model {
    @Override
    public HttpObservable<BaseResponse<Integer>> changePswMine(String type, String pswNew, String smsCode, String pswOld) {
        return HttpClient.getApi().changePswMine(type,pswNew,smsCode,pswOld);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }
}
