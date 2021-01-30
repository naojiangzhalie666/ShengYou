package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.xiaoshanghai.nancang.helper.RoomManager
import com.xiaoshanghai.nancang.net.bean.CreateRoomResult
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.tencent.liteav.trtcvoiceroom.R
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef.RoomParam
import com.tencent.liteav.trtcvoiceroom.ui.base.MemberEntity
import com.tencent.liteav.trtcvoiceroom.ui.list.TCConstants
import com.tencent.liteav.trtcvoiceroom.ui.widget.CommonBottomDialog
import com.tencent.liteav.trtcvoiceroom.ui.widget.SelectMemberView
import com.tencent.liteav.trtcvoiceroom.ui.widget.msg.MsgEntity
import com.tencent.trtc.TRTCCloudDef
import java.util.*


class VoiceLiveRoomAct : VoiceBaseRoomAct(), SelectMemberView.onSelectedCallback {

    private var mMemberEntityList: MutableList<MemberEntity>? = null

    private var mMemberEntityMap: MutableMap<String, MemberEntity>? = null

    val ERROR_ROOM_ID_EXIT = -1301

    // 用户申请上麦的map
    private var mTakeSeatInvitationMap: MutableMap<String, String>? = null

    // 邀请人上麦的map
    private var mPickSeatInvitationMap: MutableMap<String, SeatInvitation>? = null

    private var mIsEnterRoom = false

