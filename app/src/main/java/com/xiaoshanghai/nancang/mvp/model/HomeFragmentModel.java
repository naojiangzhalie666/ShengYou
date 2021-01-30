package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.HomeFragmentContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BannerResult;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeSortResult;

import java.util.List;

public class HomeFragmentModel implements HomeFragmentContract.Model {
    @Override
    public HttpObservable<BaseResponse<List<BannerResult>>> getBanner() {
        return HttpClient.getApi().getBanner();
    }

    @Override
    public HttpObservable<BaseResponse<List<HomeSortResult>>> getHomeSort() {
        return HttpClient.getApi().getHomeSort();
    }
}
