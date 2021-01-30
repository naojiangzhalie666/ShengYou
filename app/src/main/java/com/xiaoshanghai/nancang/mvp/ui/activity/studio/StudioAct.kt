package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.google.gson.Gson
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.FollowCallback
import com.xiaoshanghai.nancang.callback.RoomUserCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.Constant.isAnchorColse
import com.xiaoshanghai.nancang.constant.CustomMsgConstant
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.helper.EnterRoomAnimManager
import com.xiaoshanghai.nancang.helper.NewRoomAnimatorManager
import com.xiaoshanghai.nancang.helper.RoomAnimatorManager
import com.xiaoshanghai.nancang.helper.RoomManager
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.NobleWebActivity
import com.xiaoshanghai.nancang.mvp.ui.view.EnterRoomView
import com.xiaoshanghai.nancang.mvp.ui.view.ManagerSeatView
import com.xiaoshanghai.nancang.mvp.ui.view.NobleDialog
import com.xiaoshanghai.nancang.mvp.ui.view.RoomSeatView
import com.xiaoshanghai.nancang.net.bean.*
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.TipsDialog
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomCallback
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef.*
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity
import com.tencent.trtc.TRTCCloudDef

open class StudioAct : VoiceBaseRoomAct() {

    private var mInvitationSeatMap: MutableMap<String, Int>? = null


    private var mSelfSeatIndex = 0


