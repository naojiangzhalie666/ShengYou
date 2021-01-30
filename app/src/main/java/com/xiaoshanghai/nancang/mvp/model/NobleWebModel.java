package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.NobleWebContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.NobleResult;

import java.util.List;

public class NobleWebModel implements NobleWebContract.Model {


    @Override
    public HttpObservable<BaseResponse<List<NobleResult>>> getNobleList() {
        return HttpClient.getApi().getNobleList();
    }
}
