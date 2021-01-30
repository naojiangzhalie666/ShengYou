package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomNotiyContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class RoomNotifyModel:RoomNotiyContract.Model {
    override fun editRoomNotify(roomId: String?, noticeTitle: String?, noticeComment: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().editRoomNotify(roomId, noticeTitle, noticeComment)
    }
}