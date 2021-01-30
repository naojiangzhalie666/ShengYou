package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.MineContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.CustomerServiceEntity;
import com.xiaoshanghai.nancang.net.bean.MineReslut;
import com.xiaoshanghai.nancang.net.bean.MyFamilyResult;

public class MineModel implements MineContract.Model {

    @Override
    public HttpObservable<BaseResponse<MineReslut>> getMine(String userId) {
        return HttpClient.getApi().getMine(userId);
    }

    @Override
    public HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId) {
        return HttpClient.getApi().getFamilyInfo(userId);
    }

    @Override
    public HttpObservable<BaseResponse<CustomerServiceEntity>> getService() {
        return HttpClient.getApi().getService();
    }
}
