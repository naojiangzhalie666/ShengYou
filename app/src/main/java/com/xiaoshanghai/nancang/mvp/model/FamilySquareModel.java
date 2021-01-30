package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.FamilySquareContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.FamilyRankResult;

import java.util.List;

public class FamilySquareModel implements FamilySquareContract.Model {
    @Override
    public HttpObservable<BaseResponse<List<FamilyRankResult>>> getFamilyRank() {
        return HttpClient.getApi().getFamilyRank();
    }
}
