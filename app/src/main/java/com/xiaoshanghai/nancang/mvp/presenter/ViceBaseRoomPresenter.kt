package com.xiaoshanghai.nancang.mvp.presenter

import android.text.TextUtils
import com.google.gson.Gson
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.callback.FollowCallback
import com.xiaoshanghai.nancang.callback.OnHiChatCallback
import com.xiaoshanghai.nancang.callback.RoomUserCallback
import com.xiaoshanghai.nancang.constant.CustomMsgConstant
import com.xiaoshanghai.nancang.mvp.contract.ViceBaseRoomContract
import com.xiaoshanghai.nancang.mvp.model.ViceBaseRoomModel
import com.xiaoshanghai.nancang.mvp.ui.adapter.VoiceLiveSeatAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.MikeView
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GiftAllResult
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomCallback

class ViceBaseRoomPresenter : BasePresenter<ViceBaseRoomContract.View>(), ViceBaseRoomContract.Presenter {


    private val model: ViceBaseRoomModel by lazy { ViceBaseRoomModel() }


    /**
     * 获取用户信息
     */
    override fun getRoomUser(userId: String?, roomId: String) {
        if (view == null) return
        model.getRoomUser(userId, roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<VoiceRoomSeatEntity.UserInfo>() {

                    override fun success(bean: VoiceRoomSeatEntity.UserInfo?, response: BaseResponse<VoiceRoomSeatEntity.UserInfo>?) {
                        view.userOnSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }


                })
    }

    /**
     * 上麦
     */
    override fun takeSeat(roomId: String, mikeOrder: String) {
        if (view == null) return
        model.takeSeat(roomId, mikeOrder)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {

                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
//
//                        if (bean == 1) {
//                            mTRTCVoiceRoom.enterSeat(position) { code, msg ->
//                                if (code == 0) {
//                                    selfUserInfo.seatIndex = position
//                                    val toJson = Gson().toJson(selfUserInfo)
//                                    mTRTCVoiceRoom.sendRoomCustomMsg("102", toJson) { code, msg ->
//                                        view.takeSeatSuccess(bean, position)
//                                    }
//                                }
//                            }
//                        }
//                        view.takeSeatSuccess(bean, position)
                    }

                    override fun error(msg: String?) {
//                        view.onError(msg)
                    }


                })
    }

    /**
     * 下麦
     */
    override fun downSeat() {
        if (view == null) return
        model.outMike()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {

                    }

                    override fun error(msg: String?) {

                    }

                })
    }


