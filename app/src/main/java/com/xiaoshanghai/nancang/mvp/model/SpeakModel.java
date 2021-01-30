package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.SpeakContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.SystemNotic;

import java.util.List;

public class SpeakModel implements SpeakContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<SystemNotic>>>> getSystemNotic(String current, String size) {
        return HttpClient.getApi().getSystemNotic(current, size);
    }
}