    /**
     * 创建房间
     */
    companion object StaticParams {


        fun createRoom(context: Context, roomName: String, userId: String, userName: String, userAvatar: String, coverUrl: String, audioQuality: Int, needRequest: Boolean) {
            val intent = Intent(context, VoiceLiveRoomAct::class.java)
            intent.putExtra(VOICEROOM_ROOM_NAME, roomName)
            intent.putExtra(VOICEROOM_USER_ID, userId)
            intent.putExtra(VOICEROOM_AUDIO_QUALITY, audioQuality)
            intent.putExtra(VOICEROOM_NEED_REQUEST, needRequest)

//            intent.putExtra(VOICEROOM_USER_NAME, userName)
//            intent.putExtra(VOICEROOM_USER_AVATAR, userAvatar)
//            intent.putExtra(VOICEROOM_ROOM_COVER, coverUrl)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAnchor()
    }

    override fun onBackPressed() {
        if (mIsEnterRoom) {
            showExitRoom()
        } else {
            finish()
        }
    }

    private fun showExitRoom() {
        if (mConfirmDialogFragment.isAdded) {
            mConfirmDialogFragment.dismiss()
        }
        mConfirmDialogFragment.setMessage("当前正在直播，是否退出直播？")
        mConfirmDialogFragment.setNegativeClickListener { mConfirmDialogFragment.dismiss() }
        mConfirmDialogFragment.setPositiveClickListener {
            mConfirmDialogFragment.dismiss()
            destroyRoom()
            finish()
        }
        mConfirmDialogFragment.show(fragmentManager, "confirm_fragment")
    }

    private fun destroyRoom() {

        mTRTCVoiceRoom.destroyRoom { code, msg ->
            if (code == 0) {
                Log.d(TAG, "IM销毁房间成功")
            } else {
                Log.d(TAG, "IM销毁房间失败:$msg")
            }
        }

        mTRTCVoiceRoom.setDelegate(null)

    }


    /**
     * 主播的逻辑
     */
    private fun initAnchor() {
        mMemberEntityList = ArrayList()
        mMemberEntityMap = HashMap()
        mTakeSeatInvitationMap = HashMap()
        mPickSeatInvitationMap = HashMap<String, SeatInvitation>()
        mVoiceRoomSeatAdapter!!.notifyDataSetChanged()
        mViewSelectMember.setList(mMemberEntityList)
        mViewSelectMember.setOnSelectedCallback(this)
        //刷新界面的按钮
//        mBtnAudio.isActivated = true


//        mBtnMsg.isActivated = true
//        mBtnMsg.isSelected = true
//        mBtnAudio.isSelected = true

//        mRoomId = getRoomId()

        mCurrentRole = TRTCCloudDef.TRTCRoleAnchor

        RoomManager.getInstance().createRoom(this, object : RoomManager.ActionCallback {

            override fun onSuccess(bean: CreateRoomResult?) {
                 mRoomId = bean!!.id
                internalCreateRoom(bean)
            }

            override fun onFailed(code: Int, msg: String?) {
                if (code == ERROR_ROOM_ID_EXIT) {
                    onSuccess(null)
                } else {
                    ToastUtils.showLong("创建房间失败[$code]:$msg")
                    finish()
                }
            }
        })
    }

    //创建房间初始化
    @SuppressLint("StringFormatMatches")
    private fun internalCreateRoom(bean: CreateRoomResult) {
        val roomParam = RoomParam()
        roomParam.roomName = bean.roomName
        roomParam.needRequest = mNeedRequest
        roomParam.seatCount = MAX_SEAT_SIZE
        roomParam.coverUrl = ""
        //        roomParam.coverUrl = ;
        mTRTCVoiceRoom.createRoom(mRoomId, roomParam) { code, msg ->
            if (code == 0) {
                mIsEnterRoom = true
                mTvTitle?.text = getString(R.string.trtcvoiceroom_main_title, roomParam.roomName, mRoomId)
                mTRTCVoiceRoom.setAudioQuality(mAudioQuality)
                takeMainSeat()
                getAudienceList()
            }
        }
    }

    /**
     * 房子创建房间后占位
     */
    private fun takeMainSeat() {
        // 开始创建房间
        mTRTCVoiceRoom.enterSeat(0) { code, msg ->
            if (code == 0) {
                //成功上座位，可以展示UI了
                ToastUtils.showLong("房主占座成功")
            } else {
                ToastUtils.showLong("主播占座失败[$code]:$msg")
            }
        }
    }

    /**
     * 获取当前房间非麦位成员
     */
    private fun getAudienceList() {
        mTRTCVoiceRoom.getUserInfoList(null) { code, msg, list ->
            if (code == 0) {
                for (userInfo in list) {
                    if (userInfo.userId == mSelfUserId) {
                        continue
                    }
                    val memberEntity = MemberEntity()
                    memberEntity.userId = userInfo.userId
                    memberEntity.userAvatar = userInfo.userAvatar
                    memberEntity.userName = userInfo.userName
                    memberEntity.type = MemberEntity.TYPE_IDEL
                    if (!mMemberEntityMap!!.containsKey(memberEntity.userId)) {
                        mMemberEntityMap!![memberEntity.userId] = memberEntity
                        mMemberEntityList?.add(memberEntity)
                    }
                }
            }
        }
    }


    /**
     * 主播点击座位列表
     *
     * @param itemPos
     */
    override fun onItemClick(itemPos: Int) {
        // TODO: 2020-06-10 这里可以统一加上loading
        // 判断座位有没有人

        // TODO: 2020-06-10 这里可以统一加上loading
        // 判断座位有没有人
        val entity = mVoiceRoomSeatEntityList!![itemPos]
        if (entity.isUsed) {
            // 有人弹出禁言/踢人
            val isMute = entity.isMute
            val dialog = CommonBottomDialog(this)
            dialog.setButton(CommonBottomDialog.OnButtonClickListener { position, text -> // 这里应该统一加上loading
                dialog.dismiss()
                if (position == 0) {
                    mTRTCVoiceRoom.muteSeat(changeSeatIndexToModelIndex(itemPos), !isMute, null)
                } else {
                    mTRTCVoiceRoom.kickSeat(changeSeatIndexToModelIndex(itemPos), null)
                }
            }, if (isMute) "对 Ta 解禁" else "对 Ta 禁言", "请 Ta 下麦")
            dialog.show()
        } else if (!entity.isClose) {
            // 没人弹出封麦
            val dialog = CommonBottomDialog(this)
            dialog.setButton(CommonBottomDialog.OnButtonClickListener { position, text ->
                dialog.dismiss()
                if (position == 0) {
                    if (mViewSelectMember != null) {
                        //设置一下邀请的座位号
                        mViewSelectMember.setSeatIndex(itemPos)
                        mViewSelectMember.show()
                    }
                } else {
                    mTRTCVoiceRoom.closeSeat(changeSeatIndexToModelIndex(itemPos), true, null)
                }
            }, "邀人上麦", "封禁麦位")
            dialog.show()
        } else {
            val dialog = CommonBottomDialog(this)
            dialog.setButton(CommonBottomDialog.OnButtonClickListener { position, text ->
                dialog.dismiss()
                if (position == 0) {
                    mTRTCVoiceRoom.closeSeat(changeSeatIndexToModelIndex(itemPos), false, null)
                }
            }, "解禁麦位")
            dialog.show()
        }
    }


    /**
     * 观众进入房间
     */
    override fun onAudienceEnter(userInfo: TRTCVoiceRoomDef.UserInfo?) {
        super.onAudienceEnter(userInfo)

        val memberEntity = MemberEntity()
        memberEntity.userId = userInfo?.userId
        memberEntity.userAvatar = userInfo?.userAvatar
        memberEntity.userName = userInfo?.userName
        memberEntity.type = MemberEntity.TYPE_IDEL
        if (!mMemberEntityMap!!.containsKey(memberEntity.userId)) {
            mMemberEntityMap!![memberEntity.userId] = memberEntity
            mMemberEntityList!!.add(memberEntity)
        }
        if (mViewSelectMember != null) {
            mViewSelectMember.notifyDataSetChanged()
        }
    }

    /**
     * 观众退出房间
     */
    override fun onAudienceExit(userInfo: TRTCVoiceRoomDef.UserInfo?) {
        super.onAudienceExit(userInfo)

        val entity = mMemberEntityMap!!.remove(userInfo!!.userId)
        if (entity != null) {
            mMemberEntityList!!.remove(entity)
        }
        if (mViewSelectMember != null) {
            mViewSelectMember.notifyDataSetChanged()
        }
    }

    /**
     * 有成员上麦(主动上麦/主播抱人上麦)
     * @param index 上麦的麦位
     * @param user  用户详细信息
     */
    override fun onAnchorEnterSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {
        super.onAnchorEnterSeat(index, user)
        val entity = mMemberEntityMap!![user!!.userId]
        if (entity != null) {
            entity.type = MemberEntity.TYPE_IN_SEAT
        }
        if (mViewSelectMember != null) {
            mViewSelectMember.notifyDataSetChanged()
        }
    }

    /**
     * 有成员下麦(主动下麦/主播踢人下麦)
     * @param index 下麦的麦位
     * @param user  用户详细信息
     */
    override fun onAnchorLeaveSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {
        super.onAnchorLeaveSeat(index, user)
        val entity = mMemberEntityMap!![user!!.userId]
        if (entity != null) {
            entity.type = MemberEntity.TYPE_IDEL
        }
        if (mViewSelectMember != null) {
            mViewSelectMember.notifyDataSetChanged()
        }
    }

    /**
     * 消息点击
     */
    override fun onAgreeClick(position: Int,userId:String?) {
        super.onAgreeClick(position,userId)

//        if (mMsgEntityList != null) {
//            val entity = mMsgEntityList!![position]
//            val inviteId = entity.invitedId
//            if (inviteId == null) {
//                ToastUtils.showLong("该请求已过期")
//                return
//            }
//            mTRTCVoiceRoom.acceptInvitation(inviteId) { code, msg ->
//                if (code == 0) {
//                    entity.type = MsgEntity.TYPE_AGREED
//                    mMsgListAdapter!!.notifyDataSetChanged()
//                } else {
//                    ToastUtils.showShort("接受请求失败:$code")
//                }
//            }
//        }

    }


    /**
     * 收到新的邀请请求
     *
     * @param id  邀请id
     * @param inviter 邀请人userId
     * @param cmd 业务指定的命令字
     * @param content 业务指定的内容
     */
    override fun onReceiveNewInvitation(id: String?, inviter: String?, cmd: String?, content: String?) {
        super.onReceiveNewInvitation(id, inviter, cmd, content)

        if (cmd == TCConstants.CMD_REQUEST_TAKE_SEAT) {
            recvTakeSeat(id!!, inviter!!, content!!)
        }
    }

    private fun recvTakeSeat(inviteId: String, inviter: String, content: String) {
        //收到了观众的申请上麦消息，显示到通知栏
        val memberEntity = mMemberEntityMap!![inviter]
        val msgEntity = MsgEntity()
        msgEntity.userId = inviter
        msgEntity.invitedId = inviteId
        msgEntity.userName = if (memberEntity != null) memberEntity.userName else inviter
        msgEntity.type = MsgEntity.TYPE_WAIT_AGREE
        val seatIndex = content.toInt()
        msgEntity.content = "申请上" + seatIndex + "号麦"
//        mMsgEntityList!!.add(msgEntity)
        if (memberEntity != null) {
            memberEntity.type = MemberEntity.TYPE_WAIT_AGREE
        }
        mTakeSeatInvitationMap!![inviter] = inviteId
        mViewSelectMember.notifyDataSetChanged()
        mMsgListAdapter!!.notifyDataSetChanged()
        mRvImMsg?.smoothScrollToPosition(mMsgListAdapter!!.itemCount)
    }

    /**
     * mViewSelectMember 的回调函数
     * 主播选择了观众进行邀请操作
     *
     * @param seatIndex
     * @param memberEntity
     */
    override fun onSelected(seatIndex: Int, memberEntity: MemberEntity) {
        // 座位号 seat index 上 选择了某个用户进行邀请
        val seatEntity = mVoiceRoomSeatEntityList!![seatIndex]
        if (seatEntity.isUsed) {
            ToastUtils.showLong("这个麦位已经有人了")
            return
        }
        if (memberEntity.type == MemberEntity.TYPE_WAIT_AGREE) {
            //这个用户已经发过申请了，那么进行同意操作，取最后一次收到消息的情况
            val inviteId = mTakeSeatInvitationMap!![memberEntity.userId]
            if (inviteId == null) {
                ToastUtils.showLong("该请求已过期")
                memberEntity.type = MemberEntity.TYPE_IDEL
                mViewSelectMember.notifyDataSetChanged()
                return
            }
            mTRTCVoiceRoom.acceptInvitation(inviteId) { code, msg ->
                if (code == 0) {
//                    for (msgEntity in mMsgEntityList!!) {
//                        if (msgEntity.userId != null && msgEntity.userId == memberEntity.userId) {
//                            msgEntity.type = MsgEntity.TYPE_AGREED
//                            break
//                        }
//                    }
//                    mMsgListAdapter!!.notifyDataSetChanged()
                } else {
                    ToastUtils.showShort("接受请求失败:$code")
                    memberEntity.type = MemberEntity.TYPE_IDEL
                    mViewSelectMember.notifyDataSetChanged()
                }
            }
            // 这里也清空一下msg list里面对应的观众信息
            for (msgEntity in mMsgEntityList!!) {
//                if (msgEntity.userId == null) {
//                    continue
//                }
//                if (msgEntity.userId == memberEntity.userId) {
//                    msgEntity.type = MsgEntity.TYPE_AGREED
//                    mTakeSeatInvitationMap?.remove(msgEntity.invitedId)
//                }
            }
            mMsgListAdapter!!.notifyDataSetChanged()
            return
        }
        val seatInvitation = SeatInvitation()
        seatInvitation.inviteUserId = memberEntity.userId
        seatInvitation.seatIndex = seatIndex
        val inviteId = mTRTCVoiceRoom.sendInvitation(TCConstants.CMD_PICK_UP_SEAT, seatInvitation.inviteUserId, changeSeatIndexToModelIndex(seatIndex).toString()) { code, msg ->
            if (code == 0) {
                ToastUtils.showLong("发送邀请成功！")
            }
        }
        mPickSeatInvitationMap!![inviteId] = seatInvitation

        mViewSelectMember.dismiss()
    }

    /**
     * 观众点击拒绝邀请
     *
     * @param id
     * @param invitee
     */
    override fun onInviteeRejected(id: String?, invitee: String?) {
        super.onInviteeRejected(id, invitee)
        val seatInvitation: SeatInvitation? = mPickSeatInvitationMap!!.remove(id!!)
        if (seatInvitation != null) {
            val entity = mMemberEntityMap!![seatInvitation.inviteUserId]
            if (entity != null) {
                ToastUtils.showShort(entity.userName + " 拒绝上麦")
            }
        }
    }

    /**
     * @param id
     * @param invitee
     */
    override fun onInviteeAccepted(id: String?, invitee: String?) {
        super.onInviteeAccepted(id, invitee)
        // 抱麦的用户同意了，先获取一下之前的消息
        val seatInvitation: SeatInvitation? = mPickSeatInvitationMap!![id]
        if (seatInvitation != null) {
            val entity = mVoiceRoomSeatEntityList!![seatInvitation.seatIndex]
            if (entity.isUsed) {
                Log.e(TAG, "seat " + seatInvitation.seatIndex + " already used")
                return
            }
            mTRTCVoiceRoom.pickSeat(changeSeatIndexToModelIndex(seatInvitation.seatIndex), seatInvitation.inviteUserId) { code, msg ->
                if (code == 0) {
                    ToastUtils.showLong("抱" + invitee + "上麦成功啦！")
                }
            }
        } else {
            Log.e(TAG, "onInviteeAccepted: $id user:$invitee not this people")
        }
    }

    override fun onCancel() {

    }


    private class SeatInvitation {
        var seatIndex = 0
        var inviteUserId: String? = null
    }

}