//    override fun downSeat(mTRTCVoiceRoom: TRTCVoiceRoom, callback: TRTCVoiceRoomCallback.ActionCallback) {
//        model.outMike()
//                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
//                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
//
//                        if (bean == 1) {
//                            mTRTCVoiceRoom.leaveSeat(callback)
//                        } else {
//                            view.onError("下麦失败")
//                        }
//
//                    }
//
//                    override fun error(msg: String?) {
//                        view.onError(msg)
//
//                    }
//
//                })
//    }

    /**
     * 收到上麦信令消息
     */
    override fun customMsgCmdInRoom(message: String?, mMainSeatUserId: VoiceRoomSeatEntity?, mVoiceRoomSeatEntityList: MutableList<VoiceRoomSeatEntity>?, mVoiceRoomSeatAdapter: VoiceLiveSeatAdapter?, mMainMike: MikeView?, giftValue: Boolean?) {

        val userInfo = Gson().fromJson(message, VoiceRoomSeatEntity.UserInfo::class.java)

        if (userInfo.seatIndex == 0) {
            if (mMainSeatUserId?.userId == userInfo.id) {
                mMainSeatUserId?.userAvatar = userInfo.userPicture
                mMainSeatUserId?.userInfo = userInfo
                mMainMike?.setSeatResult(mMainSeatUserId)
                return
            }
        } else {
            val seatEntity = mVoiceRoomSeatEntityList?.get(userInfo.seatIndex - 1)
            seatEntity?.userInfo = userInfo
            mVoiceRoomSeatAdapter?.notifyDataSetChanged()
        }

    }

    /**
     * 发送进房信令
     */
    override fun sendInRoomMsg(selfUserInfo: VoiceRoomSeatEntity.UserInfo?, mTRTCVoiceRoom: TRTCVoiceRoom?, callback: TRTCVoiceRoomCallback.ActionCallback?) {
        val toJson = Gson().toJson(selfUserInfo)
        mTRTCVoiceRoom?.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdInRoom, toJson, callback)
    }

    /**
     * 发送文本自定义消息
     */
    override fun sendTextMsg(msg: String, selfUserInfo: VoiceRoomSeatEntity.UserInfo?, mTRTCVoiceRoom: TRTCVoiceRoom?, callback: TRTCVoiceRoomCallback.ActionCallback?) {
        selfUserInfo?.message = msg
        val toJson = Gson().toJson(selfUserInfo)
        mTRTCVoiceRoom?.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdText, toJson, callback)
    }

    /**
     * 获取房间礼物
     */
    override fun getRoomGift() {
        if (view == null) return
        model.getRoomGift()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<GiftAllResult>() {
                    override fun success(bean: GiftAllResult?, response: BaseResponse<GiftAllResult>?) {
                        view.roomGiftSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun setGpSwitch(roomId: String?, type: Int?, callback: TRTCVoiceRoomCallback.ActionCallback?) {
        if (view == null) return
        model.setPublic(roomId, type!!)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        callback?.onCallback(bean!!, null)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun setGiftSwitch(roomId: String?, giftValue: String?, callback: TRTCVoiceRoomCallback.ActionCallback?) {
        if (view == null) return
        model.setRoomGiftValue(roomId, giftValue!!)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        callback?.onCallback(Integer.valueOf(bean), "")

                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun clearGiftValue(roomId: String?, userId: String?) {
        if (view == null) return
        model.clearGiftValue(roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onClearGiftSuccess(bean,userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)

                    }

                })
    }

    override fun getUser(userId: String?, roomId: String?, mRoomUser: RoomUserCallback?) {
        if (view == null) return
        model.getRoomUser(userId, roomId!!)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<VoiceRoomSeatEntity.UserInfo>() {

                    override fun success(bean: VoiceRoomSeatEntity.UserInfo?, response: BaseResponse<VoiceRoomSeatEntity.UserInfo>?) {
                        mRoomUser?.onRoomUser(bean)

                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }


                })
    }

    override fun queryFollow(userId: String, callback: FollowCallback?) {
        if (view == null) return
        model.queryFollow(userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        callback?.onFollow(bean!!)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun follow(userId: String?, status: String?) {
        if (view == null) return
        model.follow(userId, status)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        if (TextUtils.isEmpty(bean)) {
                            view.onError("请求失败")
                        } else {
                            if (status == "1") {
                                view.onFollow(bean)
                            } else if (status == "2") {
                                view.onUnFollow(bean)
                            }
                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun addManager(roomId: String?, userId: String?) {
        if (view == null) return
        model.addManager(roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddManager(bean, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })

    }

    override fun removeManager(roomId: String?, userId: String?) {
        if (view == null) return
        model.removeManager(roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onRemoveManager(bean, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun exitRoom(roomId: String?) {
        if (view == null) return
        model.exitRoom(roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onExitRoomSuccess(bean!!, roomId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun addBlackList(status: String?, roomId: String?, userId: String?) {
        if (view == null) return
        model.addBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("1", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


    override fun removeBlackList(status: String?, roomId: String?, userId: String?) {
        if (view == null) return
        model.removeBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("2", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun enterHiChatRoom(callback: OnHiChatCallback?) {
        if (view == null) return
        model.getHiChat()
                .execOnThread(view.getActLifeRecycle(),object:HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        callback?.onHiChat(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                } )
    }

    override fun getRoomConfig() {
        model.getRoomConfig()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<Map<String,String>>(){
//                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
//                        view.onConfigSuccess(bean)
//                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                    override fun success(bean: Map<String, String>?, response: BaseResponse<Map<String, String>>?) {
                      var status =  bean?.get("android")
                        view.onConfigSuccess(status)
                    }

                })
    }


}