package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.Manifest
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Group
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.github.florent37.viewanimator.ViewAnimator
import com.google.gson.Gson
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.*
import com.xiaoshanghai.nancang.constant.*
import com.xiaoshanghai.nancang.constant.Constant.isAnchorColse
import com.xiaoshanghai.nancang.constant.Constant.isMaikStatus
import com.xiaoshanghai.nancang.helper.EnterRoomAnimManager
import com.xiaoshanghai.nancang.helper.NewRoomAnimatorManager
import com.xiaoshanghai.nancang.helper.RoomAnimatorManager
import com.xiaoshanghai.nancang.helper.SVGAImageViewManage
import com.xiaoshanghai.nancang.mvp.contract.ViceBaseRoomContract
import com.xiaoshanghai.nancang.mvp.presenter.ViceBaseRoomPresenter
import com.xiaoshanghai.nancang.mvp.ui.activity.main.MainActivity
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HeadgearAct
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyBuddyAct
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.NobleWebActivity
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ChatWithAct
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ReportAct
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.SpeakAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.MsgAdapter
import com.xiaoshanghai.nancang.mvp.ui.adapter.VoiceLiveSeatAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.*
import com.xiaoshanghai.nancang.net.bean.*
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.TipsDialog
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAParser.Companion.shareParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomCallback
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDef.SeatInfo.*
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoomDelegate
import com.tencent.liteav.trtcvoiceroom.model.impl.base.TXSeatInfo.STATUS_OPEN
import com.tencent.liteav.trtcvoiceroom.model.impl.trtc.VoiceRoomTRTCService
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity
import com.tencent.liteav.trtcvoiceroom.ui.widget.ConfirmDialogFragment
import com.tencent.liteav.trtcvoiceroom.ui.widget.InputTextMsgDialog
import com.tencent.liteav.trtcvoiceroom.ui.widget.SelectMemberView
import com.tencent.liteav.trtcvoiceroom.ui.widget.msg.MsgEntity
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.trtc.TRTCCloudDef


