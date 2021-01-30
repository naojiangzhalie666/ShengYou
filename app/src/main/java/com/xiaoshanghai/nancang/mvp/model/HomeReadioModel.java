package com.xiaoshanghai.nancang.mvp.model;

import com.xiaoshanghai.nancang.mvp.contract.HomeRadioContract;
import com.xiaoshanghai.nancang.net.HttpClient;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult;
import com.xiaoshanghai.nancang.net.bean.RoomResult;

import java.util.List;

public class HomeReadioModel implements HomeRadioContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<RoomResult>>>> getRooms(String current, String size, String typeId) {
        return HttpClient.getApi().getRooms(current, size, typeId);
    }
}
