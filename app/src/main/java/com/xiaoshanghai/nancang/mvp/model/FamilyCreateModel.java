package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.FamilyCreateContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

import okhttp3.MultipartBody;

public class FamilyCreateModel implements FamilyCreateContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> getFamilyApplyStatu(String userId) {
        return HttpClient.getApi().getFamilyApplyStatu(userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> createFamily(String clanName, String applicantId, String applyComment, MultipartBody.Part file) {
        return HttpClient.getApi().createFamily(clanName, applicantId, applyComment, file);
    }
}