open class VoiceBaseRoomAct : BaseMvpActivity<ViceBaseRoomPresenter>(), ViceBaseRoomContract.View,
        InputTextMsgDialog.OnTextSendListener, MsgAdapter.OnItemClickListener, VoiceLiveSeatAdapter.OnItemClickListener,
        TRTCVoiceRoomDelegate, RoomFaceCallback, OnGiftSendCallback, RoomSettingCallback, DisMissCallback, OnSeatClickCallback, OnRowMikeCallback, OnCounrolClickListener {

    val TAG = VoiceBaseRoomAct::class.java.name

    @JvmField
    @BindView(R.id.costraint)
    var mCostraint: ConstraintLayout? = null

    @JvmField
    @BindView(R.id.iv_room_bg)
    var mIvRoomBg: ImageView? = null //????????????

    @JvmField
    @BindView(R.id.iv_back)
    var mIvBack: ImageView? = null  //????????????

    @JvmField
    @BindView(R.id.tv_title)
    var mTvTitle: TextView? = null   //?????????

    @JvmField
    @BindView(R.id.iv_lock)
    var mIvLock: ImageView? = null //??????????????????

    @JvmField
    @BindView(R.id.iv_close_tx)
    var mIvRoomCloseTx: ImageView? = null //??????????????????

    @JvmField
    @BindView(R.id.iv_lh)
    var mIvLh: ImageView? = null     //????????????

    @JvmField
    @BindView(R.id.tv_id_num)
    var mTvRoomId: TextView? = null  //??????ID

    @JvmField
    @BindView(R.id.tv_online_num)
    var mTvOnLineNum: TextView? = null //????????????

    @JvmField
    @BindView(R.id.iv_room_shar)
    var mIvRoomShar: ImageView? = null //??????????????????

    @JvmField
    @BindView(R.id.iv_room_more)
    var mIvRoomMore: ImageView? = null //??????????????????

    @JvmField
    @BindView(R.id.main_mike)
    var mMainMike: MikeView? = null     //?????????

    @JvmField
    @BindView(R.id.tv_room_rank)
    var mTvRoomRank: TextView? = null //???????????????

    @JvmField
    @BindView(R.id.tv_room_type)
    var mTvRoomType: TextView? = null // ????????????

    @JvmField
    @BindView(R.id.tv_bulletin)
    var mTvBulletin: TextView? = null //??????????????????

    @JvmField
    @BindView(R.id.iv_edit)
    var mIvEdit: ImageView? = null //??????????????????

    @JvmField
    @BindView(R.id.rv_seat)
    var mRvSeat: RecyclerView? = null //????????????

    @JvmField
    @BindView(R.id.iv_lottery)
    var mIvLottery: ImageView? = null

    @JvmField
    @BindView(R.id.rv_im_msg)
    var mRvImMsg: RecyclerView? = null //??????????????????

    @JvmField
    @BindView(R.id.group_audience)
    var mAudience: Group? = null     //???????????????????????????????????????

    @JvmField
    @BindView(R.id.group_anchor)
    var mAnchor: Group? = null        //???????????????????????????????????????

    @JvmField
    @BindView(R.id.iv_mike)
    var mIvMaike: ImageView? = null   //?????????????????????

    @JvmField
    @BindView(R.id.iv_sound)
    var mIvSound: ImageView? = null   //??????????????????

    @JvmField
    @BindView(R.id.svag)
    var mSvga: SVGAImageView? = null

    @JvmField
    @BindView(R.id.ll_seat_face)
    var mSeatFace: LinearLayout? = null

    @JvmField
    @BindView(R.id.ll_seat_wave)
    var mSeatWave: LinearLayout? = null

    @JvmField
    @BindView(R.id.ll_gift)
    var mGiftValueView: LinearLayout? = null

    @JvmField
    @BindView(R.id.iv_row_seat)
    var mIvRowSeat: ImageView? = null

    @JvmField
    @BindView(R.id.view_start)
    var mViewStart: View? = null  //?????????????????????

    @JvmField
    @BindView(R.id.view_end)
    var mViewEnd: View? = null    //???????????????????????????


    companion object {

        const val MAX_SEAT_SIZE = 9 //????????????????????????

        const val VOICEROOM_ROOM_ID = "room_id" //??????id

        const val VOICEROOM_ROOM_NAME = "room_name" //?????????

//        const val VOICEROOM_TITLE_COLOR = "room_title_color"    //????????????

//        const val VOICEROOM_USER_NAME = "user_name" //?????????

        const val VOICEROOM_USER_ID = "user_id" //??????id

        const val VOICEROOM_USER_SIG = "user_sig"

        const val VOICEROOM_NEED_REQUEST = "need_request" //??????????????????????????????

        const val VOICEROOM_SEAT_COUNT = "seat_count"

        const val VOICEROOM_AUDIO_QUALITY = "audio_quality" //??????

        const val VOICEROOM_CREATE = "create_status" //?????????????????????

        const val VOICEROOM_ISAGAIN = "voiceroom_isagain" //???????????????????????????????????????

//        const val VOICEROOM_USER_AVATAR = "user_avatar" //????????????

//        const val VOICEROOM_ROOM_COVER = "room_cover" //????????????

        const val START_ON_LINE = 300

    }


    private val RESQUEST_OPEN = 100//?????????????????????

    private val RESQUEST_CLOSE = 200//?????????????????????

    protected var mSelfUserId: String? = null //????????????ID

    protected var mCurrentRole = 0//??????????????????

    protected var mSeatUserSet: MutableSet<String>? = null//???????????????????????????

    protected val mTRTCVoiceRoom: TRTCVoiceRoom by lazy { TRTCVoiceRoom.sharedInstance(this) }//??????????????????

    protected var mVoiceRoomSeatEntityList: MutableList<VoiceRoomSeatEntity>? = null //????????????

    protected var mFaceView: MutableList<SVGAImageView>? = null

    protected var mWaveView: MutableList<WaveView>? = null

    protected var mGiftValue: MutableList<GiftValueView>? = null

    protected var mVoiceRoomSeatAdapter: VoiceLiveSeatAdapter? = null//??????Adapter

    protected var mMsgEntityList: MutableList<MsgBody>? = null//????????????

    protected var mMsgListAdapter: MsgAdapter? = null//??????adapter

    protected val mConfirmDialogFragment by lazy { ConfirmDialogFragment() } //?????????dialog

    protected val mViewSelectMember by lazy { SelectMemberView(this) } //????????????dialog

    protected val mInputTextMsgDialog by lazy { InputTextMsgDialog(this, R.style.TRTCVoiceRoomInputDialog) }    //????????????dialog

    protected val mRoomFaceView: RoomFaceView by lazy { RoomFaceView.newInstance() }

    protected var mRoomRank: RoomRankView? = null

    protected val mControlPopupView by lazy { ControlPopupView.newInstance() }


    protected var mRoomId: String? = null//??????ID

    protected var mRoomName: String? = null//?????????

//    protected var mUserName: String? = null //???????????????

//    protected var mUserAvatar: String? = null//????????????url

//    protected var mRoomCover: String? = null //????????????

    protected var mOwnerId: String? = null  //??????ID

    protected var mMainSeatUserId: VoiceRoomSeatEntity? = null//???????????????


//    protected var isAnchorColse = false //????????????????????????????????????????????????????????????????????????????????????????????????

//    protected var isMaikStatus = false //????????????????????? ?????????????????????????????????????????? ?????????????????????

    protected var isRoomMute = false //??????????????????

    protected var mAudioQuality = 0 //??????

    protected var mSvgaManager: SVGAImageViewManage? = null //???????????????????????????

    protected val svgaParser: SVGAParser by lazy { shareParser() }  //????????????

    protected var isCreate = false

//    protected var mTitleColor: String? = null

//    protected val mRoomGift: RoomGiftResult? = null     //????????????????????????

    protected var mRoomGiftView: RoomGiftView? = null

    protected var mRoomSpecial = true//?????????????????????????????? true.?????? false.??????

    protected var mRoomPublic = 0//???????????????????????? 1.?????? 0.?????????

    protected var giftValue = 0//?????????????????????

    protected var mUserKind: Int = -1

    protected var mNeedRequest = false // ???????????????????????? ????????????????????????

    protected var mRoomLock = 0 //?????????????????? 1?????? 0?????????


    //???????????????????????????????????????????????????????????????
    protected var mikesSeat: MutableMap<String, MikesBean> = HashMap()

    protected var mIsSeatInitSuccess = false        //?????????????????????

    protected var mSelfUserInfo: VoiceRoomSeatEntity.UserInfo? = null   //?????????????????????

    protected var mRoomInfo: CreateRoomResult? = null   //???????????????????????????

    protected val mRoomSetting: RoomSettingView by lazy { RoomSettingView.newInstance() }//??????????????????dialog

    private var isFaceViewUp: Boolean = false   //????????????????????????

    private var isRoomRank: Boolean = false //?????????????????????

    protected var isGift: Boolean = false //????????????????????????

    private var isSetting: Boolean = false //????????????????????????

    protected var isSeatWindows: Boolean = false    //????????????????????????

    protected var isManagerWindows: Boolean = false  //???????????????????????????

    protected var isUpSeat: Boolean = false         //????????????????????????

    protected var isControl: Boolean = false     //?????????????????????????????????

    protected var mHiChatRoomId: String? = null     //?????????ID

    protected var isAgain: Boolean = false       //?????????????????????????????????

    protected var mContext: Context? = null


    //??????????????????
    override fun isFull(): Boolean = true

    //??????????????????
    override fun isRegisterEventBus(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this
//        // ?????????????????????????????????????????????
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun setLayoutId(): Int = R.layout.activity_voice_room

    override fun createPresenter(): ViceBaseRoomPresenter = ViceBaseRoomPresenter()

    override fun initView(savedInstanceState: Bundle?) {

//        EasyFloat.dismiss()

        MainActivity.mRoomId = ""

        mPresenter.attachView(this)
        initView()
        initDate(intent)

        EventBusUtil.sendEvent(Event<BackstageRoom>(EventConstant.FLOAT_DISMISS, null))

    }

    /**
     * ???????????????
     */
    protected fun initView() {

        mRoomSpecial = SPUtils.getInstance().getBoolean(SpConstant.ROOM_IS_PLAY, true)

        setRoomSpecialIcon(mRoomSpecial)

        //??????????????????dialog
        mInputTextMsgDialog.setmOnTextSendListener(this)
        mSvgaManager = SVGAImageViewManage(this, mRoomSpecial, mSvga)
        mRoomFaceView.setCallback(this)

        //?????????????????????
        mMsgEntityList = ArrayList()

        //?????????????????????adapter
        mMsgListAdapter = MsgAdapter(this, mMsgEntityList, this)

        mRvImMsg?.layoutManager = LinearLayoutManager(this)
        mRvImMsg?.adapter = mMsgListAdapter

        //???????????????????????????
        mVoiceRoomSeatEntityList = ArrayList()


        for (i in 0 until MAX_SEAT_SIZE - 1) {
            val voiceRoomSeatEntity = VoiceRoomSeatEntity()
            voiceRoomSeatEntity.userInfo = VoiceRoomSeatEntity.UserInfo()
            mVoiceRoomSeatEntityList?.add(voiceRoomSeatEntity)
        }

        //Gridlayout ??????????????????
        val gridLayoutManager = GridLayoutManager(this, 4)
        //?????????????????????Adapter
        mVoiceRoomSeatAdapter = VoiceLiveSeatAdapter(this, mVoiceRoomSeatEntityList, giftValue == 1, this)
        mRvSeat?.layoutManager = gridLayoutManager
        mRvSeat?.adapter = mVoiceRoomSeatAdapter

        mFaceView = getImageView(mSeatFace!!)

        mWaveView = getWaveView(mSeatWave!!)

        mGiftValue = getValueView(mGiftValueView!!)

        mPresenter.getRoomGift()

        mRoomSetting.setRoomSettingCallback(this)


    }

    override fun onResume() {
        super.onResume()
        mPresenter.getRoomConfig()
    }

    /**
     * ???????????????
     */
    protected fun initDate(intent: Intent?) {

//        val intent = intent
        mRoomId = intent?.getStringExtra(VOICEROOM_ROOM_ID)
        mRoomName = intent?.getStringExtra(VOICEROOM_ROOM_NAME)
        mSelfUserId = intent?.getStringExtra(VOICEROOM_USER_ID)
        mNeedRequest = intent!!.getBooleanExtra(VOICEROOM_NEED_REQUEST, false)
        isCreate = intent.getBooleanExtra(VOICEROOM_CREATE, false)
        mAudioQuality = intent.getIntExtra(VOICEROOM_AUDIO_QUALITY, TRTCCloudDef.TRTC_AUDIO_QUALITY_MUSIC)

        isAgain = intent.getBooleanExtra(VOICEROOM_ISAGAIN, false)

//        mTitleColor = intent.getStringExtra(VOICEROOM_TITLE_COLOR)

//        mUserName = intent.getStringExtra(VOICEROOM_USER_NAME)
//        mUserAvatar = intent.getStringExtra(VOICEROOM_USER_AVATAR)
//        mRoomCover = intent.getStringExtra(VOICEROOM_ROOM_COVER)

        mTRTCVoiceRoom.setDelegate(this)

        if (mRoomRank == null) {
            mRoomRank = RoomRankView.newInstance(mRoomId)
            mRoomRank?.setOnDissmissCallback(this)

        }

    }

//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        var roomId = intent?.getStringExtra(VOICEROOM_ROOM_ID)
//        if (roomId != mRoomId) {
//            mTRTCVoiceRoom.exitRoom { code, msg ->
//
//                initView()
//                initDate(intent)
//
//                mPresenter.getRoomUser(mSelfUserId!!, mRoomId!!)
//
//            }
//
//
//        }
//    }


    /**
     * ????????????????????????????????????????????????
     *
     * @return ???????????????
     */
    protected open fun checkButtonPermission(): Boolean {
        val hasPermission = mCurrentRole == TRTCCloudDef.TRTCRoleAnchor
        if (!hasPermission) {
            ToastUtils.showLong("?????????????????????")
        }
        return hasPermission
    }

    /**
     * ?????????????????????????????????????????????
     */
    protected open fun isAnchor(anchor: Boolean) {
        if (anchor) {
            mAnchor?.visibility = View.VISIBLE
            mAudience?.visibility = View.GONE
        } else {
            mAnchor?.visibility = View.GONE
            mAudience?.visibility = View.VISIBLE
        }
    }

    /**
     * ???????????????
     */
    protected open fun mikeSwitch() {

        //???????????????????????????????????????????????????????????????????????????
        if (isAnchorColse) {
            //????????????????????????????????????????????????????????????
            mIvMaike?.setImageResource(R.mipmap.icon_mike_close)

            closeMike()

        } else {
            //????????????????????????????????????????????????????????? true ????????????????????? false ???????????????
            if (isMaikStatus) {
                if (checkButtonPermission()) {
                    closeMike()
                }
            } else {
                if (checkButtonPermission()) {
                    openMike()
                }
            }
        }

    }


    /**
     * ????????????Dialog
     */
    private fun showInputMsgDialog() {
        val display = windowManager.defaultDisplay
        val lp = mInputTextMsgDialog.window.attributes;
        lp.width = display.width
        mInputTextMsgDialog.window.attributes = lp
        mInputTextMsgDialog.setCancelable(true)
        mInputTextMsgDialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        mInputTextMsgDialog.show()
    }

    open fun resetSeatView() {
        mSeatUserSet?.clear()
        for (entity in mVoiceRoomSeatEntityList!!) {
            entity.isUsed = false
        }
        mVoiceRoomSeatAdapter!!.notifyDataSetChanged()
    }


    /**
     * ????????????
     */
    override fun onTextSend(msg: String) {
        if (msg.isEmpty()) {
            return
        }
    }

    @OnClick(R.id.iv_back, R.id.tv_msg_btn, R.id.iv_msg, R.id.iv_mike, R.id.iv_sound, R.id.main_mike,
            R.id.iv_face, R.id.iv_gift, R.id.tv_room_rank, R.id.tv_bulletin, R.id.iv_more, R.id.iv_room_shar,
            R.id.iv_lottery, R.id.tv_online_name, R.id.iv_row_seat, R.id.iv_buddy, R.id.iv_room_more, R.id.tv_title)
    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
//                onBackPressed()
//                moveTaskToBack(true)
//                enterPiPMode()

                minimize()

            }

            R.id.tv_msg_btn, R.id.iv_msg -> {
                if (mRoomPublic == 1) {
                    showInputMsgDialog()
                } else {
                    ToastUtils.showShort("??????????????????????????????")
                }
            }

            R.id.iv_mike -> {
                if (!isAnchorColse && checkButtonPermission()) {
                    isMaikStatus = !isMaikStatus
                    mikeSwitch()
                }
            }

            R.id.iv_sound -> {
                isRoomMute = !isRoomMute
                isRoomMute(isRoomMute)
            }

            R.id.main_mike -> {
                mainSeatOnClick()
            }

            R.id.iv_face -> {
                if (!isFaceViewUp) {
                    isFaceViewUp = true
                    mRoomFaceView.show(supportFragmentManager, TAG)
                }
            }

            R.id.iv_gift -> {
                if (!isGift) {
                    isGift = true
                    if (mRoomGiftView != null) {
                        mRoomGiftView?.setGiveAway(null, mMainSeatUserId, mVoiceRoomSeatEntityList)
                        mRoomGiftView?.show(supportFragmentManager, TAG)
                    }
                }
            }

            R.id.tv_room_rank -> {
                if (!isRoomRank) {
                    isRoomRank = true
                    mRoomRank?.show(supportFragmentManager, TAG)
                }
            }

            R.id.tv_bulletin -> {
                onClickNotiy()
            }

            R.id.iv_more -> {
                if (!isSetting) {
                    isSetting = true
                    mRoomSetting?.setSwitch(mRoomSpecial, mRoomPublic == 1, giftValue == 1, mUserKind)
                    mRoomSetting?.show(supportFragmentManager, TAG)
                }

            }

            R.id.iv_room_shar -> {
//                startAnimation()

                TipsDialog.createDialog(this, R.layout.dialog_share)
                        .bindClick(R.id.tv_cancel) { v: View?, dialog: TipsDialog -> dialog.dismiss() }
                        .bindClick(R.id.tv_share_buddy) { v: View?, dialog: TipsDialog? ->

                            var shareRoom = ShareRoom(mRoomId, mRoomName, mRoomInfo?.coverUrl)

                            val bundle = Bundle()
                            bundle.putString(Constant.BUDDY_KEY, Constant.SHARE_ROOM)
                            bundle.putSerializable(Constant.SHARE_ROOM, shareRoom)
                            ActStartUtils.startAct(this, MyBuddyAct::class.java, bundle)
                        }
                        .show()

            }

            R.id.iv_lottery -> {
                ActStartUtils.startAct(this, LotterySelectionAct::class.java)
            }

            R.id.tv_online_name, R.id.tv_title -> {
                var bundle = Bundle()
                bundle.putSerializable(Constant.ROOM_INFO, mRoomInfo)
                bundle.putInt(Constant.ROOM_SELF_KIND, mSelfUserInfo?.userKind!!)
                ActStartUtils.startAct(this, RoomOnLineAct::class.java, bundle)
            }

            R.id.iv_row_seat -> {
                val mRoomRowMike = RoomRowMikeView.newInstance(mSelfUserInfo?.userKind!!, mRoomId)
                mRoomRowMike.setOnRowMikeCallback(this)
                mRoomRowMike.show(supportFragmentManager, TAG)

            }

            R.id.iv_buddy -> {
                ActStartUtils.startAct(this, ChatWithAct::class.java)
            }

            R.id.iv_room_more -> {
                if (!isControl) {
                    mPresenter.enterHiChatRoom(OnHiChatCallback {
                        if (it != null && it != "-1") {
                            isControl = true
                            mHiChatRoomId = it
                            mControlPopupView.onSetRoomId(it)
                            mControlPopupView.setOnCounrolClick(this)
                            mControlPopupView.show(supportFragmentManager, TAG)
                        }
                    })
                }

            }
        }
    }

    private var mPictureInPictureParamsBuilder: PictureInPictureParams.Builder? = null


    protected open fun onClickNotiy() {

    }


    /**
     * ???????????????
     */
    protected open fun mainSeatOnClick() {

    }


    /**
     * ????????????
     *      isMute: true ?????? false ?????????
     */
    private fun isRoomMute(isMute: Boolean) {

        if (isMute) {
            mIvSound?.setImageResource(R.mipmap.icon_sound_close)
        } else {
            mIvSound?.setImageResource(R.mipmap.icon_sound)

        }
        mTRTCVoiceRoom.muteAllRemoteAudio(isMute)
    }

    protected open fun changeSeatIndexToModelIndex(srcSeatIndex: Int): Int {
        return srcSeatIndex + 1
    }

    /**
     * ??????
     */
    protected open fun showNotifyMsg(msg: String?) {

//        val msgEntity = MsgEntity()
//        msgEntity.type = MsgEntity.TYPE_NORMAL
//        msgEntity.content = msg
//        mMsgEntityList!!.add(msgEntity)
//        mMsgListAdapter!!.notifyDataSetChanged()
//        mRvImMsg.smoothScrollToPosition(mMsgListAdapter!!.itemCount)
//
    }


    /**
     *????????????
     */
    protected open fun showNotifyMsg(msg: MsgBody?) {
        if (mRoomPublic == 1) {

            runOnUiThread {
                if (mMsgListAdapter?.itemCount!! > 500) {


                    var deleteList = mMsgEntityList?.subList(0, 200)

                    mMsgEntityList?.removeAll(deleteList as MutableList)
                    mMsgListAdapter!!.notifyDataSetChanged()

                }

                mMsgListAdapter!!.addData(msg!!)
                mRvImMsg?.scrollToPosition(mMsgListAdapter?.itemCount!! - 1);
//                mRvImMsg?.smoothScrollToPosition(mMsgListAdapter?.itemCount!!)
            }
        }
    }

    /**
     * ??????????????????
     */
    private fun showImMsg(entity: MsgEntity) {

//        runOnUiThread {
//            if (mMsgEntityList?.size!! > 1000) {
//                while (mMsgEntityList?.size!! > 900) {
//                    mMsgEntityList?.removeAt(0)
//                }
//            }
//            mMsgEntityList?.add(entity)
//            mMsgListAdapter?.notifyDataSetChanged()
//            mRvImMsg.smoothScrollToPosition(mMsgListAdapter!!.itemCount)
//        }

    }


    //    ?????????onRequestPermissionsResult????????????????????????
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {


        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                permissions[0] == Manifest.permission.RECORD_AUDIO) {

            if (requestCode == RESQUEST_OPEN) {
                openMike()
            } else if (requestCode == RESQUEST_CLOSE) {
                closeMike()
            }
        } else {
            ToastUtils.showShort("???????????????????????????")
        }

    }


    /**
     * ???????????????
     */
    fun openMike() {
        if (checkAudio()) {
//            mTRTCVoiceRoom.startMicrophone()
            runOnUiThread { VoiceRoomTRTCService.getInstance().startMicrophone() }

//            runOnMainThread(Runnable { VoiceRoomTRTCService.getInstance().startMicrophone() })
            mIvMaike?.setImageResource(R.mipmap.icon_mike_open)
//            isMaikStatus = false
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RESQUEST_OPEN)
        }

    }


    /**
     * ???????????????
     */
    fun closeMike() {

        if (checkAudio()) {
//            mTRTCVoiceRoom.stopMicrophone()
            runOnUiThread { VoiceRoomTRTCService.getInstance().stopMicrophone() }
            mIvMaike?.setImageResource(R.mipmap.icon_mike_close)
            isMaikStatus = true

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), RESQUEST_CLOSE)
        }

    }

    fun checkAudio(): Boolean {
        val checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        return checkSelfPermission == PackageManager.PERMISSION_GRANTED
    }


    /**
     * ????????????
     */
    override fun onAgreeClick(position: Int, userId: String?) {

        if (!TextUtils.isEmpty(userId)) {
            mPresenter.getUser(userId, mRoomId, object : RoomUserCallback {
                override fun onRoomUser(userInfo: VoiceRoomSeatEntity.UserInfo?) {

                    mPresenter.queryFollow(userId!!, object : FollowCallback {
                        override fun onFollow(status: Int) {

                            if (!isSeatWindows) {

                                var isFollow = status == 1

                                val userSeatInfo = getUserSeatInfo(userId)

                                val roomSeatDialog = RoomSeatView.newInstance(userInfo, mSelfUserInfo?.userKind!!, userSeatInfo, isFollow)
                                roomSeatDialog.setOnSeatClickCallback(this@VoiceBaseRoomAct)
                                roomSeatDialog.show(supportFragmentManager, TAG)
                                isSeatWindows = true
                            }
                        }

                    })


                }

            })
        }


    }

    /**
     * ??????????????????????????????
     *
     * @param position
     */
    override fun onItemClick(position: Int) {

    }

    /**
     * ?????????????????????????????????????????????
     */
    override fun onError(code: Int, message: String?) {

    }

    /**
     * ??????????????????
     */
    override fun onWarning(code: Int, message: String?) {

    }

    /**
     * ??????log??????
     */
    override fun onDebugLog(message: String?) {
    }

    /**
     * ?????????????????????????????????destroyRoom??????????????????????????????
     */
    override fun onRoomDestroy(roomId: String?) {
    }

    /**
     * ???????????????????????????
     */
    override fun onRoomInfoChange(roomInfo: TRTCVoiceRoomDef.RoomInfo?) {
//        mNeedRequest = roomInfo!!.needRequest
//        mRoomName = roomInfo.roomName
//        mTvTitle?.text = mRoomName
//        mTvOnLineNum.text = roomInfo.memberCount.toString()
    }

    /**
     * ???????????????????????????,????????????????????????
     * @param seatInfoList ?????????????????????
     */
    override fun onSeatListChange(seatInfoList: MutableList<TRTCVoiceRoomDef.SeatInfo>?) {
        //userids ????????????????????????userid
        val userids: MutableList<String> = ArrayList()
        //?????????????????????????????????
        for (i in seatInfoList!!.indices) {

            //????????????????????????????????????
            val newSeatInfo = seatInfoList[i]

            //?????????1?????????????????????????????????,???????????????????????????????????????????????????????????????????????????????????????????????????
            if (i == 0) {

                //??????????????????????????????????????????????????????????????????????????????????????????userids?????????
                //?????????????????????????????????????????????????????????user????????????????????????????????????????????????????????????user?????????
                if (!TextUtils.isEmpty(newSeatInfo.userId) && newSeatInfo.userId != mMainSeatUserId?.userId) {
                    userids.add(newSeatInfo.userId)
                }

                //??????????????? ????????? ??????????????????userId ?????? ???????????????????????????userId ??? ?????????????????????userId?????????
                //?????????????????????????????????????????????????????????????????????????????????
                if (mMainSeatUserId == null || TextUtils.isEmpty(mMainSeatUserId?.userId) ||
                        mMainSeatUserId?.userId != newSeatInfo.userId) {

                    //?????????
                    val userInfo = VoiceRoomSeatEntity.UserInfo()
                    if (mMainSeatUserId == null) {
                        mMainSeatUserId = VoiceRoomSeatEntity()
//                        mMainSeatUserId?.userInfo = VoiceRoomSeatEntity.UserInfo()
                    }

                    //??????????????????????????????????????????userId??????????????? ???????????????
                    mMainSeatUserId?.userId = newSeatInfo.userId

                    mMainSeatUserId?.userAvatar = ""
                    mMainSeatUserId?.userName = ""

                    userInfo.let {
                        /// ????????????????????????????????????
                        it.id = newSeatInfo.userId
                        /// ??????????????????????????????
                        it.userName = ""
                        /// ??????????????????????????????
                        it.userPicture = ""
                        /// ??????????????????????????????
                        it.userSex = -1
                        /// ?????????????????? ?????????????????? 1?????? 2????????? 3?????????
                        it.userKind = 0
                        /// ??????????????????????????????  0?????? 1?????? 2?????? 3?????? 4?????? 5?????? 6?????? 7??????
                        it.noble = "0"
                        /// ?????????????????????????????????????????????
                        it.giftCoinCount = 0
                        /// ??????????????????????????????
                        it.headwear = ""
                    }

                    mMainSeatUserId?.userInfo = userInfo

                }

                mMainSeatUserId?.isMute = newSeatInfo.mute

                if (newSeatInfo.status == STATUS_USED) {
                    mMainSeatUserId?.isUsed = true
                } else if (newSeatInfo.status == STATUS_UNUSED) {
                    mMainSeatUserId?.isUsed = false
                }

                if (newSeatInfo.closeStatus == STATUS_OPEN) {
                    mMainSeatUserId?.isClose = false
                } else if (newSeatInfo.closeStatus == STATUS_CLOSE) {
                    mMainSeatUserId?.isClose = true
                }


                mMainMike?.setSeatResult(mMainSeatUserId)
                //???????????????????????????????????????????????????????????????????????????
                continue
            }

            //??????????????????????????????????????????
            //??????????????????????????????????????? ??????????????????????????????-1
            val oldSeatInfo = mVoiceRoomSeatEntityList?.get(i - 1)

            //???????????????userid?????????????????????????????????????????????????????????????????????????????? ??????????????????????????????????????????????????????ID ??????????????????????????????????????????????????????
            //?????????????????? ??????????????????????????????????????????????????????????????????
            if (!TextUtils.isEmpty(newSeatInfo.userId) && newSeatInfo.userId != oldSeatInfo?.userId) {
                userids.add(newSeatInfo.userId)
            }

            //??????????????????????????????????????????userId??????????????? ???????????????  ???????????????????????????????????????????????????????????????????????????????????????
            oldSeatInfo?.userId = newSeatInfo.userId
            oldSeatInfo?.isMute = newSeatInfo.mute

            if (newSeatInfo.status == STATUS_USED) {
                oldSeatInfo?.isUsed = true
            } else if (newSeatInfo.status == STATUS_UNUSED) {
                oldSeatInfo?.isUsed = false
            }

            if (newSeatInfo.closeStatus == STATUS_OPEN) {
                oldSeatInfo?.isClose = false
            } else if (newSeatInfo.closeStatus == STATUS_CLOSE) {
                oldSeatInfo?.isClose = true
            }

            //??????????????????????????????????????????????????????id?????????user???????????????
            if (TextUtils.isEmpty(newSeatInfo.userId) || newSeatInfo.userId != oldSeatInfo?.userId) {
                oldSeatInfo?.userName = ""
                oldSeatInfo?.userAvatar = ""

                val emptyUserInfo = VoiceRoomSeatEntity.UserInfo()


                emptyUserInfo.let {
                    /// ????????????????????????????????????
                    it.id = newSeatInfo.userId
                    /// ??????????????????????????????
                    it.userName = ""
                    /// ??????????????????????????????
                    it.userPicture = ""
                    /// ??????????????????????????????
                    it.userSex = -1
                    /// ?????????????????? ?????????????????? 1?????? 2????????? 3?????????
                    it.userKind = 0
                    /// ??????????????????????????????  0?????? 1?????? 2?????? 3?????? 4?????? 5?????? 6?????? 7??????
                    it.noble = ""
                    /// ?????????????????????????????????????????????
                    it.giftCoinCount = 0
                    /// ??????????????????????????????
                    it.headwear = ""
                }

                oldSeatInfo?.userInfo = emptyUserInfo
            }
        }

        //??????????????????
        mVoiceRoomSeatAdapter?.notifyDataSetChanged()
        setRoomGiftValue()

        //??????????????????user???userid?????????????????????????????????
        mTRTCVoiceRoom.getUserInfoList(userids) { code, msg, list ->
            //?????????????????????user???????????????????????????????????????????????????
            val map: MutableMap<String, TRTCVoiceRoomDef.UserInfo> = HashMap()

            list?.forEach {
                map[it.userId] = it
            }

            //?????????????????????????????????????????????????????????????????????user?????????????????????????????????????????????user?????????userinfo
            for (i in seatInfoList.indices) {

                val newSeatInfo = seatInfoList[i]

                val userInfo = map[newSeatInfo.userId] ?: continue

                //????????????????????????
                if (i == 0) {
                    if (mMainSeatUserId?.userId == userInfo.userId) {
                        mMainSeatUserId?.userName = userInfo.userName
                        mMainSeatUserId?.userAvatar = userInfo.userAvatar
                        mMainMike?.setSeatResult(mMainSeatUserId)
                    }

                } else {
                    val seatEntity = mVoiceRoomSeatEntityList?.get(i - 1)
                    if (userInfo.userId == seatEntity?.userId) {

                        if (seatEntity?.userInfo == null) {
                            seatEntity?.userInfo = VoiceRoomSeatEntity.UserInfo()
                        }
                        seatEntity?.userName = userInfo.userName
                        seatEntity?.userAvatar = userInfo.userAvatar
                        seatEntity?.userInfo?.let {
                            seatEntity.userInfo.userPicture = userInfo.userAvatar
                            seatEntity.userInfo.id = userInfo.userId
                            seatEntity.userInfo.userName = userInfo.userName
                        }
                    }

                }

            }

            //?????????????????????????????? ??????????????????????????????????????????????????????
            if (mikesSeat.isNotEmpty() && !mIsSeatInitSuccess) {

                //?????????????????????
                if (mMainSeatUserId != null) {
                    val mikesBean = mikesSeat[mMainSeatUserId!!.userId]
                    mikesBean?.let {
                        mMainSeatUserId!!.userInfo.userSex = it.userSex
                        mMainSeatUserId!!.userInfo.noble = it.noble.toString()
                        mMainSeatUserId!!.userInfo.headwear = it.headwear
                        mMainSeatUserId!!.userInfo.giftCoinCount = it.giftCoinCount
                        mMainMike?.setSeatResult(mMainSeatUserId!!)
                    }


                }

                //????????????
                for (roomSeat in mVoiceRoomSeatEntityList!!) {
                    val mikesBean = mikesSeat[roomSeat.userId] ?: continue
                    roomSeat.userInfo.userSex = mikesBean.userSex
                    roomSeat.userInfo.noble = mikesBean.noble.toString()
                    roomSeat.userInfo.headwear = mikesBean.headwear
                    roomSeat.userInfo.giftCoinCount = mikesBean.giftCoinCount
                }

            }
            mIsSeatInitSuccess = true

            mRoomGiftView?.setSeatAdapter(mMainSeatUserId, mVoiceRoomSeatEntityList)
            mVoiceRoomSeatAdapter!!.notifyDataSetChanged()
            setRoomGiftValue()
        }


    }

    /**
     * ???????????????(????????????/??????????????????)
     * @param index ???????????????
     * @param user  ??????????????????
     */
    override fun onAnchorEnterSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {

        if (user?.userId == mSelfUserId) {
            isAnchorColse = getMySeatMute(index)
            mikeSwitch()
        }
        if (index != 0) {
            // ???????????????????????????
            showNotifyMsg(user!!.userName + "???" + index + "??????")
        }

    }


    protected fun getMySeatMute(index: Int): Boolean {
        return if (index == 0) {
            mMainSeatUserId!!.isMute
        } else {
            mVoiceRoomSeatEntityList?.get(index - 1)!!.isMute
        }
    }

    /**
     * ???????????????(????????????/??????????????????)
     * @param index ???????????????
     * @param user  ??????????????????
     */
    override fun onAnchorLeaveSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {
        //????????????????????????????????????????????????
        if (user?.userId == mSelfUserId) {
//            isMaikStatus = true
//            mikeSwitch()
            closeMike()
        }
//        mVoiceRoomSeatEntityList.

//        if (index != 0) {
//            // ???????????????????????????
//            showNotifyMsg(user!!.userName + "???" + index + "??????")
//        }

    }

    /**
     * ????????????
     * @param index  ???????????????
     * @param isMute ????????????
     */
    override fun onSeatMute(index: Int, isMute: Boolean) {

        if (isMute) {
            showNotifyMsg(index.toString() + "???????????????")
        } else {
            showNotifyMsg(index.toString() + "??????????????????")
        }
    }

    /**
     * ????????????
     * @param index  ???????????????
     * @param isClose ??????????????????
     */
    override fun onSeatClose(index: Int, isClose: Boolean) {

        showNotifyMsg(if (isClose) "????????????" + index + "??????" else "????????????" + index + "??????")

    }

    /**
     * ??????????????????
     *
     * @param userInfo ?????????????????????
     */
    override fun onAudienceEnter(userInfo: TRTCVoiceRoomDef.UserInfo?) {

    }

    /**
     * ??????????????????
     *
     * @param userInfo ?????????????????????
     */
    override fun onAudienceExit(userInfo: TRTCVoiceRoomDef.UserInfo?) {
        showNotifyMsg(userInfo!!.userName + "??????")
    }

    /**
     * ???????????????????????????
     *
     * @param userId ?????? ID
     * @param volume ???????????? 0-100
     */
    override fun onUserVolumeUpdate(userId: String?, volume: Int) {
        if (volume >= 30) {
            var userSeatIndex = getUserSeatIndex(userId)
            setSeatWave(userSeatIndex)
        }

    }

    /**
     * ??????????????????????????????????????????????????????
     */
    private fun setSeatWave(index: Int) {
        if (index != -1) {
            if (index == 0) {
                val noble = mMainSeatUserId?.userInfo?.noble
                var nobleMave = 0
                if (!TextUtils.isEmpty(noble)) {
                    nobleMave = UserManagerUtils.getNobleMave(Integer.valueOf(noble))
                } else {
                    nobleMave = UserManagerUtils.getNobleMave(0)
                }
                mMainMike?.setWave(nobleMave)
            } else {
                val mWave = mWaveView?.get(index - 1)

                val voiceRoomSeatEntity = mVoiceRoomSeatEntityList?.get(index - 1)

                val noble = voiceRoomSeatEntity?.userInfo?.noble
                var nobleMave = 0
                if (!TextUtils.isEmpty(noble)) {
                    nobleMave = UserManagerUtils.getNobleMave(Integer.valueOf(noble))
                } else {
                    nobleMave = UserManagerUtils.getNobleMave(0)
                }

                mWave?.setColor(ContextCompat.getColor(this, nobleMave))
                mWave?.setMaxRadius(ScreenUtils.dp2px(this, 40f).toFloat())
                mWave?.start()
            }
        }
    }


    /**
     * ??????userId??????????????????
     */
    protected fun getUserSeatIndex(userId: String?): Int {
        if (userId == mMainSeatUserId?.userId) {
            return 0
        } else {
            var index = -1
            for (i in mVoiceRoomSeatEntityList!!.indices) {
                if (userId == mVoiceRoomSeatEntityList!![i].userId) {
                    index = i
                    break
                }
            }

            return if (index != -1) index + 1 else return -1
        }
    }

    /**
     * ?????????????????????
     *
     * @param message ???????????????
     * @param userInfo ????????????????????????
     */
    override fun onRecvRoomTextMsg(message: String?, userInfo: TRTCVoiceRoomDef.UserInfo?) {
    }

    /**
     * ????????????????????????
     *
     * @param cmd ???????????????????????????????????????????????????????????????????????????
     * @param message ???????????????
     * @param userInfo ????????????????????????
     */
    override fun onRecvRoomCustomMsg(cmd: String?, message: String?, userInfo: TRTCVoiceRoomDef.UserInfo?) {

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdOnseat) {
            mPresenter.customMsgCmdInRoom(message, mMainSeatUserId, mVoiceRoomSeatEntityList, mVoiceRoomSeatAdapter, mMainMike, giftValue == 1)
        }

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdInRoom) {

            if (mCostraint == null) return

            val userInfo = Gson().fromJson(message, VoiceRoomSeatEntity.UserInfo::class.java)

            if (!TextUtils.isEmpty(userInfo?.car) && (userInfo.noble.toInt() < 6 || (userInfo.noble.toInt() >= 6 && userInfo.isInvisible == 0))) {
                addAnimation(userInfo.car)
            }


            if (!TextUtils.isEmpty(userInfo.noble) && userInfo.noble.toInt() > 0 && userInfo.isInvisible == 0) {

                val instance = EnterRoomAnimManager.getInstance()
                var enterRoomView = EnterRoomView(this)
                enterRoomView.setUserEnterRoomBg(userInfo)
                enterRoomView.id = View.generateViewId()
                instance.setInfo(this, mCostraint!!)
                instance.addAnimation(enterRoomView)
            }

            if (userInfo.noble.toInt() < 6 || (userInfo.noble.toInt() >= 6 && userInfo.isInvisible == 0)) {
                var msgBody = MsgBody()
                msgBody.type = MsgBody.MSG_IN_ROOM
                msgBody.userInfo = userInfo
                showNotifyMsg(msgBody)
            }
        }

        //????????????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdText) {
            val userInfo = Gson().fromJson(message, VoiceRoomSeatEntity.UserInfo::class.java)
            var msgBody = MsgBody()
            msgBody.type = MsgBody.MSG_TALK
            msgBody.userInfo = userInfo
            msgBody.message = userInfo.message
            showNotifyMsg(msgBody)
        }

        //emoji????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdEmoji) {
            val faceMsg = Gson().fromJson(message, FaceMsg::class.java)
            if (faceMsg?.seatIndex == 0) {
                mMainMike?.setSvga(faceMsg.phiz, svgaParser)
            } else {
                setSvga(mFaceView?.get(faceMsg.seatIndex - 1), faceMsg.phiz)
            }
        }

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdGift) {
            val mSendGift = Gson().fromJson(message, SendGift::class.java)

            val userIds = mSendGift.getUserIds

            val userList = userIds?.split(",")

            for (userid in userList!!) {
                val giftValue = getGiftValue(mSendGift?.giftPriceType!!, mSendGift?.giftPrice!!, mSendGift.giftNum!!)

                if (mMainSeatUserId?.userId == userid) {

                    val giftCoinCount = mMainSeatUserId?.userInfo?.giftCoinCount
                    mMainSeatUserId?.userInfo?.giftCoinCount = giftValue + giftCoinCount!!
                    mMainMike?.setSeatResult(mMainSeatUserId)

                }


                for (seatEntity in mVoiceRoomSeatEntityList!!) {

                    if (seatEntity.userId == userid) {
                        val giftCoinCount = seatEntity.userInfo.giftCoinCount
                        seatEntity.userInfo.giftCoinCount = giftValue + giftCoinCount!!
                    }

                }


            }

//            mVoiceRoomSeatAdapter?.notifyDataSetChanged()


            var msgBody = MsgBody()
            msgBody.type = MsgBody.MSG_SEND_GIFT
            msgBody.sendGift = mSendGift
            showNotifyMsg(msgBody)

            if (mSendGift?.giftShowType == 1) {
                val animationView = getAnimationView(userIds.toString(), mSendGift.giftShowImage)
                startAnimation(animationView)
            } else if (mSendGift?.giftShowType == 2) {
                mSvgaManager?.addAnimation(mSendGift.giftShowImage)
//                mVoiceRoomSeatAdapter?.notifyDataSetChanged()
            }

            setRoomGiftValue()


        }

        //??????????????????????????????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdTitleChange) {

            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)

            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdTitleChange, mRoomSettingEntity)
        }

        //?????????????????????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdGiftShowChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdGiftShowChange, mRoomSettingEntity)
        }

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdPublicChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdPublicChange, mRoomSettingEntity)
        }

        //???????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdNameChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdNameChange, mRoomSettingEntity)
        }

        //????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdPwdChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdPwdChange, mRoomSettingEntity)
        }

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdTypeChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdTypeChange, mRoomSettingEntity)
        }

        //????????????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdRoomBgViewChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdRoomBgViewChange, mRoomSettingEntity)
        }

        //???????????????????????????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdSetManager) {
            val mRoomManager = Gson().fromJson(message, RoomManagerMsgEntity::class.java)
            setUserKind(CustomMsgConstant.CustomMsgCmdSetManager, mRoomManager)
        }

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdKickUser) {

            var bean = BackstageRoom(mRoomId!!, mRoomInfo?.coverUrl!!)

            EventBusUtil.sendEvent(Event<BackstageRoom>(EventConstant.OUT_ROOM, bean))

            val mRoomManager = Gson().fromJson(message, RoomManagerMsgEntity::class.java)
            if (mRoomManager.userId == mSelfUserInfo?.id) {
                mTRTCVoiceRoom.exitRoom { code, msg ->
                    if (code == 0) {
                        mPresenter.exitRoom(mRoomId)
                    }
                }
            }
        }

        //???????????????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdClearGiftValue) {
            var mRoomManager = Gson().fromJson(message, RoomManagerMsgEntity::class.java)
