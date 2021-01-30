package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MaterialContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;

public class MaterialModel implements MaterialContract.Model {
    @Override
    public HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId) {
        return HttpClient.getApi().getFamilyInfo(userId);
    }
}
