package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnSeatClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.RoomSeatConstant
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.SeatButtonAdapter
import com.xiaoshanghai.nancang.net.bean.FunButtonEntity
import com.xiaoshanghai.nancang.net.bean.RoomSeat
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.GridDecoration
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity.UserInfo

class RoomSeatView private constructor() : DialogFragment(), View.OnClickListener {

    var mUserInfo: ConstraintLayout? = null

    var mIvAvatar: AvatarView? = null

    var mTvUserName: TextView? = null

    var mSexAndAge: SexAndAgeView? = null

    var mULv: UserLevelView? = null

    var mClv: CharmLevelView? = null

    var mTvId: TextView? = null

    var mFamilyName: TextView? = null

    var mRcyFun: RecyclerView? = null

    var mRlMikeSwitch: RelativeLayout? = null

    var mRlPiceSeat: RelativeLayout? = null

    var mRlLockSeat: RelativeLayout? = null

    var mRlClearGift: RelativeLayout? = null

    var mTvMikeSwitch: TextView? = null

    var mTvPickSeat: TextView? = null

    var mTvLockSeat: TextView? = null

    var mTvClearGift: TextView? = null

    var mLlDown: LinearLayout? = null

    var userInfo: UserInfo? = null

    var userSeat: RoomSeat? = null

    var isFollow: Boolean = false

    var mSelfKind: Int = 3

    var mIvClose: ImageView? = null

    var mIvJuBao: ImageView? = null

    var mIvLh: ImageView? = null

    var mLine: View? = null

    var mIvNoble: ImageView? = null

    var mOnSeatClickCallback: OnSeatClickCallback? = null

    val mAdapter: SeatButtonAdapter by lazy { SeatButtonAdapter() }

    companion object {
        fun newInstance(userInfo: UserInfo?, selfKind: Int, userSeatInfo: RoomSeat, isFollow: Boolean): RoomSeatView {
            val mRoomSeatView = RoomSeatView()
            val args = Bundle()
            args.putSerializable(Constant.ROOM_USER, userInfo)
            args.putInt(Constant.ROOM_SELF_KIND, selfKind)
            args.putSerializable(Constant.ROOM_USER_SEAT, userSeatInfo)
            args.putBoolean(Constant.ROOM_IS_FOLLOW, isFollow)
            mRoomSeatView.arguments = args
            return mRoomSeatView
        }
    }

    fun setOnSeatClickCallback(onSeatClick: OnSeatClickCallback?) {
        this.mOnSeatClickCallback = onSeatClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelfKind = arguments?.getInt(Constant.ROOM_SELF_KIND, 3)!!

        userInfo = arguments?.getSerializable(Constant.ROOM_USER) as UserInfo?

        userSeat = arguments?.getSerializable(Constant.ROOM_USER_SEAT) as RoomSeat?

        isFollow = arguments?.getBoolean(Constant.ROOM_IS_FOLLOW)!!

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val mView = inflater.inflate(R.layout.view_room_seat, container)
        initView(mView)
        setUserInfo()
        return mView

    }

