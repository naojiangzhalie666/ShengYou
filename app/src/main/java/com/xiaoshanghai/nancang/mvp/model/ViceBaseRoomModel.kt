package com.xiaoshanghai.nancang.mvp.model

import com.xiaoshanghai.nancang.mvp.contract.ViceBaseRoomContract
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GiftAllResult
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

class ViceBaseRoomModel : ViceBaseRoomContract.Model {

    override fun getRoomUser(userId: String?, roomId: String): HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> {
        return HttpClient.getApi().getRoomUser(userId, roomId)
    }


    override fun takeSeat(roomId: String?, mikeOrder: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().takeSeat(roomId, mikeOrder)
    }

    override fun outMike(): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().outMike()
    }

    override fun getRoomGift(): HttpObservable<BaseResponse<GiftAllResult>> {
        return HttpClient.getApi().roomGift
    }

    override fun setPublic(roomId: String?, type: Int): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().setRoomPublic(roomId,type)
    }

    override fun setRoomGiftValue(roomId: String?, giftValue: String): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().setRoomGiftValue(roomId, giftValue)
    }

    override fun clearGiftValue(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().clearGiftValue(roomId, userId)
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

    override fun exitRoom(roomId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().exitRoom(roomId)
    }

    override fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().addBlackList(blacklistType, currentId, blacklistUserId)
    }

    override fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId)
    }

    override fun getHiChat(): HttpObservable<BaseResponse<String>> {
        return  HttpClient.getApi().hiChat
    }

    override fun getRoomConfig(): HttpObservable<BaseResponse<Map<String,String>>> {
        return HttpClient.getApi().roomConfig
    }
}