package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.BlackListMineContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.BlackListMineBean;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;

import java.util.List;

public class BlackListMineModel implements BlackListMineContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<BlackListMineBean>>>> getBlackListMine(String current, String size, String blackListType, String currentId) {
        return HttpClient.getApi().getBlackListMine(current,size,blackListType,currentId);
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> removeBlackMine(String id) {
        return HttpClient.getApi().removeBlackMine(id);
    }
}