    companion object {
        fun enterRoom(context: Context, roomId: String, userId: String, audioQuality: Int, roomName: String, isAgain: Boolean?) {
            val starter = Intent(context, StudioAct::class.java)
            starter.putExtra(VOICEROOM_ROOM_ID, roomId)
            starter.putExtra(VOICEROOM_USER_ID, userId)
            starter.putExtra(VOICEROOM_AUDIO_QUALITY, audioQuality)
            starter.putExtra(VOICEROOM_CREATE, false)
            starter.putExtra(VOICEROOM_ROOM_NAME, roomName)
            starter.putExtra(VOICEROOM_ISAGAIN, isAgain)
            context.startActivity(starter)
        }

        fun createRoom(context: Context, userId: String, audioQuality: Int, isAgain: Boolean?) {
            val starter = Intent(context, StudioAct::class.java)
            starter.putExtra(VOICEROOM_USER_ID, userId)
            starter.putExtra(VOICEROOM_AUDIO_QUALITY, audioQuality)
            starter.putExtra(VOICEROOM_CREATE, true)
            starter.putExtra(VOICEROOM_ISAGAIN, isAgain)
            context.startActivity(starter)
        }


    }

//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//
//        var roomId = intent?.getStringExtra(VOICEROOM_ROOM_ID)
//
//        if (roomId != mRoomId) {
//
//
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAudience();

    }

    /**
     * 获取当前用户信息成功
     */
    override fun userOnSuccess(userInfo: VoiceRoomSeatEntity.UserInfo?) {
        super.userOnSuccess(userInfo)

        mSelfUserInfo = userInfo

    }

    private fun initAudience() {
        mInvitationSeatMap = HashMap()
        mVoiceRoomSeatAdapter!!.notifyDataSetChanged()

        if (isCreate) {
            createRoom()
        } else {
            // 开始进房哦
            enterRoom()
            mPresenter.getRoomUser(mSelfUserId!!, mRoomId!!)
        }

    }


    override fun onBackPressed() {

//        finish()

        mTRTCVoiceRoom.exitRoom { code, msg ->
//            ToastUtils.showShort("退房成功")
            exitRoom()
            super.onBackPressed()
        }

    }

    private fun exitRoom() {
        mPresenter.exitRoom(mRoomId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 进房
     */
    private fun enterRoom() {
        mIsSeatInitSuccess = false
        mSelfSeatIndex = -1
        mCurrentRole = TRTCCloudDef.TRTCRoleAudience

        RoomManager.getInstance().enterRoom(this, mRoomId!!, object : RoomManager.ActionCallback {
            override fun onSuccess(bean: CreateRoomResult?) {

                setRoomConfig(bean)

//                bean!!.mikeSets


                mTRTCVoiceRoom.enterRoom(bean?.roomNumber) { code, msg ->

                    if (code == 0) {
//                    进房成功
//                        ToastUtils.showShort("进房成功")
                        mTRTCVoiceRoom.setAudioQuality(mAudioQuality)
                    } else {
                        ToastUtils.showShort("进房失败[$code]:$msg")
                        finish()
                    }

                }
            }

            override fun onFailed(code: Int, msg: String?) {
                ToastUtils.showShort("进房失败[$code]:$msg")
                finish()
            }

        })
    }

    private fun createRoom() {

        mIsSeatInitSuccess = false
        mSelfSeatIndex = -1
        mCurrentRole = TRTCCloudDef.TRTCRoleAudience

        RoomManager.getInstance().createRoom(this, object : RoomManager.ActionCallback {
            override fun onSuccess(bean: CreateRoomResult?) {

                setRoomConfig(bean)
                mRoomId = bean?.id
                mPresenter.getRoomUser(mSelfUserId!!, mRoomId!!)

                val roomParam = RoomParam()
                if (bean?.roomName?.length!! > 8) {

                   var roomNameStr =  bean.roomName.substring(0,8)

                    roomParam.roomName = roomNameStr
                } else {
                    roomParam.roomName = bean.roomName
                }

                roomParam.needRequest = mNeedRequest
                roomParam.seatCount = MAX_SEAT_SIZE
                roomParam.coverUrl = mSelfUserInfo?.userPicture

                mTRTCVoiceRoom.createRoom(bean.roomNumber, roomParam) { code, msg ->
                    if (code == 0) {
                        //进房成功
                        ToastUtils.showShort("进房成功")
                        mTRTCVoiceRoom.setAudioQuality(mAudioQuality)
                    } else {
                        ToastUtils.showShort("进房失败[$code]:$msg")
                        finish()
                    }

                }

            }

            override fun onFailed(code: Int, msg: String?) {
                ToastUtils.showShort("进房失败[$code]:$msg")
                finish()
            }

        })
    }


    /**
     * 设置房间信息
     */
    fun setRoomConfig(bean: CreateRoomResult?) {

        mRoomInfo = bean

        //设置当前用户在该房间身份
        mUserKind = mRoomInfo?.userKind!!


        //设置房间锁状态
        mRoomLock = mRoomInfo?.roomLock!!

        setRoomLockIcon(mRoomLock)

        //设置礼物值开关
        giftValue = mRoomInfo?.giftValue!!
        setIsOpenGiftValue(giftValue)


        mikesSeat.clear()
        mikesSeat = initMikes(bean!!.mikes)
        mOwnerId = bean.roomOwnerId
        mRoomId = bean.id

//        mRoomSpecial = bean?.roomSpecial

        mRoomPublic = bean?.roomPublic

        isOpenGp(mRoomPublic == 0, mRoomPublic == 1)

        if (bean.roomExclusion == 0) {
            mNeedRequest = false
            mIvRowSeat?.visibility = View.GONE
        } else if (bean?.roomExclusion == 1) {
            mNeedRequest = true
            mIvRowSeat?.visibility = View.VISIBLE
        }

        if (bean.isBeautiful == 1) {
            mIvLh?.visibility = View.VISIBLE
        } else {
            mIvLh?.visibility = View.GONE
        }

        mTvTitle?.isSelected = true
        mTvTitle?.text = bean.roomName
        mTvRoomId?.text = bean.roomOwnerNumber.toString()
        mTvOnLineNum?.text = bean.personCount.toString()

        if (mContext != null) {

            GlideAppUtil.loadImage(mContext, bean.roomBgPicture, mIvRoomBg)
        }


        if (!TextUtils.isEmpty(bean.typeName)) {
            if (!TextUtils.isEmpty(bean.roomTypeColor)) {
                mTvRoomType?.setTextColor(Color.parseColor(bean.roomTypeColor))
            }
            mTvRoomType?.text = bean.typeName
        }

        if (bean.userKind == 1 || bean.userKind == 2) {

            mIvEdit?.visibility = View.VISIBLE

        } else {
            mIvEdit?.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(bean.roomNoticeTitle)) {
            mTvBulletin?.text = bean.roomNoticeTitle
        }


    }

    override fun onClickNotiy() {
        super.onClickNotiy()
        if (mSelfUserInfo?.userKind == 1 || mSelfUserInfo?.userKind == 2) {

            var bundle = Bundle()
            bundle.putString(Constant.ROOM_TITLE, mRoomInfo?.roomNoticeTitle)
            bundle.putString(Constant.ROOM_COMMENT, mRoomInfo?.roomNoticeComment)
            bundle.putString(Constant.ROOM_ID, mRoomId)

            ActStartUtils.startAct(this, RoomNotiyAct::class.java, bundle)

        } else {

            TipsDialog.createDialog(this, R.layout.dialog_room_notiy)
                    .setText(R.id.tv_notiy_title, if (!TextUtils.isEmpty(mRoomInfo?.roomNoticeTitle)) mRoomInfo?.roomNoticeTitle else "房间公告")
                    .setText(R.id.tv_notiy_content, if (!TextUtils.isEmpty(mRoomInfo?.roomNoticeComment)) mRoomInfo?.roomNoticeComment else "这个房主很懒~")
                    .bindClick(R.id.tv_close) { v, dialog -> dialog.dismiss() }
                    .show()

        }
    }


    override fun onError(msg: String?) {
        super.onError(msg)

    }

    /**
     * 发送消息
     */
    override fun onTextSend(msg: String) {
        super.onTextSend(msg)
        mPresenter.sendTextMsg(msg, mSelfUserInfo, mTRTCVoiceRoom, TRTCVoiceRoomCallback.ActionCallback { code, _ ->
            if (code == 0) {
                mSelfUserInfo?.message = ""
                var msgBody = MsgBody()
                msgBody.type = MsgBody.MSG_TALK
                msgBody.message = msg
                msgBody.userInfo = mSelfUserInfo
                showNotifyMsg(msgBody)
            }
        })
    }

    /**
     * 初始化麦位信息，已键值对形式保存以便后面通过id取出麦位上的人做信息完善
     */
    fun initMikes(mikes: MutableList<MikesBean>): MutableMap<String, MikesBean> {
        var mikesMap: MutableMap<String, MikesBean> = HashMap()

        for (mike in mikes) {
            mikesMap[mike.userId] = mike
        }
        return mikesMap
    }

    /**
     * 全量的麦位列表变化,包含了整个麦位表
     * @param seatInfoList 全量的麦位列表
     */
    override fun onSeatListChange(seatInfoList: MutableList<SeatInfo>?) {
        super.onSeatListChange(seatInfoList!!)

        //循环座位 看看自己在不在座位上
        for (i in seatInfoList.indices) {
            //如果在
            if (seatInfoList[i].userId == mSelfUserId) {
                //身份改变为主播
                mCurrentRole = TRTCCloudDef.TRTCRoleAnchor
                mSelfSeatIndex = i
                isAnchor(true)
                isAnchorColse = seatInfoList[i].mute
//                isMaikStatus = seatInfoList[i].mute
                mikeSwitch()
                break
            }
        }


    }


    /**
     * 主麦位点击回调
     */
    override fun mainSeatOnClick() {
        super.mainSeatOnClick()


        if (mMainSeatUserId?.isUsed!!) {

            mPresenter.getUser(mMainSeatUserId?.userId, mRoomId, object : RoomUserCallback {
                override fun onRoomUser(userInfo: VoiceRoomSeatEntity.UserInfo?) {

                    mPresenter.queryFollow(mMainSeatUserId?.userId!!, object : FollowCallback {
                        override fun onFollow(status: Int) {

                            if (!isSeatWindows) {

                                var isFollow = status == 1
                                val userSeatInfo = getUserSeatInfo(mMainSeatUserId?.userId!!)

                                val roomSeatDialog = RoomSeatView.newInstance(userInfo, mSelfUserInfo?.userKind!!, userSeatInfo, isFollow)
                                roomSeatDialog.setOnSeatClickCallback(this@StudioAct)
                                roomSeatDialog.show(supportFragmentManager, TAG)
                                isSeatWindows = true
                            }
                        }
                    })
                }
            })
        } else {
            if (mSelfUserInfo?.userKind == 1 || mSelfUserInfo?.userKind == 2) {

                if (!isManagerWindows) {
                    isManagerWindows = true
//                    val voiceRoomSeatEntity = mVoiceRoomSeatEntityList!![itemPos]
                    val roomSeat = RoomSeat(false, 0, mMainSeatUserId?.isUsed, mMainSeatUserId?.isClose, mMainSeatUserId?.isMute)

                    val managerView = ManagerSeatView.newInstance(roomSeat)
                    managerView.setOnSeatClickCallback(this)
                    managerView.show(supportFragmentManager, TAG)


                }


            } else if (mSelfUserInfo?.userKind == 3) {
                if (!mMainSeatUserId?.isClose!!) {
                    startTakeSeat(0)
                }
            }
        }
    }

    /**
     * 点击麦位列表观众端的操作
     *
     * @param itemPos
     */
    override fun onItemClick(itemPos: Int) {
        super.onItemClick(itemPos)

        val seatEntity = mVoiceRoomSeatEntityList?.get(itemPos)

        if (seatEntity?.isUsed!!) {

            mPresenter.getUser(seatEntity?.userId, mRoomId, object : RoomUserCallback {
                override fun onRoomUser(userInfo: VoiceRoomSeatEntity.UserInfo?) {

                    mPresenter.queryFollow(seatEntity.userId, object : FollowCallback {
                        override fun onFollow(status: Int) {

                            if (!isSeatWindows) {

                                var isFollow = status == 1
                                val userSeatInfo = getUserSeatInfo(seatEntity?.userId!!)

                                val roomSeatDialog = RoomSeatView.newInstance(userInfo, mSelfUserInfo?.userKind!!, userSeatInfo, isFollow)
                                roomSeatDialog.setOnSeatClickCallback(this@StudioAct)
                                roomSeatDialog.show(supportFragmentManager, TAG)
                                isSeatWindows = true
                            }
                        }
                    })
                }
            })
        } else {
            if (mSelfUserInfo?.userKind == 1 || mSelfUserInfo?.userKind == 2) {

                if (!isManagerWindows) {
                    isManagerWindows = true
                    val voiceRoomSeatEntity = mVoiceRoomSeatEntityList!![itemPos]
                    val roomSeat = RoomSeat(false, itemPos + 1, voiceRoomSeatEntity.isUsed, voiceRoomSeatEntity.isClose, voiceRoomSeatEntity.isMute)

                    val managerView = ManagerSeatView.newInstance(roomSeat)
                    managerView.setOnSeatClickCallback(this)
                    managerView.show(supportFragmentManager, TAG)


                }


            } else if (mSelfUserInfo?.userKind == 3) {
                if (!seatEntity.isClose) {
                    startTakeSeat(itemPos + 1)
                }
            }
        }

    }


    /**
     * 下麦
     */
    private fun leaveSeat() {

        mPresenter.downSeat()
    }


    /**
     * 抱上麦
     */
    private fun recvPickSeat(id: String, cmd: String, content: String) {
        //这里收到了主播抱麦的邀请
        if (mConfirmDialogFragment != null && mConfirmDialogFragment.isAdded) {
            mConfirmDialogFragment.dismiss()
        }
//        mConfirmDialogFragment = ConfirmDialogFragment()
        val seatIndex = content.toInt()
        mConfirmDialogFragment.setMessage("主播邀请你上" + seatIndex + "号麦")
        mConfirmDialogFragment.setNegativeClickListener {
            mTRTCVoiceRoom.rejectInvitation(id) { code, msg ->
                Log.d(TAG, "rejectInvitation callback:$code")
                ToastUtils.showShort("你拒绝上麦申请")
            }
            mConfirmDialogFragment.dismiss()
        }
        mConfirmDialogFragment.setPositiveClickListener { //同意上麦，回复接受
            mTRTCVoiceRoom.acceptInvitation(id) { code, msg ->
                if (code != 0) {
                    ToastUtils.showShort("接受请求失败:$code")
                }
                Log.d(TAG, "acceptInvitation callback:$code")
            }
            mConfirmDialogFragment.dismiss()
        }
        mConfirmDialogFragment.show(fragmentManager, "confirm_fragment$seatIndex")
    }


    /**
     * 观众进入房间
     *
     * @param userInfo 观众的详细信息
     */
    override fun onAudienceEnter(userInfo: TRTCVoiceRoomDef.UserInfo?) {
        super.onAudienceEnter(userInfo)
        if (userInfo?.userId == mSelfUserId) {

            if (!isAgain) {

                mPresenter.sendInRoomMsg(mSelfUserInfo, mTRTCVoiceRoom, TRTCVoiceRoomCallback.ActionCallback { code, msg ->
                    if (code == 0) {
                        var msgBody = MsgBody()
                        msgBody.type = MsgBody.MSG_IN_ROOM
                        msgBody.userInfo = mSelfUserInfo
                        showNotifyMsg(msgBody)
                    }
                })

                if (!TextUtils.isEmpty(mSelfUserInfo?.noble) && mSelfUserInfo?.noble?.toInt()!! > 0 && mSelfUserInfo?.isInvisible == 0) {

                    val instance = EnterRoomAnimManager.getInstance()
                    var enterRoomView = EnterRoomView(this)
                    enterRoomView.setUserEnterRoomBg(mSelfUserInfo)
                    enterRoomView.id = View.generateViewId()
                    instance.setInfo(this, mCostraint!!)
                    instance.addAnimation(enterRoomView)
                }

                if (!TextUtils.isEmpty(mSelfUserInfo?.car) && (mSelfUserInfo?.noble?.toInt()!! < 6 || (mSelfUserInfo?.noble?.toInt()!! >= 6 && mSelfUserInfo?.isInvisible == 0))) {
                    addAnimation(mSelfUserInfo?.car!!)
                }
            }
        } else {
            if (userInfo?.userId != mRoomInfo?.roomOwnerId) {
                var count = mRoomInfo?.personCount
                mRoomInfo?.personCount = count!! + 1
                setRoomConfig(mRoomInfo)
            }
        }
    }

    override fun onAudienceExit(userInfo: UserInfo?) {
        super.onAudienceExit(userInfo)
        if (userInfo?.userId != mSelfUserId) {
            var count = mRoomInfo?.personCount
            if (count!! > 0) {
                mRoomInfo?.personCount = count - 1
                setRoomConfig(mRoomInfo)
            }
        }
    }

    /**
     * 房间信息改变的通知
     */
    override fun onRoomInfoChange(roomInfo: RoomInfo?) {
        super.onRoomInfoChange(roomInfo)
//        mOwnerId = roomInfo?.ownerId
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
//        if (cmd == TCConstants.CMD_PICK_UP_SEAT) {
//            recvPickSeat(id!!, cmd, content!!)
//        }
    }

    /**
     * 被邀请者接受邀请
     *
     * @param id  邀请id
     * @param invitee 被邀请人userId
     */
    override fun onInviteeAccepted(id: String?, invitee: String?) {
        super.onInviteeAccepted(id, invitee)
        val seatIndex = mInvitationSeatMap!!.remove(id!!)
        if (seatIndex != null) {
            val entity = mVoiceRoomSeatEntityList!![seatIndex]
            if (!entity.isUsed) {
                mTRTCVoiceRoom.enterSeat(changeSeatIndexToModelIndex(seatIndex)) { code, msg ->
                    if (code == 0) {

                    }
                }
            }
        }
    }

    /**
     * 主播禁麦
     * @param index  操作的麦位
     * @param isMute 是否静音
     */
    override fun onSeatMute(index: Int, isMute: Boolean) {
        super.onSeatMute(index, isMute)

    }

    /**
     * 有成员上麦(主动上麦/主播抱人上麦)
     * @param index 上麦的麦位
     * @param user  用户详细信息
     */
    override fun onAnchorEnterSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {
        super.onAnchorEnterSeat(index, user)
        if (user?.userId == mSelfUserId) {
            mSelfUserInfo?.giftCoinCount = 0
            mCurrentRole = TRTCCloudDef.TRTCRoleAnchor
            mSelfSeatIndex = index
            isAnchor(true)
            mPresenter.takeSeat(mRoomId!!, index.toString())


            if (index == 0) {
                mMainSeatUserId?.userInfo = mSelfUserInfo
                mMainMike?.setSeatResult(mMainSeatUserId)
            } else {
                val seatInfo = mVoiceRoomSeatEntityList?.get(index - 1)
                seatInfo?.userInfo = mSelfUserInfo
                setRoomGiftValue()
                mVoiceRoomSeatAdapter?.notifyDataSetChanged()
            }

            mSelfUserInfo?.seatIndex = index
            val toJson = Gson().toJson(mSelfUserInfo)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdOnseat, toJson, null)

            mPresenter.clearGiftValue(mRoomId, mSelfUserId)
            isUpSeat = false
        } else {
            if (index == 0) {

                mMainSeatUserId?.userInfo?.giftCoinCount = 0
                mMainMike?.setSeatResult(mMainSeatUserId)

            } else {
                val seatInfo = mVoiceRoomSeatEntityList?.get(index - 1)
                seatInfo?.userInfo?.giftCoinCount = 0
                mVoiceRoomSeatAdapter?.notifyDataSetChanged()
                setRoomGiftValue()
            }
        }
    }


    /**
     * 有成员下麦(主动下麦/主播踢人下麦)
     * @param index 下麦的麦位
     * @param user  用户详细信息
     */
    override fun onAnchorLeaveSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {
        super.onAnchorLeaveSeat(index, user)
        if (user?.userId == mSelfUserId) {
            mCurrentRole = TRTCCloudDef.TRTCRoleAudience
            mSelfSeatIndex = -1
            leaveSeat()
            isAnchor(false)

            mPresenter.clearGiftValue(mRoomId, mSelfUserId)

        }
    }

    override fun onRoomDestroy(roomId: String?) {
        super.onRoomDestroy(roomId)
        ToastUtils.showLong("房间被强制关闭")
        mPresenter.exitRoom(mRoomId)
        mTRTCVoiceRoom.exitRoom(null)
        EventBusUtil.sendEvent(Event(EventConstant.ROOM_DESTROY,null))
        finish()
    }

    /**
     * 上座成功（这里废除了）
     */
    override fun takeSeatSuccess(status: Int?, position: Int?) {
        super.takeSeatSuccess(status, position)
        if (status == 1) {

        }
    }


    /**
     * 点击表情回调
     */
    override fun onFaceClickResult(face: RoomFace?, isNobleFace: Boolean?, noble: String?) {
        super.onFaceClickResult(face, isNobleFace, noble)

        val selfNoble = mSelfUserInfo?.noble?.toInt()
        val faceNoble = noble?.toInt()
        if (selfNoble!! >= faceNoble!!) {


            val faceMsg = FaceMsg(mSelfSeatIndex, face?.filter)

            val toJson = Gson().toJson(faceMsg)

            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdEmoji, toJson) { code, msg ->
                if (code == 0) {
                    if (mSelfSeatIndex == 0) {

                        mMainMike?.setSvga(face?.filter!!, svgaParser)
                    } else {
                        setSvga(mFaceView?.get(mSelfSeatIndex - 1), face?.filter!!)
                    }
                }
            }
        } else {
            val mNobleDialog = NobleDialog.newInstance(selfNoble = selfNoble.toString(), giftNoble = noble)
            mNobleDialog.setOnClick(View.OnClickListener {
                ActStartUtils.startAct(this, NobleWebActivity::class.java)
            })

            mNobleDialog.show(supportFragmentManager, TAG)
        }

        mRoomFaceView.dismiss()
    }


    /**
     * 礼物赠送的回调
     */
    override fun onSendGiftResult(status: Int?, users: MutableList<GiftViewSeat>?, giftResult: RoomGiftResult?, giftNum: Int?, isTotal: Boolean?) {
        super.onSendGiftResult(status, users, giftResult, giftNum, isTotal)
        if (status == 0) {
            ToastUtils.showShort("余额不足！")
        } else if (status == 1) {

            var userIds = StringBuffer()


            if (users?.size!! > 0) {
                for (i in users.indices) {

                    val giftValue = getGiftValue(giftResult?.giftPriceType!!, giftResult?.giftPrice, giftNum!!)

                    if (mMainSeatUserId?.userId == users[i].userId) {

                        val giftCoinCount = mMainSeatUserId?.userInfo?.giftCoinCount
                        mMainSeatUserId?.userInfo?.giftCoinCount = giftValue + giftCoinCount!!
                        mMainMike?.setSeatResult(mMainSeatUserId)

                    }

                    for (j in mVoiceRoomSeatEntityList!!.indices) {
                        var seatEntity = mVoiceRoomSeatEntityList?.get(j)
                        if (seatEntity?.userId == users[i].userId) {
                            val giftCoinCount = seatEntity?.userInfo?.giftCoinCount
                            seatEntity?.userInfo?.giftCoinCount = giftValue + giftCoinCount!!

                        }
                    }

                    setRoomGiftValue()


//                    for (seatEntity in mVoiceRoomSeatEntityList!!) {
//
//                        if (seatEntity.userId == users[i].userId) {
//                            val giftCoinCount = seatEntity.userInfo.giftCoinCount
//                            seatEntity.userInfo.giftCoinCount = giftValue + giftCoinCount!!
//                        }
//
//                    }

                    if (i == users.size - 1) {
                        userIds?.append(users[i].userId)
                    } else {
                        userIds?.append(users[i].userId + ",")
                    }
                }

//                mVoiceRoomSeatAdapter?.notifyDataSetChanged()

            }

            //全麦模式就发一条消息
            if (isTotal!!) {
                var sendGift: SendGift? = SendGift(mSelfUserInfo?.id, mSelfUserInfo?.userName,
                        mSelfUserInfo?.charmLevel, mSelfUserInfo?.noble, "", "全麦",
                        "0", giftResult?.giftStaticUrl, giftResult?.giftUrl, giftNum, 2,
                        userIds.toString(), giftResult?.giftPriceType, giftResult?.giftPrice, giftResult?.giftShowType)
                val toJson = Gson().toJson(sendGift)
                mTRTCVoiceRoom?.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdGift, toJson, null)
                var msgBody = MsgBody()
                msgBody.type = MsgBody.MSG_SEND_GIFT
                msgBody.sendGift = sendGift
                showNotifyMsg(msgBody)

            } else {

                for (giftViewSeat in users!!) {
                    var sendGift: SendGift? = SendGift(mSelfUserInfo?.id, mSelfUserInfo?.userName,
                            mSelfUserInfo?.charmLevel, mSelfUserInfo?.noble, "", giftViewSeat.name,
                            giftViewSeat.noble, giftResult?.giftStaticUrl, giftResult?.giftUrl, giftNum, 2,
                            userIds.toString(), giftResult?.giftPriceType, giftResult?.giftPrice, giftResult?.giftShowType)
                    val toJson = Gson().toJson(sendGift)
                    mTRTCVoiceRoom?.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdGift, toJson, null)

                    var msgBody = MsgBody()
                    msgBody.type = MsgBody.MSG_SEND_GIFT
                    msgBody.sendGift = sendGift
                    showNotifyMsg(msgBody)
                }

            }

            if (giftResult?.giftShowType == 1) {
                val animationView = getAnimationView(userIds.toString(), giftResult.giftUrl)
                startAnimation(animationView)
            } else if (giftResult?.giftShowType == 2) {
                mSvgaManager?.addAnimation(giftResult?.giftUrl)
//                mVoiceRoomSeatAdapter?.notifyDataSetChanged()
            }


            if (giftResult?.giftPriceType == 2) {
                if ((giftResult.giftPrice) * giftNum!! >= Constant.PUBLICITY_PRICE_3) {
                    for (giftViewSeat in users) {

                        val globalMsgResult = GlobalMsgResult(giftResult.giftPrice.toString(), mRoomId, mSelfUserInfo?.userPicture,
                                giftResult.giftStaticUrl, giftViewSeat.name, giftNum.toString(),
                                mSelfUserInfo?.userName, giftViewSeat.avatar, giftResult?.giftName, 2,null)
                        val globalGson = Gson().toJson(globalMsgResult)
                        mTRTCVoiceRoom.sendGlobalCustomMsg(CustomMsgConstant.CustomMsgCmdGlobalMesssage, globalGson) { code, msg ->
                            var createNotice = createNotice(globalMsgResult.type, globalMsgResult)
                            if (createNotice != null) {
                                if (globalMsgResult.type == 1){
                                    var newManager = NewRoomAnimatorManager.getInstance()
                                    newManager.setInfo(this, mCostraint)
                                    newManager.addAnimation(createNotice)
                                } else {
                                    var manager = RoomAnimatorManager.getInstance()
                                    manager.setInfo(this, mCostraint)
                                    manager.addAnimation(createNotice)
                                }
                            }
                        }

                    }
                }
            }

        } else if (status == 2) {

            val mNobleDialog = NobleDialog.newInstance(mSelfUserInfo?.noble, giftResult?.nobleId)
            mNobleDialog.setOnClick(View.OnClickListener {
                ActStartUtils.startAct(this, NobleWebActivity::class.java)
            })

            mNobleDialog.show(supportFragmentManager, TAG)
        } else {
            ToastUtils.showShort("礼物赠送失败")
        }

    }


    /**
     * 接收到eventbus 消息处理
     */
    override fun receiveEvent(event: Event<*>?) {

        if (event?.code == EventConstant.ROOM_NOTIY) {
            val roomNotiyEntity = event.data as RoomNotiyEntity
            mRoomInfo?.roomNoticeTitle = roomNotiyEntity.roomNotiyTitle
            mRoomInfo?.roomNoticeComment = roomNotiyEntity.roomNotiyComment

            var mRoomSettingEntity = RoomSettingEntity(null, roomNotiyEntity.roomNotiyTitle, roomNotiyEntity.roomNotiyComment,
                    null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null, null)

            sendSettingRoom(mRoomSettingEntity, TRTCVoiceRoomCallback.ActionCallback { code, msg ->
                if (code == 0) {

                    setRoomConfig(mRoomInfo)
                }
            })

        }

        if (event?.code == EventConstant.ROOM_NAME) {
            var roomName: String = event.data as String


            var mRoomSettingEntity = RoomSettingEntity(roomName, null, null,
                    null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null, null)


            val roomSetting = Gson().toJson(mRoomSettingEntity)

            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdNameChange, roomSetting) { code, msg ->
                if (code == 0) {
                    mRoomInfo?.roomName = roomName
                    setRoomConfig(mRoomInfo)
                }
            }
        }

        if (event?.code == EventConstant.ROOM_PSD) {
            var mRoomPsd = event.data as RoomPsd
            var mRoomSettingEntity = RoomSettingEntity(null, null, null,
                    null, null, null, null, mRoomPsd?.roomLock, mRoomPsd.roomPsd, null, null,
                    null, null, null, null, null, null, null, null, null, null, null)

            val roomSetting = Gson().toJson(mRoomSettingEntity)

            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdPwdChange, roomSetting) { code, msg ->
                if (code == 0) {
                    mRoomInfo?.roomLock = if (mRoomPsd.roomLock) 1 else 0
                    mRoomInfo?.roomPassword = mRoomPsd.roomPsd
                    setRoomConfig(mRoomInfo)
                }
            }
        }

        if (event?.code == EventConstant.ROOM_TYPE) {
            val roomTypeEntity = event.data as RoomTypeEntity
            var mRoomSettingEntity = RoomSettingEntity(null, null,
                    null, roomTypeEntity.roomTypeName, roomTypeEntity.roomTypeId, null,
                    null, null, null, null,
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, roomTypeEntity.roomTypeColor)

            val roomSetting = Gson().toJson(mRoomSettingEntity)

            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdTypeChange, roomSetting) { code, msg ->
                if (code == 0) {

                    mRoomInfo?.roomTypeColor = mRoomSettingEntity?.roomTypeColor
                    mRoomInfo?.typeName = mRoomSettingEntity?.typeName
                    mRoomInfo?.roomTypeId = mRoomSettingEntity?.roomTypeId

                    setRoomConfig(mRoomInfo)
                }

            }
        }

        if (event?.code == EventConstant.ROOM_REMOVE_MANAGER) {
            val userId: String = event.data as String
            var mRoomMagager = RoomManagerMsgEntity(3, userId)
            val mRoomManagerMsg = Gson().toJson(mRoomMagager)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdSetManager, mRoomManagerMsg, null)
        }

        if (event?.code == EventConstant.ROOM_BG) {
            val bgPicture: String? = event?.data as String?


            var mRoomSettingEntity = RoomSettingEntity(null, null,
                    null, null, null, null,
                    null, null, null, null,
                    bgPicture, null, null, null, null,
                    null, null, null, null, null,
                    null, null)
            val roomSetting = Gson().toJson(mRoomSettingEntity)

            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdRoomBgViewChange, roomSetting) { code, msg ->
                if (code == 0) {
                    mRoomInfo?.roomBgPicture = bgPicture
                    setRoomConfig(mRoomInfo)
                }

            }

        }

        if (event?.code == EventConstant.ROOM_GIFT) {

            var isOpenTx: Boolean = event?.data as Boolean
            setGiftSeitch(isOpenTx)
        }

        if (event?.code == EventConstant.ROOM_PUBLIC) {

            var isOpenGp: Boolean = event?.data as Boolean
            var mRoomSettingEntity = RoomSettingEntity(null, null,
                    null, null, null, isOpenGp,
                    null, null, null, null,
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, null)
            val mRoonSettingMsg = Gson().toJson(mRoomSettingEntity)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdPublicChange, mRoonSettingMsg) { code, msg ->
                if (code == 0) {
                    mRoomPublic = if (isOpenGp) 1 else 0
                    mRoomInfo?.roomPublic = mRoomPublic

                    isOpenGp(true, isOpenGp)

                }
            }
        }

        if (event?.code == EventConstant.OPEN_GIFT_WINDOWS) {
            if (!isGift) {
                isGift = true
                if (mRoomGiftView != null) {
                    mRoomGiftView?.setGiveAway(null, mMainSeatUserId, mVoiceRoomSeatEntityList)
                    mRoomGiftView?.show(supportFragmentManager, TAG)
                }
            }
        }

        if (event?.code == EventConstant.ROOM_PICK) {

            var isPice = event.data as Boolean

            var mRoomSettingEntity = RoomSettingEntity(null, null,
                    null, null, null, null,
                    null, null, null, isPice,
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, null)
            val mRoonSettingMsg = Gson().toJson(mRoomSettingEntity)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdExclusionChange, mRoonSettingMsg) { code, msg ->
                if (code == 0) {

                    mNeedRequest = isPice
                    mRoomInfo?.roomExclusion = if (isPice) 1 else 0

                    setRoomConfig(mRoomInfo)

                    mTRTCVoiceRoom.closeSeat(isPice, null)

                    val msgBody = MsgBody()
                    msgBody.type = MsgBody.MSG_PICE_SEAT
                    msgBody.roomPick = mNeedRequest

                    showNotifyMsg(msgBody)


                }
            }

        }

        if (event?.code == EventConstant.LOTTER_PRICE) {
            var giftsResult = event?.data as GiftsResult
            if (giftsResult?.giftPriceType == 2 && giftsResult?.giftPrice >= Constant.PUBLICITY_PRICE_2) {
                var globalMsgResult = GlobalMsgResult(giftsResult.giftPrice.toString(), mRoomId, mSelfUserInfo?.userPicture, giftsResult.giftStaticUrl,
                        null, giftsResult.giftCont.toString(), mSelfUserInfo?.userName, null, giftsResult?.giftName, 1,giftsResult.boxType)
                val globalGson = Gson().toJson(globalMsgResult)
                mTRTCVoiceRoom.sendGlobalCustomMsg(CustomMsgConstant.CustomMsgCmdGlobalMesssage, globalGson) { code, msg ->
                    var createNotice = createNotice(globalMsgResult.type, globalMsgResult)
                    if (createNotice != null) {
                        if (globalMsgResult.type == 1){
                            var newManager = NewRoomAnimatorManager.getInstance()
                            newManager.setInfo(this, mCostraint)
                            newManager.addAnimation(createNotice)
                        } else {
                            var manager = RoomAnimatorManager.getInstance()
                            manager.setInfo(this, mCostraint)
                            manager.addAnimation(createNotice)
                        }
                    }
                }
            }

            var result = "${giftsResult.giftName} (${giftsResult.giftPrice}${if (giftsResult.giftPriceType == 1) "辣椒" else "金币"}) x${giftsResult.giftCont}"

            val lotteryEntity = LotteryEntity(giftsResult.giftPrice, mSelfUserInfo?.userLevel, giftsResult.giftPriceType, mSelfUserInfo?.id,
                    mSelfUserInfo?.noble!!.toInt(), mSelfUserInfo?.userName, giftsResult.boxType, 4, result)
            val lotterJson = Gson().toJson(lotteryEntity)
            mTRTCVoiceRoom.sendGlobalCustomMsg(CustomMsgConstant.CustomMsgCmdLotteryResult, lotterJson) { code, msg ->
                if (code == 0) {
                    val msgBody = MsgBody()
                    msgBody.type = MsgBody.MSG_LOTTERY
                    msgBody.lottery = lotteryEntity
                    showNotifyMsg(msgBody)
                }

            }


        }
    }


    /**
     * 发送设置
     */
    private fun sendSettingRoom(roomSetting: RoomSettingEntity, callback: TRTCVoiceRoomCallback.ActionCallback) {

        if (roomSetting == null) return

        val roomSetting = Gson().toJson(roomSetting)

        mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdTitleChange, roomSetting, callback)
    }

    /**
     * 接收到房间修改消息
     */
    override fun receiveRoomSetting(cmd: String?, roomSetting: RoomSettingEntity?) {
        super.receiveRoomSetting(cmd, roomSetting)
        if (cmd == CustomMsgConstant.CustomMsgCmdTitleChange) {
            mRoomInfo?.roomNoticeTitle = roomSetting?.roomNoticeTitle
            mRoomInfo?.roomNoticeComment = roomSetting?.roomNoticeComment
            setRoomConfig(mRoomInfo)
        }

        if (cmd == CustomMsgConstant.CustomMsgCmdGiftShowChange) {
            giftValue = if (roomSetting?.giftValue!!) 1 else 0
            mRoomInfo?.giftValue = giftValue

            setIsOpenGiftValue(giftValue)
        }

        if (cmd == CustomMsgConstant.CustomMsgCmdPublicChange) {
            mRoomPublic = if (roomSetting?.roomPublic!!) 1 else 0
            mRoomInfo?.roomPublic = mRoomPublic

            isOpenGp(true, roomSetting?.roomPublic!!)
        }

        if (cmd == CustomMsgConstant.CustomMsgCmdNameChange) {
            mRoomInfo?.roomName = roomSetting?.roomName
            setRoomConfig(mRoomInfo)
        }

        if (cmd == CustomMsgConstant.CustomMsgCmdPwdChange) {
            mRoomInfo?.roomLock = if (roomSetting?.roomLock!!) 1 else 0
            mRoomInfo?.roomPassword = roomSetting?.roomPassword
            setRoomConfig(mRoomInfo)
        }

        if (cmd == CustomMsgConstant.CustomMsgCmdTypeChange) {

            mRoomInfo?.roomTypeColor = roomSetting?.roomTypeColor
            mRoomInfo?.typeName = roomSetting?.typeName
            mRoomInfo?.roomTypeId = roomSetting?.roomTypeId

            setRoomConfig(mRoomInfo)
        }

        if (cmd == CustomMsgConstant.CustomMsgCmdRoomBgViewChange) {
            mRoomInfo?.roomBgPicture = roomSetting?.roomBgPicture
            setRoomConfig(mRoomInfo)
        }

        if (cmd == CustomMsgConstant.CustomMsgCmdExclusionChange) {
            val roomExclusion = roomSetting?.roomExclusion
            mNeedRequest = roomExclusion!!
            mRoomInfo?.roomExclusion = if (roomExclusion) 1 else 0

            setRoomConfig(mRoomInfo)

            mTRTCVoiceRoom.closeSeat(roomExclusion, null)

            val msgBody = MsgBody()
            msgBody.type = MsgBody.MSG_PICE_SEAT
            msgBody.roomPick = mNeedRequest
            showNotifyMsg(msgBody)

        }


    }

    /**
     * 设置管理员
     */
    override fun setUserKind(cmd: String?, roomManager: RoomManagerMsgEntity?) {
        super.setUserKind(cmd, roomManager)

        if (mSelfUserInfo?.id == roomManager?.userId) {
            mRoomInfo?.userKind = roomManager?.userKind!!
            mSelfUserInfo?.userKind = roomManager?.userKind!!
            setRoomConfig(mRoomInfo)
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            minimize()
            false
        } else {
            super.onKeyDown(keyCode, event);
        }

    }


}
