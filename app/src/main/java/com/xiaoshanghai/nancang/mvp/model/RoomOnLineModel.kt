package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.RoomOnLineContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

class RoomOnLineModel: RoomOnLineContract.Model {
    override fun getOnLineUser(current: String?, size: String?, roomId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>> {
        return HttpClient.getApi().getOnLineUser(current, size, roomId)
    }

    override fun getRoomUser(userId: String?, roomId: String): HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> {
        return HttpClient.getApi().getRoomUser(userId, roomId)
    }

    override fun queryFollow(userId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().queryFollow(userId)
    }

    override fun follow(userId: String?, status: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().follow(userId, status)
    }

    override fun addManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().addManager(roomId, userId)
    }

    override fun removeManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeManager(roomId, userId)
    }


    override fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().addBlackList(blacklistType, currentId, blacklistUserId)
    }

    override fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId)
    }

}