//            if (mRoomManager.userId == mSelfUserInfo?.id) {
            var userSeatInfo = getUserSeatInfo(mRoomManager.userId!!)

            if (userSeatInfo.isSeat!! && userSeatInfo.index != -1) {
                if (userSeatInfo.index == 0) {
                    mMainSeatUserId?.userInfo?.giftCoinCount = 0
                    mMainMike?.setSeatResult(mMainSeatUserId)
                } else {
                    mVoiceRoomSeatEntityList!![userSeatInfo.index!! - 1].userInfo?.giftCoinCount = 0
//                    mVoiceRoomSeatAdapter?.notifyDataSetChanged()
                    setRoomGiftValue()
                }
            }
//            }

        }

        //?????????????????????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdExclusionChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdExclusionChange, mRoomSettingEntity)
        }

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdGlobalMesssage) {
            var globalEntity = Gson().fromJson(message, GlobalMsgResult::class.java)
            var createNotice = createNotice(globalEntity.type, globalEntity)
            if (createNotice != null) {

                if (globalEntity.type == 1) {
                    var newManager = NewRoomAnimatorManager.getInstance()
                    newManager.setInfo(this, mCostraint)
                    newManager.addAnimation(createNotice)
                } else {
                    var manager = RoomAnimatorManager.getInstance()
                    manager.setInfo(this, mCostraint!!)
                    manager.addAnimation(createNotice)
                }


            }
        }

        //??????????????????
        if (cmd == CustomMsgConstant.CustomMsgCmdLotteryResult) {

            val lotteryEntity = Gson().fromJson(message, LotteryEntity::class.java)
            val msgBody = MsgBody()
            msgBody.type = MsgBody.MSG_LOTTERY
            msgBody.lottery = lotteryEntity
            showNotifyMsg(msgBody)
        }

    }

    protected fun createNotice(type: Int, result: GlobalMsgResult?): View? {

        var mView: View? = null
        if (type == 1) {
//            val roomLotteryView = RoomLotteryView(this)
            val roomLotteryView = NewRoomLotteryView(this)
            roomLotteryView.setViewType(type)
            roomLotteryView.id = View.generateViewId()
            roomLotteryView.setNoticeResult(result)
            mView = roomLotteryView
        } else if (type == 2) {

            val roomNoticeView = RoomNoticeView(this)
            roomNoticeView.setViewType(type)
            roomNoticeView.id = View.generateViewId()
            roomNoticeView.setNoticeResult(result)

            roomNoticeView.setOnClickListener { _ ->
//                ToastUtils.showLong("????????????")
                if (result?.roomId != mRoomId) {
                    onEnterRoom(result?.roomId)
                }
            }
            mView = roomNoticeView
        }

        return mView
    }


    protected open fun receiveRoomSetting(cmd: String?, roomSetting: RoomSettingEntity?) {

    }

    protected open fun setUserKind(cmd: String?, roomManager: RoomManagerMsgEntity?) {

    }

    protected fun addAnimation(animation: String) {
        mSvgaManager?.addAnimation(animation)
    }

    /**
     * ????????????????????????
     *
     * @param id  ??????id
     * @param inviter ?????????userId
     * @param cmd ????????????????????????
     * @param content ?????????????????????
     */
    override fun onReceiveNewInvitation(id: String?, inviter: String?, cmd: String?, content: String?) {
    }


    /**
     * ????????????????????????
     *
     * @param id  ??????id
     * @param invitee ????????????userId
     */
    override fun onInviteeAccepted(id: String?, invitee: String?) {
    }

    /**
     * ????????????????????????
     *
     * @param id  ??????id
     * @param invitee ????????????userId
     */
    override fun onInviteeRejected(id: String?, invitee: String?) {
    }

    /**
     * ?????????????????????
     *
     * @param id  ??????id
     * @param inviter ?????????userId
     */
    override fun onInvitationCancelled(id: String?, inviter: String?) {
    }

    override fun onDestroy() {
        mContext = null
        super.onDestroy()
    }

    /**
     * ????????????????????????
     * @param userInfo  ????????????
     */
    override fun userOnSuccess(userInfo: VoiceRoomSeatEntity.UserInfo?) {


    }


    /**
     * ???????????????
     * @param status ?????? 1 ?????????2???????????????????????????
     */
    override fun takeSeatSuccess(status: Int?, position: Int?) {

    }

    /**
     * ??????????????????????????????
     */
    override fun roomGiftSuccess(roomGift: GiftAllResult?) {
        if (roomGift != null) {
            mRoomGiftView = RoomGiftView.newInstance(roomGift, mRoomId, mSelfUserInfo?.noble)
            mRoomGiftView?.setOnGiftSendCallback(this)
        }
    }

    override fun onFollow(status: String?) {
        if (status == "1") {
            ToastUtils.showShort("????????????")
        } else {
            ToastUtils.showShort("????????????")
        }
    }

    override fun onUnFollow(status: String?) {
        if (status == "1") {
            ToastUtils.showShort("??????????????????")
        } else {
            ToastUtils.showShort("??????????????????")
        }
    }

    override fun onAddManager(status: Int?, userId: String?) {
        if (status == 1) {

            var rommManager = RoomManagerMsgEntity(2, userId)
            val managerStr = Gson().toJson(rommManager)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdSetManager, managerStr) { code, msg ->
                if (code == 0) {
                    ToastUtils.showShort("?????????????????????")
                }
            }

        } else {
            ToastUtils.showShort("?????????????????????")
        }
    }

    override fun onRemoveManager(status: Int?, userId: String?) {
        if (status == 1) {

            var rommManager = RoomManagerMsgEntity(3, userId)
            val managerStr = Gson().toJson(rommManager)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdSetManager, managerStr) { code, msg ->
                if (code == 0) {
                    ToastUtils.showShort("?????????????????????")
                }
            }
        } else {
            ToastUtils.showShort("?????????????????????")
        }
    }

    override fun onExitRoomSuccess(status: Int?, roomId: String?) {
        if (status == 1) {
            finish()
        }
    }

    override fun onAddAndRemoveBlackSuccess(status: String?, resultStatus: Int?, roomId: String?, userId: String?) {
        if (status == "1") {
            if (resultStatus == 1) {
                var map = HashMap<String, String>()
                map["userId"] = userId!!
                val outRoom = Gson().toJson(map)
                mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdKickUser, outRoom) { code, msg ->
                    if (code == 0) {
                        ToastUtils.showShort("????????????")
                    }
                }
            } else {
                ToastUtils.showShort("?????????????????????")
            }
        } else if (status == "2") {
            if (resultStatus == 1) {
                ToastUtils.showShort("?????????????????????")
            } else {
                ToastUtils.showShort("?????????????????????")
            }
        }
    }

    /**
     * ???????????????????????????
     */
    override fun onClearGiftSuccess(status: Int?, userId: String?) {
        if (status == 1) {
            if (giftValue == 1) {


                var map = HashMap<String, String>()
                map["userId"] = userId!!
                val clearValue = Gson().toJson(map)

                mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdClearGiftValue, clearValue) { code, msg ->
                    if (code == 0) {
                        val userSeatInfo = getUserSeatInfo(userId)
                        if (userSeatInfo.isSeat!! && userSeatInfo.index != -1) {
                            if (userSeatInfo.index == 0) {
                                mMainSeatUserId?.userInfo?.giftCoinCount = 0
                                mMainMike?.setSeatResult(mMainSeatUserId)
                            } else {
                                mVoiceRoomSeatEntityList!![userSeatInfo.index!! - 1].userInfo?.giftCoinCount = 0
//                                mVoiceRoomSeatAdapter?.notifyDataSetChanged()
                                setRoomGiftValue()
                            }
                        }
                    }
                }
            }


        }

    }

    override fun onConfigSuccess(status: String?) {
        if (status == "1"){
            mIvLottery?.visibility = View.VISIBLE
        } else if (status == "2") {
            mIvLottery?.visibility = View.GONE
        }
    }


    override fun onError(msg: String?) {

    }

    override fun onDialogDismiss() {
        isFaceViewUp = false
    }

    /**
     * ??????????????????
     */
    override fun onFaceClickResult(face: RoomFace?, isNobleFace: Boolean?, noble: String?) {

    }

    /**
     * ????????????ImageView
     */
    private fun getImageView(view: ViewGroup): MutableList<SVGAImageView> {
        val allchildren: MutableList<SVGAImageView> = ArrayList()
        if (view is ViewGroup) {
            val vp: ViewGroup = view as ViewGroup
            for (i in 0 until vp.childCount) {
                val viewchild: View = vp.getChildAt(i)
                if (viewchild is SVGAImageView) {
                    allchildren.add(viewchild)
                }
                if (viewchild is ViewGroup) {
                    allchildren.addAll(getImageView(viewchild))
                }
            }
        }
        return allchildren
    }

    /**
     * ????????????getWaveView
     */
    private fun getWaveView(view: ViewGroup): MutableList<WaveView> {
        val allchildren: MutableList<WaveView> = ArrayList()
        if (view is ViewGroup) {
            val vp: ViewGroup = view as ViewGroup
            for (i in 0 until vp.childCount) {
                val viewchild: View = vp.getChildAt(i)
                if (viewchild is WaveView) {
                    allchildren.add(viewchild)
                }
                if (viewchild is ViewGroup) {
                    allchildren.addAll(getWaveView(viewchild))
                }
            }
        }
        return allchildren
    }

    private fun getValueView(view: ViewGroup): MutableList<GiftValueView> {

        val allchildren: MutableList<GiftValueView> = ArrayList()
        if (view is ViewGroup) {
            val vp: ViewGroup = view as ViewGroup
            for (i in 0 until vp.childCount) {
                val viewchild: View = vp.getChildAt(i)
                if (viewchild is GiftValueView) {
                    allchildren.add(viewchild)
                }
                if (viewchild is ViewGroup) {
                    allchildren.addAll(getValueView(viewchild))
                }
            }
        }
        return allchildren
    }


    /**
     * ??????????????????
     */
    protected fun setSvga(mSvagFace: SVGAImageView?, path: String?) {

        var path = "vip_svga/$path.svga"

        svgaParser.init(this)

        svgaParser.decodeFromAssets(path, object : SVGAParser.ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                mSvagFace?.stopAnimation()
                mSvagFace?.setVideoItem(videoItem)
                mSvagFace?.stepToFrame(0, true)
            }

            override fun onError() {

            }
        })
    }

    /**
     * ??????????????????
     */
    override fun onSendGiftResult(status: Int?, users: MutableList<GiftViewSeat>?, giftResult: RoomGiftResult?, giftNum: Int?, isTotal: Boolean?) {

    }

    /**
     * ????????????????????????
     */
    override fun onNobleClick() {
        ActStartUtils.startAct(this, NobleWebActivity::class.java)
    }


    /**
     * ?????????????????????????????? - ????????????
     */
    override fun onTxSwitch(isOpenTx: Boolean?) {
//        mRoomSpecial = isOpenTx!!
//        SPUtils.getInstance().put(SpConstant.ROOM_IS_PLAY, mRoomSpecial)
//        mSvgaManager?.setRoomsPecial(mRoomSpecial)
//        setRoomSpecialIcon(mRoomSpecial)
        setGiftSeitch(isOpenTx)
    }

    fun setGiftSeitch(switch: Boolean?) {
        mRoomSpecial = switch!!
        SPUtils.getInstance().put(SpConstant.ROOM_IS_PLAY, mRoomSpecial)
        mSvgaManager?.setRoomsPecial(mRoomSpecial)
        setRoomSpecialIcon(mRoomSpecial)
    }

    /**
     * ?????????????????????????????? - ????????????
     */
    override fun onGpSwitch(isOpenGp: Boolean?) {

        mPresenter?.setGpSwitch(mRoomId, if (isOpenGp!!) 1 else 0, TRTCVoiceRoomCallback.ActionCallback { code, msg ->

            if (code == 1) {//??????

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

            } else {//??????
                ToastUtils.showShort("????????????")
            }
        })
    }

    /**
     * ?????????????????????????????? - ???????????????
     */
    override fun onLwzSwitch(isOpenLwz: Boolean?) {


        mPresenter?.setGiftSwitch(mRoomId, if (isOpenLwz!!) "1" else "0", TRTCVoiceRoomCallback.ActionCallback { code, msg ->
            if (code == 1) {

                var mRoomSettingEntity = RoomSettingEntity(null, null, null,
                        null, null, null, null, null,
                        null, null, null, null,
                        null, null, null, null, isOpenLwz,
                        null, null, null, null, null)

                val mRoonSettingMsg = Gson().toJson(mRoomSettingEntity)

                mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdGiftShowChange, mRoonSettingMsg) { code, msg ->
                    if (code == 0) {
                        giftValue = if (isOpenLwz!!) 1 else 0
                        mRoomInfo?.giftValue = giftValue

                        for (i in mVoiceRoomSeatEntityList!!.indices) {
                            var userInfo = mVoiceRoomSeatEntityList!![i].userInfo
                            if (userInfo != null) {
                                userInfo.giftCoinCount = 0
                            }
                        }

                        setIsOpenGiftValue(giftValue)

                    }
                }


            } else {
                ToastUtils.showShort("????????????")
            }
        })


    }

    /**
     * ?????????????????????????????? - ????????????
     */
    override fun onSetting() {
        val bundle = Bundle()
        bundle.putSerializable(Constant.ROOM_INFO, mRoomInfo)
        ActStartUtils.startAct(this, RoomSettingAct::class.java, bundle)
    }

    protected fun setIsOpenGiftValue(isOpenLwz: Int) {
        //?????????????????????
//        mVoiceRoomSeatAdapter?.setGiftValue(isOpenLwz == 1)
        switchGiftValue(isOpenLwz == 1)

        //????????????????????????
        mMainMike?.setSeatGiftValue(isOpenLwz == 1)
    }

    /**
     * ???????????????
     */
    private fun switchGiftValue(isOpenGp: Boolean) {
        if (isOpenGp) {


            for (i in mVoiceRoomSeatEntityList!!.indices) {

                var giftValueView = mGiftValue?.get(i)
                giftValueView?.setGiftValue(mVoiceRoomSeatEntityList!![i].userInfo)

            }
            mGiftValueView?.visibility = View.VISIBLE
        } else {
            for (i in mVoiceRoomSeatEntityList!!.indices) {
                var userInfo = mVoiceRoomSeatEntityList!![i].userInfo
                if (userInfo != null) {
                    userInfo.giftCoinCount = 0
                }

                var giftValueView = mGiftValue?.get(i)
                giftValueView?.clearValue()

            }
            mGiftValueView?.visibility = View.GONE
        }

    }

    protected fun setRoomGiftValue() {

//        var userInfo = mVoiceRoomSeatEntityList?.get(position)?.userInfo
//
//        var giftValueView = mGiftValue?.get(position)
//        giftValueView?.setGiftValue(userInfo)

        for (i in mVoiceRoomSeatEntityList!!.indices) {
            var giftValueView = mGiftValue?.get(i)
            giftValueView?.setGiftValue(mVoiceRoomSeatEntityList!![i].userInfo)

        }
    }


    /**
     * ??????????????????????????????
     */
    protected fun isOpenGp(sendGpMsg: Boolean?, isOpenGp: Boolean?) {
        if (isOpenGp == null) return
        if (sendGpMsg!!) {
            if (!isOpenGp) {
                mMsgEntityList?.clear()
            }

            var msgEntity = MsgBody()
            msgEntity.type = MsgBody.MSG_PUBLIC_SETTING
            msgEntity.roomPublic = isOpenGp

            mMsgEntityList!!.add(msgEntity)
            mMsgListAdapter!!.notifyDataSetChanged()
            mRvImMsg?.smoothScrollToPosition(mMsgListAdapter!!.itemCount)
        }
    }

    /**
     * ?????????????????????????????????
     */
    protected fun setRoomLockIcon(roomLock: Int?) {
        if (roomLock == 1) {
            mIvLock?.visibility = View.VISIBLE
        } else {
            mIvLock?.visibility = View.GONE
        }
    }

    /**
     * ??????????????????????????????
     */
    protected fun setRoomSpecialIcon(roomSpecial: Boolean) {

        if (roomSpecial) {
            mIvRoomCloseTx?.visibility = View.GONE
        } else {
            mIvRoomCloseTx?.visibility = View.VISIBLE
        }
    }


    /**
     * ????????????dialog??????
     */
    override fun onSettingDismiss() {
        isSetting = false
    }

    /**
     * ????????? ?????????????????????
     */
    override fun onEnterRoom(roomId: String?) {
        if (roomId != mRoomId) {

            mTRTCVoiceRoom.exitRoom { code, msg ->
                mPresenter.exitRoom(mRoomId)
                EventBusUtil.sendEvent(Event(EventConstant.ENTER_HI_CHAT_ROOM, roomId))
                finish()
            }
        }

    }

    /**
     * ?????????????????????
     */
    override fun onFinishRoom() {
        onBackPressed()
    }

    /**
     * ??????????????????
     */
    override fun onMinimize() {
        minimize()
    }

    /**
     * ???????????????
     */
    override fun onReport() {
        var bundle = Bundle()
        bundle.putString(Constant.REPOTR_TYPE, Constant.REPORT_TYPE_ROOM)
        bundle.putString(Constant.REPORT_ID, mRoomId)
        ActStartUtils.startAct(this, ReportAct::class.java, bundle)
    }

    /**
     * ?????????????????????
     */
    override fun onCounDismiss() {
        isControl = false
    }

    /**
     * ?????????????????????
     */
    override fun onDismiss() {
        isRoomRank = false
    }


    /**
     * ????????????????????????
     */
    override fun onGiftDismiss() {
        isGift = false
    }

    /**
     * ???????????????
     */
    protected fun getGiftValue(giftType: Int, giftPrice: Int, giftNum: Int): Int {

        if (giftType == 1) return 0

        return giftPrice * giftNum
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    protected fun getUserSeatInfo(userId: String): RoomSeat {
        if (!TextUtils.isEmpty(mMainSeatUserId?.userId) && mMainSeatUserId?.userId == userId) {
            return RoomSeat(true, 0, mMainSeatUserId?.isUsed, mMainSeatUserId?.isClose, mMainSeatUserId?.isMute)
        } else {
            val roomSeat = RoomSeat(false, -1, isUsed = false, isClose = false, isMute = false)
            for (i in mVoiceRoomSeatEntityList!!.indices) {
                if (userId == mVoiceRoomSeatEntityList!![i].userId) {
                    roomSeat.isSeat = true
                    roomSeat.index = i + 1
                    roomSeat.isUsed = mVoiceRoomSeatEntityList!![i].isUsed
                    roomSeat.isClose = mVoiceRoomSeatEntityList!![i].isClose
                    roomSeat.isMute = mVoiceRoomSeatEntityList!![i].isMute
                    break
                }
            }

            return roomSeat
        }

    }

    /**
     * ??????????????????????????????
     *
     * @param status ????????? ??????????????????
     * @param userInfo  ??????????????????
     * @param userSeatInfo ????????????????????????
     */
    override fun onSeatItemClick(status: Int, userInfo: VoiceRoomSeatEntity.UserInfo?, userSeatInfo: RoomSeat?) {
        when (status) {
            RoomSeatConstant.SEND_GIFT -> {     //?????????
                ToastUtils.showShort("?????????")
                if (!isGift) {
                    isGift = true
                    if (mRoomGiftView != null) {

                        val giftViewSeat = GiftViewSeat(userInfo?.id, userInfo?.userName, userInfo?.userPicture, userInfo?.noble, userSeatInfo?.index, false, isRoom = false, isSelect = false, isSeat = true)

                        mRoomGiftView?.setGiveAway(giftViewSeat, mMainSeatUserId, mVoiceRoomSeatEntityList)
                        mRoomGiftView?.show(supportFragmentManager, TAG)
                    }
                }
            }

            RoomSeatConstant.PRIVATE_MSG -> {        //??????

                val chatInfo = ChatInfo()
                chatInfo.type = V2TIMConversation.V2TIM_C2C
                chatInfo.id = userInfo?.id
                chatInfo.chatName = userInfo?.userName

                var iconUrlList: MutableList<Any> = ArrayList()
                iconUrlList.add(userInfo?.userPicture!!)

                chatInfo.setIconUrlList(iconUrlList)

                val intent = Intent(this, SpeakAct::class.java)
                intent.putExtra(Constant.CHAT_INFO, chatInfo)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            RoomSeatConstant.SEND_DRESS -> {         //?????????
                val bundle = Bundle()
                bundle.putString(Constant.USER_ID, userInfo?.id)
                ActStartUtils.startAct(this, HeadgearAct::class.java, bundle)

            }

            RoomSeatConstant.ATTENTION -> {     //??????
                mPresenter.follow(userInfo?.id, "1")
            }

            RoomSeatConstant.UNSUBSCRIBE -> {       //????????????
                mPresenter.follow(userInfo?.id, "0")
            }

            RoomSeatConstant.SET_MANAGER -> {        //???????????????
                mPresenter.addManager(mRoomId, userInfo?.id)

            }

            RoomSeatConstant.CANCEL_MANAGER -> {         //???????????????
                mPresenter.removeManager(mRoomId, userInfo?.id)
            }

            RoomSeatConstant.OUT_ROOM -> {      //????????????
                var map = HashMap<String, String>()
                map["userId"] = userInfo?.id!!
                val outRoom = Gson().toJson(map)
                mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdKickUser, outRoom) { code, msg ->
                    if (code == 0) {
                        ToastUtils.showShort("????????????")
                    }
                }

            }

            RoomSeatConstant.ADD_BLACK_LIST -> {        //???????????????
                mPresenter.addBlackList("1", mRoomId, userInfo?.id)

            }

            RoomSeatConstant.REMOVE_BLACK_LIST -> {     //???????????????
                mPresenter.removeBlackList("1", mRoomId, userInfo?.id)
            }

            RoomSeatConstant.OPEN_MIKE -> {       //???????????????
                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.muteSeat(userSeatInfo?.index!!, false) { code, msg ->
                        if (code == 0) {
                            ToastUtils.showShort("???????????????")
                        }
                    }
                }

            }

            RoomSeatConstant.CLOSE_MIKE -> {        //???????????????
                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.muteSeat(userSeatInfo?.index!!, true) { code, msg ->
                        if (code == 0) {
                            ToastUtils.showShort("???????????????")
                        }
                    }
                }
            }

            RoomSeatConstant.PICK_UP -> {       //?????????
                if (userSeatInfo?.index == -1) {
                    val pickIndex = getPickIndex()
                    if (pickIndex != -1) {
                        mTRTCVoiceRoom.pickSeat(pickIndex, userInfo?.id, null)
                    } else {
                        ToastUtils.showShort("????????????")
                    }
                }

            }

            RoomSeatConstant.PICK_DOWN -> {     //?????????

                val seat = getUserSeatInfo(userInfo?.id!!)
                if (seat.isSeat!!) {
                    mTRTCVoiceRoom.kickSeat(seat.index!!, null)
                }
            }

            RoomSeatConstant.LOCK_MIKE -> {      //??????

                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.closeSeat(userSeatInfo?.index!!, true, null)
                }


            }

            RoomSeatConstant.UNLOCK_MIKE -> {       //?????????
                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.closeSeat(userSeatInfo?.index!!, false, null)
                }
            }

            RoomSeatConstant.CLEAR_GIFT_VALUE -> {      //???????????????
                ToastUtils.showShort("???????????????")
                mPresenter.clearGiftValue(mRoomId, userInfo?.id)
            }

            RoomSeatConstant.WINDOWS_DISMISS -> {       //????????????
                isSeatWindows = false
            }


            RoomSeatConstant.UP_SEAT -> {           //??????
                startTakeSeat(userSeatInfo?.index!!)
            }

            RoomSeatConstant.PICK_SEAT -> {         //????????????
                var bundle = Bundle()
                bundle.putSerializable(Constant.ROOM_INFO, mRoomInfo)
                bundle.putInt(Constant.ROOM_MARK, Constant.ROOM_PICK)
                bundle.putInt(Constant.ROOM_SEAT_INDEX, userSeatInfo?.index!!)
                ActStartUtils.startForAct(this, OnLineAct::class.java, bundle, START_ON_LINE)
            }

            RoomSeatConstant.MANAGER_VIEW_DISMISS -> {      //Manager????????????
                isManagerWindows = false
            }

            RoomSeatConstant.REPORT_USER -> {
                var bundle = Bundle()
                bundle.putString(Constant.REPOTR_TYPE, Constant.REPORT_TYPE_USER)
                bundle.putString(Constant.REPORT_ID, userInfo?.id)
                ActStartUtils.startAct(this, ReportAct::class.java, bundle)
            }

        }
    }

    /**
     * ????????????
     */
    protected fun startTakeSeat(itemPos: Int) {

        if (!isUpSeat) {
            isUpSeat = true
            if (mCurrentRole == TRTCCloudDef.TRTCRoleAnchor) {

                mTRTCVoiceRoom.leaveSeat { code, msg ->
                    if (code == 0) {
                        mTRTCVoiceRoom.enterSeat(itemPos, null)
                    }

                }


            } else {
                mTRTCVoiceRoom.enterSeat(itemPos, null)
            }
        }
    }


    /**
     * ?????????????????????
     */
    protected fun getPickIndex(): Int {
        var index = -1
        for (i in MAX_SEAT_SIZE - 1 downTo 0) {
            if (i != 0) {
//                for (voiceRoomSeatEntity in mVoiceRoomSeatEntityList!!) {
                val used = mVoiceRoomSeatEntityList!![i - 1].isUsed
                if (used) {
                    continue
                } else {
                    index = i
                    break
                }
//                }
            } else {
                val used = mMainSeatUserId?.isUsed
                if (!used!!) {
                    index = i
                }
            }
        }

        return index
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //?????????????????????????????????
        if (requestCode == START_ON_LINE && resultCode == RESULT_OK) {
            var onLinePick: OnLinePick = data?.getSerializableExtra(Constant.ON_LINE_USER_INFO) as OnLinePick
            mTRTCVoiceRoom.pickSeat(onLinePick.seatIndex, onLinePick?.userId, null)
        }
    }

    override fun onRowMikeCallback(rowSeatResult: RowSeatResult?) {
        val pickIndex = getPickIndex()
        mTRTCVoiceRoom.pickSeat(pickIndex, rowSeatResult?.userId, null)
    }


    protected fun startAnimation(viewList: MutableList<PositionEntity>?) {
        if (viewList == null) return
        for (positionEntity in viewList!!) {
            playAnimation(positionEntity)
        }
    }


    fun getIndex(userId: String?): Int {

        var index = -1

        if (userId == mMainSeatUserId?.userId) {
            index = 0
        } else {
            for (i in mVoiceRoomSeatEntityList!!.indices) {
                if (mVoiceRoomSeatEntityList!![i].userId == userId) {
                    index = i + 1
                    break
                }
            }
        }

        return index
    }


    protected fun getAnimationView(users: String?, giftUrl: String?): MutableList<PositionEntity>? {

        if (mViewStart == null) return null

        val start = getOrigin(mViewStart)

        var mid = getOrigin(mViewEnd)

        val midX = (mid[0] - start[0]).toFloat()
        val midY = (mid[1] - start[1]).toFloat()

        var list: MutableList<PositionEntity> = ArrayList()

        val userList = users?.split(",")

        for (i in userList!!.indices) {


            val image = ImageView(this)
            image.id = View.generateViewId()
            image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            image.setImageResource(R.mipmap.icon_logo)

            GlideAppUtil.loadImage(this, giftUrl, image)

            val index = getIndex(userList[i])

            var positionEntity: PositionEntity? = null
            if (index == -1) {

                positionEntity = PositionEntity(image, midX, midY, null, null, false)

            } else if (index == 0) {
                val end = getOrigin(mMainMike!!)
                val endX = (end[0] - start[0]).toFloat() + 100
                val endY = (end[1] - start[1]).toFloat() + 100
                positionEntity = PositionEntity(image, midX, midY, endX, endY, true)

            } else {

                val endView = mFaceView!![index - 1]
                val end = getOrigin(endView)
                val endX = (end[0] - start[0]).toFloat() + 50
                val endY = (end[1] - start[1]).toFloat() + 50
                positionEntity = PositionEntity(image, midX, midY, endX, endY, true)
            }

            list.add(positionEntity!!)

        }

        return list
    }


    private fun getOrigin(view: View?): IntArray {
        val location = IntArray(2)
        view?.getLocationInWindow(location) //???????????????????????????????????????
        return location
    }

    private fun playAnimation(bean: PositionEntity) {

        if (mRoomSpecial) {


            val view = bean.image!!
            val constraintSet = ConstraintSet()
            constraintSet.constrainWidth(view.id, ScreenUtils.dp2px(this, 90f))
            constraintSet.constrainHeight(view.id, ScreenUtils.dp2px(this, 90f))
            constraintSet.setDimensionRatio(view.id, "h,1:1")
            constraintSet.connect(view.id, ConstraintSet.TOP, R.id.view_start, ConstraintSet.TOP)
            constraintSet.connect(view.id, ConstraintSet.START, R.id.view_start, ConstraintSet.START)
            constraintSet.connect(view.id, ConstraintSet.END, R.id.view_start, ConstraintSet.END)
            constraintSet.connect(view.id, ConstraintSet.BOTTOM, R.id.view_start, ConstraintSet.BOTTOM)

            mCostraint?.removeView(view)

            mCostraint?.addView(view)
            TransitionManager.beginDelayedTransition(mCostraint!!)
            constraintSet.applyTo(mCostraint!!)

            if (bean.isSeat!!) {
                ViewAnimator.animate(view)
                        .scale(1f, 1.1f, 1f)
                        .duration(400)
                        .thenAnimate(view)
                        .translationX(0f, bean.fromAxisX!!)
                        .translationY(0f, bean.fromAxisY!!)
                        .duration(1000)
                        .interpolator(AccelerateDecelerateInterpolator())
                        .andAnimate(view)
                        .scale(1.0f, 1.25f)
                        .duration(1000)
                        .thenAnimate(view)
                        .scale(1.25f, 1.25f)
                        .duration(200)
                        .thenAnimate(view)
                        .translationX(bean.fromAxisX!!, bean.toAxisX!! + 30)
                        .translationY(bean.fromAxisY!!, bean.toAxisY!! + 30)
                        .interpolator(AccelerateDecelerateInterpolator())
                        .duration(1000)
                        .andAnimate(view)
                        .scale(1.25f, 1.0f)
                        .duration(1000)
                        .thenAnimate(view)
                        .scale(1.0f, 1.0f)
                        .duration(200)
                        .thenAnimate(view)
                        .alpha(1.0f, 0f)
                        .duration(200)
                        .onStart {
                            view.visibility = View.VISIBLE
                        }
                        .onStop {
                            mCostraint?.removeView(view)
                        }
                        .start()
            } else {


                ViewAnimator.animate(view)
                        .scale(1f, 1.1f, 1f)
                        .duration(400)
                        .thenAnimate(view)
                        .translationX(0f, bean.fromAxisX!!)
                        .translationY(0f, bean.fromAxisY!!)
                        .duration(1000)
                        .interpolator(AccelerateDecelerateInterpolator())
                        .andAnimate(view)
                        .scale(1.0f, 1.25f)
                        .duration(1000)
                        .thenAnimate(view)
                        .scale(1.25f, 1.0f)
                        .duration(200)
                        .thenAnimate(view)
                        .alpha(1.0f, 0f)
                        .duration(200)
                        .onStart {
                            view.visibility = View.VISIBLE
                        }
                        .onStop {
                            mCostraint?.removeView(view)
                        }
                        .start()

            }
        }

    }

    protected fun minimize() {
        if (!TextUtils.isEmpty(mRoomId) && !TextUtils.isEmpty(mRoomInfo?.coverUrl)) {
            var backstageRoom = BackstageRoom(mRoomId!!, mRoomInfo?.coverUrl!!)
            EventBusUtil.sendEvent(Event<BackstageRoom>(EventConstant.BACKSTAGE_ROOM, backstageRoom))
            finish()
        }

    }


}
