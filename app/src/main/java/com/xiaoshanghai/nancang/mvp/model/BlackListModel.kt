package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.BlackListConstract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.RoomBlackListEntity

class BlackListModel : BlackListConstract.Model {
    override fun getRoomBlackList(current: String?, size: String?, blacklistType: String?, current_id: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<RoomBlackListEntity>>>> {
        return HttpClient.getApi().getRoomBlackList(current, size, blacklistType, current_id)
    }

    override fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId)
    }
}