    private fun initView(mView: View?) {
        mUserInfo = mView?.findViewById(R.id.con_user_info)

        mIvAvatar = mView?.findViewById(R.id.iv_avatar)

        mTvUserName = mView?.findViewById(R.id.tv_user_name)

        mSexAndAge = mView?.findViewById(R.id.sex_and_age)

        mULv = mView?.findViewById(R.id.u_lv)

        mClv = mView?.findViewById(R.id.c_lv)

        mTvId = mView?.findViewById(R.id.tv_id)

        mFamilyName = mView?.findViewById(R.id.tv_family_name)

        mRcyFun = mView?.findViewById(R.id.rcy_fun)

        mRlMikeSwitch = mView?.findViewById(R.id.rl_mike_switch)

        mRlPiceSeat = mView?.findViewById(R.id.rl_pice_seat)

        mRlLockSeat = mView?.findViewById(R.id.rl_lock_seat)

        mRlClearGift = mView?.findViewById(R.id.rl_clear_gift)

        mTvMikeSwitch = mView?.findViewById(R.id.tv_mike_switch)

        mTvPickSeat = mView?.findViewById(R.id.tv_pick_seat)

        mTvLockSeat = mView?.findViewById(R.id.tv_lock_seat)

        mTvClearGift = mView?.findViewById(R.id.tv_clear_gift)

        mIvLh = mView?.findViewById(R.id.iv_lh)

        mIvClose = mView?.findViewById(R.id.iv_close)
        mIvJuBao = mView?.findViewById(R.id.iv_jubao)

        mLlDown = mView?.findViewById(R.id.ll_down)

        mLine = mView?.findViewById(R.id.line)
        mIvNoble = mView?.findViewById(R.id.iv_noble)

        mIvClose?.setOnClickListener(this)
        mIvJuBao?.setOnClickListener(this)


        mRlMikeSwitch?.setOnClickListener(this)
        mRlPiceSeat?.setOnClickListener(this)
        mRlLockSeat?.setOnClickListener(this)
        mRlClearGift?.setOnClickListener(this)
        mIvAvatar?.setOnClickListener(this)

        mRcyFun?.layoutManager = GridLayoutManager(context, 4)
        mRcyFun?.addItemDecoration(GridDecoration(0, 16, 0, 0, 4))
        mRcyFun?.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val funButton = mAdapter.getItem(position)
            when (funButton.buttonId) {

                0 -> {

                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.SEND_GIFT, userInfo!!, userSeat!!)
                }

                1 -> {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.PRIVATE_MSG, userInfo!!, userSeat!!)
                }
                2 -> {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.SEND_DRESS, userInfo!!, userSeat!!)
                }
                3 -> {
                    if (funButton.isSelect!!) {
                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.UNSUBSCRIBE, userInfo!!, userSeat!!)

                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.ATTENTION, userInfo!!, userSeat!!)
                    }
                }
                4 -> {

                    if (funButton.isSelect!!) {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.CANCEL_MANAGER, userInfo!!, userSeat!!)
                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.SET_MANAGER, userInfo!!, userSeat!!)
                    }

                }
                5 -> {

                    if (mSelfKind == 2 && userInfo?.noble == "7") {
                        ToastUtils.showShort("皇帝大大不能被踢出房间哦")
                    } else {
                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.OUT_ROOM, userInfo!!, userSeat!!)

                    }

                }
                6 -> {

                    if (funButton.isSelect!!) {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.REMOVE_BLACK_LIST, userInfo!!, userSeat!!)
                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.ADD_BLACK_LIST, userInfo!!, userSeat!!)
                    }

                }
            }
            dismiss()
        }


    }

    private fun setUserInfo() {


        mIvAvatar?.setAvatarAndHeadear(userInfo?.userPicture, userInfo?.headwear)
        mTvUserName?.text = userInfo?.userName
        mSexAndAge?.setSexAndAge(userInfo?.userSex!!, BirthdayToAgeUtil.BirthdayToAge(userInfo?.userBirthday))
        mULv?.setUserLevel(userInfo?.userLevel!!)
        mClv?.setCharmLevel(userInfo?.charmLevel!!)
        mTvId?.text = userInfo?.userNumber.toString()

        if (userInfo?.isBeautiful == 1) {
            mIvLh?.visibility = View.VISIBLE
        } else {
            mIvLh?.visibility = View.GONE
        }

        if (userInfo?.noble == "0") {
            mIvNoble?.visibility = View.GONE
        } else {
            val nobleImage = UserManagerUtils.nobleImage(userInfo?.noble!!.toInt())
            GlideAppUtil.loadImage(context, nobleImage, mIvNoble)
            mIvNoble?.visibility = View.VISIBLE
        }

        mFamilyName?.text = if (!TextUtils.isEmpty(userInfo?.clanId)) userInfo?.clanName else "未加入家族"

        val funEntity = getFunEntity(mSelfKind, userInfo!!, isFollow!!)
        setDown(mSelfKind, userSeat!!)
        mAdapter.data = funEntity

    }

    private fun getFunEntity(selfKind: Int, userInfo: UserInfo, isFollow: Boolean): MutableList<FunButtonEntity> {

        var buttonList: MutableList<FunButtonEntity> = ArrayList()

        if (userInfo?.id == SPUtils.getInstance().userInfo.id) {
            return buttonList
        }

        val sendGift = FunButtonEntity(R.mipmap.icon_send_gift, R.mipmap.icon_send_gift, "送礼物", 0, false)
        var privateMsg = FunButtonEntity(R.mipmap.icon_private_msg, R.mipmap.icon_private_msg, "私信", 1, false)
        val sendDress = FunButtonEntity(R.mipmap.icon_send_dress, R.mipmap.icon_send_dress, "送装扮", 2, false)

        var note = FunButtonEntity(R.mipmap.icon_not_note, R.mipmap.icon_note, "关注", 3, false)

        var manager = FunButtonEntity(R.mipmap.icon_out_manager, R.mipmap.icon_set_manager, "设为管理员", 4, false)

        var outRoom = FunButtonEntity(R.mipmap.icon_out_room, R.mipmap.icon_out_room, "踢出房间", 5, false)
        var blackList = FunButtonEntity(R.mipmap.icon_out_blact_list, R.mipmap.icon_in_black_list, "拉入黑名单", 6, false)

        if (isFollow) {
            note.buttonName = "取消关注"
            note.isSelect = true
        } else {
            note.buttonName = "关注"
            note.isSelect = false
        }

        if (userInfo?.userKind == 3) {
            manager.isSelect = false
            manager.buttonName = "设为管理员"
        } else {
            manager.isSelect = true
            manager.buttonName = "取消管理员"
        }

        if (userInfo?.isBlock != 1) {
            blackList.isSelect = true
            blackList.buttonName = "取消黑名单"
        } else {
            blackList.isSelect = false
            blackList.buttonName = "拉入黑名单"
        }


        buttonList.add(sendGift)
        buttonList.add(privateMsg)
        buttonList.add(sendDress)
        buttonList.add(note)

        if (selfKind == 1 && userInfo.userKind != 1) {
            buttonList.add(manager)
            buttonList.add(outRoom)
            buttonList.add(blackList)
        } else if (selfKind == 2 && userInfo.userKind != 1 && userInfo.userKind != 2) {
            buttonList.add(outRoom)
            buttonList.add(blackList)
        }

        if (buttonList.size > 0) {
            mLine?.visibility = View.GONE
        } else {
            mLine?.visibility = View.VISIBLE
        }


        return buttonList

    }

    private fun setDown(selfKind: Int, userSeat: RoomSeat) {

        when (selfKind) {

            1 -> {//自己为房主

                //点击对象在麦位上
                if (userSeat?.isSeat!!) {
                    mRlMikeSwitch?.visibility = View.VISIBLE
                    mRlPiceSeat?.visibility = View.VISIBLE
                    mRlLockSeat?.visibility = View.VISIBLE
                    mRlClearGift?.visibility = View.VISIBLE

                    //点击对象是自己
                    if (isSelf()) {
                        mTvPickSeat?.text = "下麦旁听"

                    } else {
                        //不是自己
                        mTvPickSeat?.text = "抱TA下麦"
                    }

                } else {
                    mRlMikeSwitch?.visibility = View.GONE
                    mRlPiceSeat?.visibility = View.VISIBLE
                    mRlLockSeat?.visibility = View.GONE
                    mRlClearGift?.visibility = View.GONE
                    mTvPickSeat?.text = "抱TA上麦"
                }

                //对麦位的操作不管是不是自己
                if (userSeat?.isMute!!) {
                    mTvMikeSwitch?.text = "开麦"
                } else {
                    mTvMikeSwitch?.text = "闭麦"
                }

                if (userSeat?.isClose!!) {
                    mTvLockSeat?.text = "解锁麦"
                } else {
                    mTvLockSeat?.text = "锁麦"
                }

            }

            //自己为房管
            2 -> {
                //对麦位的操作不管是不是自己
                if (userSeat?.isMute!!) {
                    mTvMikeSwitch?.text = "开麦"
                } else {
                    mTvMikeSwitch?.text = "闭麦"
                }

                if (userSeat?.isClose!!) {
                    mTvLockSeat?.text = "解锁麦"
                } else {
                    mTvLockSeat?.text = "锁麦"
                }
                //点击对象在麦位上
                if (userSeat?.isSeat!!) {
                    //点击对象是自己
                    if (isSelf()) {
                        mRlMikeSwitch?.visibility = View.VISIBLE
                        mRlPiceSeat?.visibility = View.VISIBLE
                        mRlLockSeat?.visibility = View.VISIBLE
                        mRlClearGift?.visibility = View.VISIBLE
                        mTvPickSeat?.text = "下麦旁听"

                    } else {
                        if (userInfo?.userKind == 1 || userInfo?.userKind == 2) {
                            mRlMikeSwitch?.visibility = View.GONE
                            mRlPiceSeat?.visibility = View.GONE
                            mRlLockSeat?.visibility = View.VISIBLE
                            mRlClearGift?.visibility = View.GONE
                        } else if (userInfo?.userKind == 3) {
                            mRlMikeSwitch?.visibility = View.VISIBLE
                            mRlPiceSeat?.visibility = View.VISIBLE
                            mRlLockSeat?.visibility = View.VISIBLE
                            mRlClearGift?.visibility = View.VISIBLE
                            mTvPickSeat?.text = "抱TA下麦"

                        }
                    }


                } else {
                    mRlMikeSwitch?.visibility = View.GONE
                    mRlPiceSeat?.visibility = View.VISIBLE
                    mRlLockSeat?.visibility = View.GONE
                    mRlClearGift?.visibility = View.GONE

                    mTvPickSeat?.text = "抱TA上麦"

                }


            }

            3 -> {

                //对麦位的操作不管是不是自己
                if (userSeat?.isMute!!) {
                    mTvMikeSwitch?.text = "开麦"
                } else {
                    mTvMikeSwitch?.text = "闭麦"
                }

                if (userSeat?.isClose!!) {
                    mTvLockSeat?.text = "解锁麦"
                } else {
                    mTvLockSeat?.text = "锁麦"
                }

                if (isSelf()) {

                    mRlMikeSwitch?.visibility = View.GONE
                    mRlPiceSeat?.visibility = View.VISIBLE
                    mRlLockSeat?.visibility = View.GONE
                    mRlClearGift?.visibility = View.GONE
                    if (userSeat?.isSeat!!) {
                        mLlDown?.visibility = View.VISIBLE
                        mTvPickSeat?.text = "下麦旁听"
                    } else {
                        mTvPickSeat?.text = "抱TA上麦"
                        mLlDown?.visibility = View.GONE
                        mUserInfo?.setBackgroundResource(R.drawable.shape_b_r17_bg)
                        mLine?.visibility = View.GONE

                    }

                } else {
                    mLlDown?.visibility = View.GONE
                    mRlMikeSwitch?.visibility = View.GONE
                    mRlPiceSeat?.visibility = View.GONE
                    mRlLockSeat?.visibility = View.GONE
                    mRlClearGift?.visibility = View.GONE
//                    mUserInfo?.setBackgroundResource(R.drawable.shape_b_r17_bg)
                }

            }
        }
    }

    private fun isSelf(): Boolean {
        return userInfo?.id == SPUtils.getInstance().userInfo.id
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            //开麦闭麦开关
            R.id.rl_mike_switch -> {
                if (userSeat?.isSeat!!) {
                    if (userSeat?.isMute!!) {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.OPEN_MIKE, userInfo!!, userSeat!!)
                    } else {

                        if (mSelfKind == 2 && userInfo?.noble!! == "7") {
                            ToastUtils.showShort("皇帝大大不可以禁言哦~")
                        } else {
                            mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.CLOSE_MIKE, userInfo!!, userSeat!!)

                        }
//
//                        if (userInfo?.noble!!.toInt() < 7) {
//                        } else {
//
//                        }

                    }
                }
                dismiss()
            }

            //抱麦开关
            R.id.rl_pice_seat -> {

                if (userSeat?.isSeat!!) {

                    if (mSelfKind == 2 && userInfo?.noble == "7" && !isSelf()) {
                        ToastUtils.showShort("皇帝大大不可以踢下麦哦~")
                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.PICK_DOWN, userInfo!!, userSeat!!)
                    }


//                    if (userInfo?.noble!!.toInt() < 7 || isSelf()) {
//
//                    } else {
//                        ToastUtils.showShort("皇帝大大不可以踢下麦哦~")
//                    }

                } else {

                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.PICK_UP, userInfo!!, userSeat!!)
                }


                dismiss()
            }

            //锁麦开关
            R.id.rl_lock_seat -> {
                if (userSeat?.isSeat!!) {
                    if (userSeat?.isClose!!) {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.UNLOCK_MIKE, userInfo!!, userSeat!!)
                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.LOCK_MIKE, userInfo!!, userSeat!!)
                    }
                }
                dismiss()
            }

            R.id.rl_clear_gift -> {

                mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.CLEAR_GIFT_VALUE, userInfo!!, userSeat!!)
                dismiss()
            }

            R.id.iv_close -> {
                dismiss()
            }

            R.id.iv_jubao -> {
                mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.REPORT_USER, userInfo!!, userSeat!!)
                dismiss()
            }

            R.id.iv_avatar -> {
                val bundle = Bundle()
                bundle.putString(Constant.USER_ID, userInfo?.id)
                ActStartUtils.startAct(context, HomePageAct::class.java, bundle)
                dismiss()
            }
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.WINDOWS_DISMISS, null, null)
    }

}