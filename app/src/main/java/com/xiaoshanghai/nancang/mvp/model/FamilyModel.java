package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.FamilyContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult;

import java.util.List;

public class FamilyModel implements FamilyContract.Model {
    @Override
    public HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId) {
        return HttpClient.getApi().getFamilyInfo(userId);
    }

    @Override
    public HttpObservable<BaseResponse<List<StartRecommendResult>>> getStartRecommend() {
        return HttpClient.getApi().getStartRecommend();
    }
}
