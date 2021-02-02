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
    var mIvRoomBg: ImageView? = null //房间背景

    @JvmField
    @BindView(R.id.iv_back)
    var mIvBack: ImageView? = null  //返回按钮

    @JvmField
    @BindView(R.id.tv_title)
    var mTvTitle: TextView? = null   //房间名

    @JvmField
    @BindView(R.id.iv_lock)
    var mIvLock: ImageView? = null //房间锁定图标

    @JvmField
    @BindView(R.id.iv_close_tx)
    var mIvRoomCloseTx: ImageView? = null //关闭特效图标

    @JvmField
    @BindView(R.id.iv_lh)
    var mIvLh: ImageView? = null     //靓号标记

    @JvmField
    @BindView(R.id.tv_id_num)
    var mTvRoomId: TextView? = null  //房间ID

    @JvmField
    @BindView(R.id.tv_online_num)
    var mTvOnLineNum: TextView? = null //在线人数

    @JvmField
    @BindView(R.id.iv_room_shar)
    var mIvRoomShar: ImageView? = null //房间分享按钮

    @JvmField
    @BindView(R.id.iv_room_more)
    var mIvRoomMore: ImageView? = null //房间更多按钮

    @JvmField
    @BindView(R.id.main_mike)
    var mMainMike: MikeView? = null     //主麦位

    @JvmField
    @BindView(R.id.tv_room_rank)
    var mTvRoomRank: TextView? = null //房间榜按钮

    @JvmField
    @BindView(R.id.tv_room_type)
    var mTvRoomType: TextView? = null // 房间类型

    @JvmField
    @BindView(R.id.tv_bulletin)
    var mTvBulletin: TextView? = null //房间公告标题

    @JvmField
    @BindView(R.id.iv_edit)
    var mIvEdit: ImageView? = null //房间公告按钮

    @JvmField
    @BindView(R.id.rv_seat)
    var mRvSeat: RecyclerView? = null //座位列表

    @JvmField
    @BindView(R.id.iv_lottery)
    var mIvLottery: ImageView? = null

    @JvmField
    @BindView(R.id.rv_im_msg)
    var mRvImMsg: RecyclerView? = null //房间聊天列表

    @JvmField
    @BindView(R.id.group_audience)
    var mAudience: Group? = null     //下麦为观众时需要展示的按钮

    @JvmField
    @BindView(R.id.group_anchor)
    var mAnchor: Group? = null        //上麦为主播是需要展示的按钮

    @JvmField
    @BindView(R.id.iv_mike)
    var mIvMaike: ImageView? = null   //麦克风开关按钮

    @JvmField
    @BindView(R.id.iv_sound)
    var mIvSound: ImageView? = null   //房间声音开关

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
    var mViewStart: View? = null  //小动画开始坐标

    @JvmField
    @BindView(R.id.view_end)
    var mViewEnd: View? = null    //小动画中间结束坐标


    companion object {

        const val MAX_SEAT_SIZE = 9 //除主麦位最大麦位

        const val VOICEROOM_ROOM_ID = "room_id" //房间id

        const val VOICEROOM_ROOM_NAME = "room_name" //房间名

//        const val VOICEROOM_TITLE_COLOR = "room_title_color"    //公告颜色

//        const val VOICEROOM_USER_NAME = "user_name" //房主名

        const val VOICEROOM_USER_ID = "user_id" //房主id

        const val VOICEROOM_USER_SIG = "user_sig"

        const val VOICEROOM_NEED_REQUEST = "need_request" //上麦是否需要房主同意

        const val VOICEROOM_SEAT_COUNT = "seat_count"

        const val VOICEROOM_AUDIO_QUALITY = "audio_quality" //音质

        const val VOICEROOM_CREATE = "create_status" //是否为创建房间

        const val VOICEROOM_ISAGAIN = "voiceroom_isagain" //是否为最小化后再次进入房间

//        const val VOICEROOM_USER_AVATAR = "user_avatar" //房主头像

//        const val VOICEROOM_ROOM_COVER = "room_cover" //房间封面

        const val START_ON_LINE = 300

    }


    private val RESQUEST_OPEN = 100//请求开启麦克风

    private val RESQUEST_CLOSE = 200//请求关闭麦克风

    protected var mSelfUserId: String? = null //进房用户ID

    protected var mCurrentRole = 0//用户当前角色

    protected var mSeatUserSet: MutableSet<String>? = null//在座位上的主播集合

    protected val mTRTCVoiceRoom: TRTCVoiceRoom by lazy { TRTCVoiceRoom.sharedInstance(this) }//直播间管理类

    protected var mVoiceRoomSeatEntityList: MutableList<VoiceRoomSeatEntity>? = null //座位列表

    protected var mFaceView: MutableList<SVGAImageView>? = null

    protected var mWaveView: MutableList<WaveView>? = null

    protected var mGiftValue: MutableList<GiftValueView>? = null

    protected var mVoiceRoomSeatAdapter: VoiceLiveSeatAdapter? = null//座位Adapter

    protected var mMsgEntityList: MutableList<MsgBody>? = null//消息列表

    protected var mMsgListAdapter: MsgAdapter? = null//消息adapter

    protected val mConfirmDialogFragment by lazy { ConfirmDialogFragment() } //提示框dialog

    protected val mViewSelectMember by lazy { SelectMemberView(this) } //邀请上麦dialog

    protected val mInputTextMsgDialog by lazy { InputTextMsgDialog(this, R.style.TRTCVoiceRoomInputDialog) }    //文本输入dialog

    protected val mRoomFaceView: RoomFaceView by lazy { RoomFaceView.newInstance() }

    protected var mRoomRank: RoomRankView? = null

    protected val mControlPopupView by lazy { ControlPopupView.newInstance() }


    protected var mRoomId: String? = null//房间ID

    protected var mRoomName: String? = null//房间名

