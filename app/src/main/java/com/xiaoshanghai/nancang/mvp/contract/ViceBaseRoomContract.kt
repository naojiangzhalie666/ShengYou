package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.callback.FollowCallback
import com.xiaoshanghai.nancang.callback.OnHiChatCallback
import com.xiaoshanghai.nancang.callback.RoomUserCallback
import com.xiaoshanghai.nancang.mvp.ui.adapter.VoiceLiveSeatAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.MikeView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GiftAllResult
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomCallback
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

interface ViceBaseRoomContract {

    interface View : BaseView {

        fun onError(msg: String?)

        fun userOnSuccess(userInfo: VoiceRoomSeatEntity.UserInfo?)

        fun takeSeatSuccess(status: Int?, position: Int?)

        fun roomGiftSuccess(roomGift: GiftAllResult?)

        fun onFollow(status: String?)

        fun onUnFollow(Status: String?)

        fun onAddManager(status: Int?, userId: String?)

        fun onRemoveManager(status: Int?, userId: String?)

        fun onExitRoomSuccess(status: Int?, roomId: String?)

        fun onAddAndRemoveBlackSuccess(status: String?, resultStatus: Int?, roomId: String?, userId: String?)

        fun onClearGiftSuccess(status: Int?, userId: String?)

        fun onConfigSuccess(status: String?)

    }

    interface Presenter {

        /**
         * 根据userId获取进入房间的userinfo
         */
        fun getRoomUser(userId: String?, roomId: String)

        /**
         * 上麦
         */
        fun takeSeat(roomId: String, mikeOrder: String)

        /**
         * 下麦
         */
        fun downSeat()

        /**
         * 发送上麦自定义消息
         */
        fun customMsgCmdInRoom(message: String?, mMainSeatUserId: VoiceRoomSeatEntity?, mVoiceRoomSeatEntityList: MutableList<VoiceRoomSeatEntity>?, mVoiceRoomSeatAdapter: VoiceLiveSeatAdapter?, mMainMike: MikeView?, gifeValue: Boolean?)

        /**
         * 发送进房消息
         */
        fun sendInRoomMsg(selfUserInfo: VoiceRoomSeatEntity.UserInfo?, mTRTCVoiceRoom: TRTCVoiceRoom?, callback: TRTCVoiceRoomCallback.ActionCallback?)

        /**
         * 发送文本消息
         */
        fun sendTextMsg(msg: String, selfUserInfo: VoiceRoomSeatEntity.UserInfo?, mTRTCVoiceRoom: TRTCVoiceRoom?, callback: TRTCVoiceRoomCallback.ActionCallback?)

        /**
         * 获取房间礼物
         */
        fun getRoomGift()

        /**
         * 修改房间公屏 1公屏 0关闭公屏
         */
        fun setGpSwitch(roomId: String?, type: Int?, callback: TRTCVoiceRoomCallback.ActionCallback?)

        /**
         * 设置礼物值开关
         */
        fun setGiftSwitch(roomId: String?, giftValue: String?, callback: TRTCVoiceRoomCallback.ActionCallback?)

        /**
         * 清空礼物值
         */
        fun clearGiftValue(roomId: String?, userId: String?)

        /**
         * 请求用户资料
         */
        fun getUser(userId: String?, roomId: String?, mRoomUser: RoomUserCallback?)

        /**
         * 查询关注状况
         */
        fun queryFollow(userId: String, callback: FollowCallback?)

        /**
         * 关注或是取消关注
         */
        fun follow(userId: String?, status: String?)

        /**
         * 添加管理员
         */
        fun addManager(roomId: String?, userId: String?)

        /**
         * 移除管理员
         */
        fun removeManager(roomId: String?, userId: String?)

        /**
         * 退出房间
         */
        fun exitRoom(roomId: String?)

        /**
         * 添加或者移除黑名单
         */
        fun addBlackList(status: String?, roomId: String?, userId: String?)

        /**
         * 移除黑名单
         */
        fun removeBlackList(status: String?, roomId: String?, userId: String?)

        /**
         * 随机进入嗨聊房
         */
        fun enterHiChatRoom(callback: OnHiChatCallback?)

        /**
         * 获取房间抽奖配置
         */
        fun getRoomConfig()

    }

    interface Model {

        /**
         * 获取用户信息
         */
        fun getRoomUser(userId: String?, roomId: String): HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>>

        /**
         * 上麦
         */
        fun takeSeat(roomId: String?, mikeOrder: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 下麦
         */
        fun outMike(): HttpObservable<BaseResponse<Int>>

        /**
         * 获取房间礼物
         */
        fun getRoomGift(): HttpObservable<BaseResponse<GiftAllResult>>

        /**
         * 公屏接口
         */
        fun setPublic(roomId: String?, type: Int): HttpObservable<BaseResponse<Int>>

        /**
         * 设置房间礼物值开关
         */
        fun setRoomGiftValue(roomId: String?, giftValue: String): HttpObservable<BaseResponse<String>>

        /**
         * 清空礼物值
         */
        fun clearGiftValue(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 查询关注
         */
        fun queryFollow(userId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 关注或是取消关注
         */
        fun follow(userId: String?, status: String?): HttpObservable<BaseResponse<String>>

        /**
         * 添加管理员
         */
        fun addManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 移除管理员
         */
        fun removeManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 退出房间
         */
        fun exitRoom(roomId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 添加和移除黑名单
         */
        fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 移除黑名单
         */
        fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>>

        /**
         * 随机进入嗨聊房
         */
        fun getHiChat(): HttpObservable<BaseResponse<String>>

        /**
         * 获取房间抽奖配置
         */
        fun getRoomConfig():HttpObservable<BaseResponse<Map<String,String>>>

    }

}