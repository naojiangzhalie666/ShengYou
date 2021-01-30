package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.BindWeChatContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public class BindWeChatModel implements BindWeChatContract.Model {

    @Override
    public HttpObservable<BaseResponse<String>> getStatusCode(String phone) {
        return HttpClient.getApi().getStatusCode(phone);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> bindWeChat(String type, String account, String realName, String code) {
        return HttpClient.getApi().bindWeChat(type, account, realName, code);
    }

}
