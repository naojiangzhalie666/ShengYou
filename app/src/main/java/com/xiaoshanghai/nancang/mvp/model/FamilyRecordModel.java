package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.FamilyRecordContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.RecordResult;

import java.util.List;

public class FamilyRecordModel implements FamilyRecordContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<RecordResult>>>> getapplyList(String userId, String begin, String size) {
        return HttpClient.getApi().getapplyList(userId, begin, size);
    }
}