//    protected var mUserName: String? = null //房主用户名

//    protected var mUserAvatar: String? = null//房主头像url

//    protected var mRoomCover: String? = null //房间封面

    protected var mOwnerId: String? = null  //房主ID

    protected var mMainSeatUserId: VoiceRoomSeatEntity? = null//主麦位对象


//    protected var isAnchorColse = false //麦克风是不被房主或是管理员封禁，当麦克风被管理员关闭自己不能打开

//    protected var isMaikStatus = false //麦克风是否关闭 ，这里为自己操作麦克风的开关 默认麦克风关闭

    protected var isRoomMute = false //房间是否静音

    protected var mAudioQuality = 0 //音质

    protected var mSvgaManager: SVGAImageViewManage? = null //房间礼物动画管理器

    protected val svgaParser: SVGAParser by lazy { shareParser() }  //播放工具

    protected var isCreate = false

//    protected var mTitleColor: String? = null

//    protected val mRoomGift: RoomGiftResult? = null     //礼物接口返回数据

    protected var mRoomGiftView: RoomGiftView? = null

    protected var mRoomSpecial = true//房间礼物特效是否开启 true.开启 false.关闭

    protected var mRoomPublic = 0//房间公屏是否开启 1.开启 0.不开启

    protected var giftValue = 0//是否显示礼物值

    protected var mUserKind: Int = -1

    protected var mNeedRequest = false // 上麦是否需要同意 这里改为排麦模式

    protected var mRoomLock = 0 //房间是否上锁 1上锁 0未上锁


    //进房时从自己服务器获取的麦位上的成员键值队
    protected var mikesSeat: MutableMap<String, MikesBean> = HashMap()

    protected var mIsSeatInitSuccess = false        //座位是否准备好

    protected var mSelfUserInfo: VoiceRoomSeatEntity.UserInfo? = null   //自己的用户信息

    protected var mRoomInfo: CreateRoomResult? = null   //联网获取的房间信息

    protected val mRoomSetting: RoomSettingView by lazy { RoomSettingView.newInstance() }//房间设置弹出dialog

    private var isFaceViewUp: Boolean = false   //表情选择是否弹出

    private var isRoomRank: Boolean = false //房间榜是否弹出

    protected var isGift: Boolean = false //礼物弹窗是否弹出

    private var isSetting: Boolean = false //设置弹窗是否弹出

    protected var isSeatWindows: Boolean = false    //座位弹窗是否弹出

    protected var isManagerWindows: Boolean = false  //管理员点击麦位弹窗

    protected var isUpSeat: Boolean = false         //是否处于上麦状态

    protected var isControl: Boolean = false     //右上角更多按钮是否开启

    protected var mHiChatRoomId: String? = null     //嗨聊房ID

    protected var isAgain: Boolean = false       //是否为最小化后再次进入

    protected var mContext: Context? = null


    //是否开启全屏
    override fun isFull(): Boolean = true

    //是否接受广播
    override fun isRegisterEventBus(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this
//        // 应用运行时，保持不锁屏、全屏化
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
     * 初始化控件
     */
    protected fun initView() {

        mRoomSpecial = SPUtils.getInstance().getBoolean(SpConstant.ROOM_IS_PLAY, true)

        setRoomSpecialIcon(mRoomSpecial)

        //初始化输入框dialog
        mInputTextMsgDialog.setmOnTextSendListener(this)
        mSvgaManager = SVGAImageViewManage(this, mRoomSpecial, mSvga)
        mRoomFaceView.setCallback(this)

        //初始化消息列表
        mMsgEntityList = ArrayList()

        //初始化消息列表adapter
        mMsgListAdapter = MsgAdapter(this, mMsgEntityList, this)

        mRvImMsg?.layoutManager = LinearLayoutManager(this)
        mRvImMsg?.adapter = mMsgListAdapter

        //初始化房间座位列表
        mVoiceRoomSeatEntityList = ArrayList()


        for (i in 0 until MAX_SEAT_SIZE - 1) {
            val voiceRoomSeatEntity = VoiceRoomSeatEntity()
            voiceRoomSeatEntity.userInfo = VoiceRoomSeatEntity.UserInfo()
            mVoiceRoomSeatEntityList?.add(voiceRoomSeatEntity)
        }

        //Gridlayout 管理器初始化
        val gridLayoutManager = GridLayoutManager(this, 4)
        //初始化房间座位Adapter
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
     * 初始化数据
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
     * 判断是否为主播，有操作按钮的权限
     *
     * @return 是否有权限
     */
    protected open fun checkButtonPermission(): Boolean {
        val hasPermission = mCurrentRole == TRTCCloudDef.TRTCRoleAnchor
        if (!hasPermission) {
            ToastUtils.showLong("主播才能操作哦")
        }
        return hasPermission
    }

    /**
     * 根据是否为主播显示屏幕下方按钮
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
     * 麦克风开关
     */
    protected open fun mikeSwitch() {

        //优先判断是否被主播封麦，麦克风图标只会在上麦时出现
        if (isAnchorColse) {
            //如果麦克风被封禁这里更换麦克风被封禁图片
            mIvMaike?.setImageResource(R.mipmap.icon_mike_close)

            closeMike()

        } else {
            //如果主播没有封禁麦位再次判断麦克风状态 true 麦克风封禁状态 false 打开麦克风
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
     * 显示输入Dialog
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
     * 发送消息
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
                    ToastUtils.showShort("管理员已关闭聊天公屏")
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
     * 主麦位点击
     */
    protected open fun mainSeatOnClick() {

    }


    /**
     * 是否静音
     *      isMute: true 静音 false 不静音
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
     * 弹幕
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
     *添加弹幕
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
     * 刷新消息列表
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


    //    然后再onRequestPermissionsResult中进行判断操作：
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {


        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                permissions[0] == Manifest.permission.RECORD_AUDIO) {

            if (requestCode == RESQUEST_OPEN) {
                openMike()
            } else if (requestCode == RESQUEST_CLOSE) {
                closeMike()
            }
        } else {
            ToastUtils.showShort("请开放麦克风权限！")
        }

    }


    /**
     * 开启麦克风
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
     * 关闭麦克风
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
     * 消息点击
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
     * 座位上点击按钮的反馈
     *
     * @param position
     */
    override fun onItemClick(position: Int) {

    }

    /**
     * 组件出错信息，请务必监听并处理
     */
    override fun onError(code: Int, message: String?) {

    }

    /**
     * 组件告警信息
     */
    override fun onWarning(code: Int, message: String?) {

    }

    /**
     * 组件log信息
     */
    override fun onDebugLog(message: String?) {
    }

    /**
     * 房间被销毁，当主播调用destroyRoom后，观众会收到该回调
     */
    override fun onRoomDestroy(roomId: String?) {
    }

    /**
     * 房间信息改变的通知
     */
    override fun onRoomInfoChange(roomInfo: TRTCVoiceRoomDef.RoomInfo?) {
//        mNeedRequest = roomInfo!!.needRequest
//        mRoomName = roomInfo.roomName
//        mTvTitle?.text = mRoomName
//        mTvOnLineNum.text = roomInfo.memberCount.toString()
    }

    /**
     * 全量的麦位列表变化,包含了整个麦位表
     * @param seatInfoList 全量的麦位列表
     */
    override fun onSeatListChange(seatInfoList: MutableList<TRTCVoiceRoomDef.SeatInfo>?) {
        //userids 只装麦位上有人的userid
        val userids: MutableList<String> = ArrayList()
        //遍历麦位，获取座位信息
        for (i in seatInfoList!!.indices) {

            //麦位变化回调后的麦位信息
            val newSeatInfo = seatInfoList[i]

            //索引为1是主麦位，需要特别处理,这里只做腾讯云返回的座位信息处理，自己服务器返回的信息不在这里处理
            if (i == 0) {

                //新座位上必须有人并且和旧座位上的人不同才添加到需要更新信息的userids里面去
                //这里需要特殊说明主麦位可能不需要后面的user信息更新，因为麦位没人是不需要跟新后面的user信息的
                if (!TextUtils.isEmpty(newSeatInfo.userId) && newSeatInfo.userId != mMainSeatUserId?.userId) {
                    userids.add(newSeatInfo.userId)
                }

                //原主麦位空 或者是 原主麦位上的userId 为空 或者是原主麦位上的userId 和 现主麦位返回的userId不相同
                //也就是麦位上没人或者是现主麦位上与原主麦位上的人不一致
                if (mMainSeatUserId == null || TextUtils.isEmpty(mMainSeatUserId?.userId) ||
                        mMainSeatUserId?.userId != newSeatInfo.userId) {

                    //主麦位
                    val userInfo = VoiceRoomSeatEntity.UserInfo()
                    if (mMainSeatUserId == null) {
                        mMainSeatUserId = VoiceRoomSeatEntity()
//                        mMainSeatUserId?.userInfo = VoiceRoomSeatEntity.UserInfo()
                    }

                    //这里做座位信息的更新，比如说userId，麦位状态 是否禁言等
                    mMainSeatUserId?.userId = newSeatInfo.userId

                    mMainSeatUserId?.userAvatar = ""
                    mMainSeatUserId?.userName = ""

                    userInfo.let {
                        /// 【字段含义】用户唯一标识
                        it.id = newSeatInfo.userId
                        /// 【字段含义】用户昵称
                        it.userName = ""
                        /// 【字段含义】用户头像
                        it.userPicture = ""
                        /// 【字段含义】用户性别
                        it.userSex = -1
                        /// 【字段含义】 用户当前身份 1房主 2管理员 3普通人
                        it.userKind = 0
                        /// 【字段含义】用户爵位  0平民 1男爵 2子爵 3伯爵 4侯爵 5公爵 6国王 7皇帝
                        it.noble = "0"
                        /// 【字段含义】收到的礼物金币总数
                        it.giftCoinCount = 0
                        /// 【字段含义】头饰地址
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
                //主麦位的座位信息跟新完成后跳过，继续后面麦位的信息
                continue
            }

            //后面为其他麦位的座位信息跟新
            //遍历全卖位变化时跳过主麦位 这里的旧座位列表需要-1
            val oldSeatInfo = mVoiceRoomSeatEntityList?.get(i - 1)

            //新麦位上的userid不为空，也就是新麦位上必须有人并且与旧麦位上的不一样 这里只把麦位上现在有人并且和以前的人ID 不同的添加到了需要更新信息的列表上了
            //后面还需要把 座位上以前有人但是现在没人了的附加信息删除掉
            if (!TextUtils.isEmpty(newSeatInfo.userId) && newSeatInfo.userId != oldSeatInfo?.userId) {
                userids.add(newSeatInfo.userId)
            }

            //这里做座位信息的更新，比如说userId，麦位状态 是否禁言等  这里要更新当前座位没人的信息，但是要保留座位没变的人的信息
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

            //现在座位上没有人或者是有人但是跟以前id不同的user的信息置空
            if (TextUtils.isEmpty(newSeatInfo.userId) || newSeatInfo.userId != oldSeatInfo?.userId) {
                oldSeatInfo?.userName = ""
                oldSeatInfo?.userAvatar = ""

                val emptyUserInfo = VoiceRoomSeatEntity.UserInfo()


                emptyUserInfo.let {
                    /// 【字段含义】用户唯一标识
                    it.id = newSeatInfo.userId
                    /// 【字段含义】用户昵称
                    it.userName = ""
                    /// 【字段含义】用户头像
                    it.userPicture = ""
                    /// 【字段含义】用户性别
                    it.userSex = -1
                    /// 【字段含义】 用户当前身份 1房主 2管理员 3普通人
                    it.userKind = 0
                    /// 【字段含义】用户爵位  0平民 1男爵 2子爵 3伯爵 4侯爵 5公爵 6国王 7皇帝
                    it.noble = ""
                    /// 【字段含义】收到的礼物金币总数
                    it.giftCoinCount = 0
                    /// 【字段含义】头饰地址
                    it.headwear = ""
                }

                oldSeatInfo?.userInfo = emptyUserInfo
            }
        }

        //刷新座位列表
        mVoiceRoomSeatAdapter?.notifyDataSetChanged()
        setRoomGiftValue()

        //拿到座位上有user的userid再去获取腾讯云上的详情
        mTRTCVoiceRoom.getUserInfoList(userids) { code, msg, list ->
            //将列表座位上的user转为键值对形式，以便后期好获取信息
            val map: MutableMap<String, TRTCVoiceRoomDef.UserInfo> = HashMap()

            list?.forEach {
                map[it.userId] = it
            }

            //遍历麦位，这时的麦位应已经有麦位状态，但是没有user信息，这里遍历麦位，将麦位上有user的添加userinfo
            for (i in seatInfoList.indices) {

                val newSeatInfo = seatInfoList[i]

                val userInfo = map[newSeatInfo.userId] ?: continue

                //主麦位做特殊处理
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

            //等待麦位初始化完成后 完善信息，已腾讯云返回的麦位信息为准
            if (mikesSeat.isNotEmpty() && !mIsSeatInitSuccess) {

                //主麦位单独处理
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

                //其他麦位
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
     * 有成员上麦(主动上麦/主播抱人上麦)
     * @param index 上麦的麦位
     * @param user  用户详细信息
     */
    override fun onAnchorEnterSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {

        if (user?.userId == mSelfUserId) {
            isAnchorColse = getMySeatMute(index)
            mikeSwitch()
        }
        if (index != 0) {
            // 房主上麦就别提醒了
            showNotifyMsg(user!!.userName + "上" + index + "号麦")
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
     * 有成员下麦(主动下麦/主播踢人下麦)
     * @param index 下麦的麦位
     * @param user  用户详细信息
     */
    override fun onAnchorLeaveSeat(index: Int, user: TRTCVoiceRoomDef.UserInfo?) {
        //如果下麦人员是我，关闭麦克风静音
        if (user?.userId == mSelfUserId) {
//            isMaikStatus = true
//            mikeSwitch()
            closeMike()
        }
//        mVoiceRoomSeatEntityList.

//        if (index != 0) {
//            // 房主上麦就别提醒了
//            showNotifyMsg(user!!.userName + "下" + index + "号麦")
//        }

    }

    /**
     * 主播禁麦
     * @param index  操作的麦位
     * @param isMute 是否静音
     */
    override fun onSeatMute(index: Int, isMute: Boolean) {

        if (isMute) {
            showNotifyMsg(index.toString() + "号位被禁言")
        } else {
            showNotifyMsg(index.toString() + "号位解除禁言")
        }
    }

    /**
     * 主播封麦
     * @param index  操作的麦位
     * @param isClose 是否封禁麦位
     */
    override fun onSeatClose(index: Int, isClose: Boolean) {

        showNotifyMsg(if (isClose) "房主封禁" + index + "号位" else "房主解禁" + index + "号位")

    }

    /**
     * 观众进入房间
     *
     * @param userInfo 观众的详细信息
     */
    override fun onAudienceEnter(userInfo: TRTCVoiceRoomDef.UserInfo?) {

    }

    /**
     * 观众离开房间
     *
     * @param userInfo 观众的详细信息
     */
    override fun onAudienceExit(userInfo: TRTCVoiceRoomDef.UserInfo?) {
        showNotifyMsg(userInfo!!.userName + "退房")
    }

    /**
     * 上麦成员的音量变化
     *
     * @param userId 用户 ID
     * @param volume 音量大小 0-100
     */
    override fun onUserVolumeUpdate(userId: String?, volume: Int) {
        if (volume >= 30) {
            var userSeatIndex = getUserSeatIndex(userId)
            setSeatWave(userSeatIndex)
        }

    }

    /**
     * 设置座位说话光晕，定位位置，选择颜色
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
     * 通过userId获取座位坐标
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
     * 收到文本消息。
     *
     * @param message 文本消息。
     * @param userInfo 发送者用户信息。
     */
    override fun onRecvRoomTextMsg(message: String?, userInfo: TRTCVoiceRoomDef.UserInfo?) {
    }

    /**
     * 收到自定义消息。
     *
     * @param cmd 命令字，由开发者自定义，主要用于区分不同消息类型。
     * @param message 文本消息。
     * @param userInfo 发送者用户信息。
     */
    override fun onRecvRoomCustomMsg(cmd: String?, message: String?, userInfo: TRTCVoiceRoomDef.UserInfo?) {

        //收到上麦消息
        if (cmd == CustomMsgConstant.CustomMsgCmdOnseat) {
            mPresenter.customMsgCmdInRoom(message, mMainSeatUserId, mVoiceRoomSeatEntityList, mVoiceRoomSeatAdapter, mMainMike, giftValue == 1)
        }

        //收到进房消息
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

        //收到文本聊天消息
        if (cmd == CustomMsgConstant.CustomMsgCmdText) {
            val userInfo = Gson().fromJson(message, VoiceRoomSeatEntity.UserInfo::class.java)
            var msgBody = MsgBody()
            msgBody.type = MsgBody.MSG_TALK
            msgBody.userInfo = userInfo
            msgBody.message = userInfo.message
            showNotifyMsg(msgBody)
        }

        //emoji表情处理
        if (cmd == CustomMsgConstant.CustomMsgCmdEmoji) {
            val faceMsg = Gson().fromJson(message, FaceMsg::class.java)
            if (faceMsg?.seatIndex == 0) {
                mMainMike?.setSvga(faceMsg.phiz, svgaParser)
            } else {
                setSvga(mFaceView?.get(faceMsg.seatIndex - 1), faceMsg.phiz)
            }
        }

        //收到礼物处理
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

        //接收到修改房间公告自定义消息
        if (cmd == CustomMsgConstant.CustomMsgCmdTitleChange) {

            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)

            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdTitleChange, mRoomSettingEntity)
        }

        //接收礼物值开关信令消息
        if (cmd == CustomMsgConstant.CustomMsgCmdGiftShowChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdGiftShowChange, mRoomSettingEntity)
        }

        //是否开启公屏
        if (cmd == CustomMsgConstant.CustomMsgCmdPublicChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdPublicChange, mRoomSettingEntity)
        }

        //房间名修改
        if (cmd == CustomMsgConstant.CustomMsgCmdNameChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdNameChange, mRoomSettingEntity)
        }

        //密码修改
        if (cmd == CustomMsgConstant.CustomMsgCmdPwdChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdPwdChange, mRoomSettingEntity)
        }

        //房间标签修改
        if (cmd == CustomMsgConstant.CustomMsgCmdTypeChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdTypeChange, mRoomSettingEntity)
        }

        //设置房间背景消息
        if (cmd == CustomMsgConstant.CustomMsgCmdRoomBgViewChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdRoomBgViewChange, mRoomSettingEntity)
        }

        //接收到设置管理员自定义消息
        if (cmd == CustomMsgConstant.CustomMsgCmdSetManager) {
            val mRoomManager = Gson().fromJson(message, RoomManagerMsgEntity::class.java)
            setUserKind(CustomMsgConstant.CustomMsgCmdSetManager, mRoomManager)
        }

        //收到踢人消息
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

        //收到清除礼物值消息
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

        //收到排麦模式自定义消息
        if (cmd == CustomMsgConstant.CustomMsgCmdExclusionChange) {
            val mRoomSettingEntity = Gson().fromJson(message, RoomSettingEntity::class.java)
            receiveRoomSetting(CustomMsgConstant.CustomMsgCmdExclusionChange, mRoomSettingEntity)
        }

        //送礼全局消息
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

        //抽奖全局消息
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
//                ToastUtils.showLong("点击飘窗")
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
     * 收到新的邀请请求
     *
     * @param id  邀请id
     * @param inviter 邀请人userId
     * @param cmd 业务指定的命令字
     * @param content 业务指定的内容
     */
    override fun onReceiveNewInvitation(id: String?, inviter: String?, cmd: String?, content: String?) {
    }


    /**
     * 被邀请者接受邀请
     *
     * @param id  邀请id
     * @param invitee 被邀请人userId
     */
    override fun onInviteeAccepted(id: String?, invitee: String?) {
    }

    /**
     * 被邀请者拒绝邀请
     *
     * @param id  邀请id
     * @param invitee 被邀请人userId
     */
    override fun onInviteeRejected(id: String?, invitee: String?) {
    }

    /**
     * 邀请人取消邀请
     *
     * @param id  邀请id
     * @param inviter 邀请人userId
     */
    override fun onInvitationCancelled(id: String?, inviter: String?) {
    }

    override fun onDestroy() {
        mContext = null
        super.onDestroy()
    }

    /**
     * 请求用户详情成功
     * @param userInfo  用户资料
     */
    override fun userOnSuccess(userInfo: VoiceRoomSeatEntity.UserInfo?) {


    }


    /**
     * 上麦后回调
     * @param status 状态 1 成功，2麦位被锁，其他失败
     */
    override fun takeSeatSuccess(status: Int?, position: Int?) {

    }

    /**
     * 请求礼物接口返回成功
     */
    override fun roomGiftSuccess(roomGift: GiftAllResult?) {
        if (roomGift != null) {
            mRoomGiftView = RoomGiftView.newInstance(roomGift, mRoomId, mSelfUserInfo?.noble)
            mRoomGiftView?.setOnGiftSendCallback(this)
        }
    }

    override fun onFollow(status: String?) {
        if (status == "1") {
            ToastUtils.showShort("关注成功")
        } else {
            ToastUtils.showShort("关注失败")
        }
    }

    override fun onUnFollow(status: String?) {
        if (status == "1") {
            ToastUtils.showShort("取消关注成功")
        } else {
            ToastUtils.showShort("取消关注失败")
        }
    }

    override fun onAddManager(status: Int?, userId: String?) {
        if (status == 1) {

            var rommManager = RoomManagerMsgEntity(2, userId)
            val managerStr = Gson().toJson(rommManager)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdSetManager, managerStr) { code, msg ->
                if (code == 0) {
                    ToastUtils.showShort("添加管理员成功")
                }
            }

        } else {
            ToastUtils.showShort("添加管理员成功")
        }
    }

    override fun onRemoveManager(status: Int?, userId: String?) {
        if (status == 1) {

            var rommManager = RoomManagerMsgEntity(3, userId)
            val managerStr = Gson().toJson(rommManager)
            mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdSetManager, managerStr) { code, msg ->
                if (code == 0) {
                    ToastUtils.showShort("移除管理员成功")
                }
            }
        } else {
            ToastUtils.showShort("移除管理员成功")
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
                        ToastUtils.showShort("踢出房间")
                    }
                }
            } else {
                ToastUtils.showShort("加入黑名单失败")
            }
        } else if (status == "2") {
            if (resultStatus == 1) {
                ToastUtils.showShort("移除黑名单成功")
            } else {
                ToastUtils.showShort("移除黑名单失败")
            }
        }
    }

    /**
     * 清除礼物值成功回调
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
     * 点击表情回调
     */
    override fun onFaceClickResult(face: RoomFace?, isNobleFace: Boolean?, noble: String?) {

    }

    /**
     * 递归获取ImageView
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
     * 递归获取getWaveView
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
     * 播放表情动画
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
     * 发送礼物结果
     */
    override fun onSendGiftResult(status: Int?, users: MutableList<GiftViewSeat>?, giftResult: RoomGiftResult?, giftNum: Int?, isTotal: Boolean?) {

    }

    /**
     * 点击开通贵族弹窗
     */
    override fun onNobleClick() {
        ActStartUtils.startAct(this, NobleWebActivity::class.java)
    }


    /**
     * 点击房间设置按钮回调 - 特效开关
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
     * 点击房间设置按钮回调 - 公屏开关
     */
    override fun onGpSwitch(isOpenGp: Boolean?) {

        mPresenter?.setGpSwitch(mRoomId, if (isOpenGp!!) 1 else 0, TRTCVoiceRoomCallback.ActionCallback { code, msg ->

            if (code == 1) {//成功

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

            } else {//失败
                ToastUtils.showShort("修改失败")
            }
        })
    }

    /**
     * 点击房间设置按钮回调 - 礼物值开关
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
                ToastUtils.showShort("修改失败")
            }
        })


    }

    /**
     * 点击房间设置按钮回调 - 点击设置
     */
    override fun onSetting() {
        val bundle = Bundle()
        bundle.putSerializable(Constant.ROOM_INFO, mRoomInfo)
        ActStartUtils.startAct(this, RoomSettingAct::class.java, bundle)
    }

    protected fun setIsOpenGiftValue(isOpenLwz: Int) {
        //设置座位礼物值
//        mVoiceRoomSeatAdapter?.setGiftValue(isOpenLwz == 1)
        switchGiftValue(isOpenLwz == 1)

        //设置主位上礼物值
        mMainMike?.setSeatGiftValue(isOpenLwz == 1)
    }

    /**
     * 礼物值开关
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
     * 设置公屏后的公屏消息
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
     * 设置房间标题上上锁图标
     */
    protected fun setRoomLockIcon(roomLock: Int?) {
        if (roomLock == 1) {
            mIvLock?.visibility = View.VISIBLE
        } else {
            mIvLock?.visibility = View.GONE
        }
    }

    /**
     * 关闭房间特效显示图标
     */
    protected fun setRoomSpecialIcon(roomSpecial: Boolean) {

        if (roomSpecial) {
            mIvRoomCloseTx?.visibility = View.GONE
        } else {
            mIvRoomCloseTx?.visibility = View.VISIBLE
        }
    }


    /**
     * 关闭设置dialog回调
     */
    override fun onSettingDismiss() {
        isSetting = false
    }

    /**
     * 右上角 随机进入嗨聊房
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
     * 右上角杀死页面
     */
    override fun onFinishRoom() {
        onBackPressed()
    }

    /**
     * 右上角最小化
     */
    override fun onMinimize() {
        minimize()
    }

    /**
     * 右上角举报
     */
    override fun onReport() {
        var bundle = Bundle()
        bundle.putString(Constant.REPOTR_TYPE, Constant.REPORT_TYPE_ROOM)
        bundle.putString(Constant.REPORT_ID, mRoomId)
        ActStartUtils.startAct(this, ReportAct::class.java, bundle)
    }

    /**
     * 右上角弹窗消失
     */
    override fun onCounDismiss() {
        isControl = false
    }

    /**
     * 房间榜是否消失
     */
    override fun onDismiss() {
        isRoomRank = false
    }


    /**
     * 礼物弹窗是否消失
     */
    override fun onGiftDismiss() {
        isGift = false
    }

    /**
     * 计算礼物值
     */
    protected fun getGiftValue(giftType: Int, giftPrice: Int, giftNum: Int): Int {

        if (giftType == 1) return 0

        return giftPrice * giftNum
    }

    /**
     * 返回被点击对象是否在麦位上的座位信息
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
     * 座位弹窗功能点击回调
     *
     * @param status 状态码 用于区分操作
     * @param userInfo  被点击人信息
     * @param userSeatInfo 被点击人座位信息
     */
    override fun onSeatItemClick(status: Int, userInfo: VoiceRoomSeatEntity.UserInfo?, userSeatInfo: RoomSeat?) {
        when (status) {
            RoomSeatConstant.SEND_GIFT -> {     //送礼物
                ToastUtils.showShort("送礼物")
                if (!isGift) {
                    isGift = true
                    if (mRoomGiftView != null) {

                        val giftViewSeat = GiftViewSeat(userInfo?.id, userInfo?.userName, userInfo?.userPicture, userInfo?.noble, userSeatInfo?.index, false, isRoom = false, isSelect = false, isSeat = true)

                        mRoomGiftView?.setGiveAway(giftViewSeat, mMainSeatUserId, mVoiceRoomSeatEntityList)
                        mRoomGiftView?.show(supportFragmentManager, TAG)
                    }
                }
            }

            RoomSeatConstant.PRIVATE_MSG -> {        //私信

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

            RoomSeatConstant.SEND_DRESS -> {         //送装扮
                val bundle = Bundle()
                bundle.putString(Constant.USER_ID, userInfo?.id)
                ActStartUtils.startAct(this, HeadgearAct::class.java, bundle)

            }

            RoomSeatConstant.ATTENTION -> {     //关注
                mPresenter.follow(userInfo?.id, "1")
            }

            RoomSeatConstant.UNSUBSCRIBE -> {       //取消关注
                mPresenter.follow(userInfo?.id, "0")
            }

            RoomSeatConstant.SET_MANAGER -> {        //设置管理员
                mPresenter.addManager(mRoomId, userInfo?.id)

            }

            RoomSeatConstant.CANCEL_MANAGER -> {         //取消管理员
                mPresenter.removeManager(mRoomId, userInfo?.id)
            }

            RoomSeatConstant.OUT_ROOM -> {      //踢出房间
                var map = HashMap<String, String>()
                map["userId"] = userInfo?.id!!
                val outRoom = Gson().toJson(map)
                mTRTCVoiceRoom.sendRoomCustomMsg(CustomMsgConstant.CustomMsgCmdKickUser, outRoom) { code, msg ->
                    if (code == 0) {
                        ToastUtils.showShort("踢出房间")
                    }
                }

            }

            RoomSeatConstant.ADD_BLACK_LIST -> {        //加入黑名单
                mPresenter.addBlackList("1", mRoomId, userInfo?.id)

            }

            RoomSeatConstant.REMOVE_BLACK_LIST -> {     //移除黑名单
                mPresenter.removeBlackList("1", mRoomId, userInfo?.id)
            }

            RoomSeatConstant.OPEN_MIKE -> {       //开启麦克风
                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.muteSeat(userSeatInfo?.index!!, false) { code, msg ->
                        if (code == 0) {
                            ToastUtils.showShort("开启麦克风")
                        }
                    }
                }

            }

            RoomSeatConstant.CLOSE_MIKE -> {        //关闭麦克风
                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.muteSeat(userSeatInfo?.index!!, true) { code, msg ->
                        if (code == 0) {
                            ToastUtils.showShort("关闭麦克风")
                        }
                    }
                }
            }

            RoomSeatConstant.PICK_UP -> {       //抱上麦
                if (userSeatInfo?.index == -1) {
                    val pickIndex = getPickIndex()
                    if (pickIndex != -1) {
                        mTRTCVoiceRoom.pickSeat(pickIndex, userInfo?.id, null)
                    } else {
                        ToastUtils.showShort("麦位已满")
                    }
                }

            }

            RoomSeatConstant.PICK_DOWN -> {     //抱下麦

                val seat = getUserSeatInfo(userInfo?.id!!)
                if (seat.isSeat!!) {
                    mTRTCVoiceRoom.kickSeat(seat.index!!, null)
                }
            }

            RoomSeatConstant.LOCK_MIKE -> {      //锁麦

                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.closeSeat(userSeatInfo?.index!!, true, null)
                }


            }

            RoomSeatConstant.UNLOCK_MIKE -> {       //解锁麦
                if (userSeatInfo?.index != -1) {
                    mTRTCVoiceRoom.closeSeat(userSeatInfo?.index!!, false, null)
                }
            }

            RoomSeatConstant.CLEAR_GIFT_VALUE -> {      //清除礼物值
                ToastUtils.showShort("清空礼物值")
                mPresenter.clearGiftValue(mRoomId, userInfo?.id)
            }

            RoomSeatConstant.WINDOWS_DISMISS -> {       //弹窗消失
                isSeatWindows = false
            }


            RoomSeatConstant.UP_SEAT -> {           //上麦
                startTakeSeat(userSeatInfo?.index!!)
            }

            RoomSeatConstant.PICK_SEAT -> {         //在线抱麦
                var bundle = Bundle()
                bundle.putSerializable(Constant.ROOM_INFO, mRoomInfo)
                bundle.putInt(Constant.ROOM_MARK, Constant.ROOM_PICK)
                bundle.putInt(Constant.ROOM_SEAT_INDEX, userSeatInfo?.index!!)
                ActStartUtils.startForAct(this, OnLineAct::class.java, bundle, START_ON_LINE)
            }

            RoomSeatConstant.MANAGER_VIEW_DISMISS -> {      //Manager弹窗消失
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
     * 主动上麦
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
     * 倒序获取空座位
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

        //从在线列表回来，抱上麦
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
        view?.getLocationInWindow(location) //获取在当前窗口内的绝对坐标
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
