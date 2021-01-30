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
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnLineClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.RoomSeatConstant
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.SeatButtonAdapter
import com.xiaoshanghai.nancang.net.bean.FunButtonEntity
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.GridDecoration
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity.UserInfo

class OnLineView private constructor() : DialogFragment(), View.OnClickListener {

    var mConUserInfo: ConstraintLayout? = null

    var mIvAvatar: AvatarView? = null

    var mTvUserName: TextView? = null

    var mSexAndAge: SexAndAgeView? = null

    var mULv: UserLevelView? = null

    var mClv: CharmLevelView? = null

    var mTvId: TextView? = null

    var mFamilyName: TextView? = null

    var mRcyFun: RecyclerView? = null

    var mTvPickSeat: TextView? = null

    var userInfo: UserInfo? = null


    var isFollow: Boolean = false

    var mSelfKind: Int = 3

    var mIvClose: ImageView? = null

    var mIvJuBao: ImageView? = null

    var mIvLh: ImageView? = null

    var mIvNoble: ImageView? = null

    var mOnSeatClickCallback: OnLineClickCallback? = null

    val mAdapter: SeatButtonAdapter by lazy { SeatButtonAdapter() }

    companion object {
        fun newInstance(userInfo: UserInfo?, selfKind: Int, isFollow: Boolean): OnLineView {
            val mRoomSeatView = OnLineView()
            val args = Bundle()
            args.putSerializable(Constant.ROOM_USER, userInfo)
            args.putInt(Constant.ROOM_SELF_KIND, selfKind)
            args.putBoolean(Constant.ROOM_IS_FOLLOW, isFollow)
            mRoomSeatView.arguments = args
            return mRoomSeatView
        }
    }

    fun setOnSeatClickCallback(onSeatClick: OnLineClickCallback) {
        this.mOnSeatClickCallback = onSeatClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelfKind = arguments?.getInt(Constant.ROOM_SELF_KIND, 3)!!

        userInfo = arguments?.getSerializable(Constant.ROOM_USER) as UserInfo?


        isFollow = arguments?.getBoolean(Constant.ROOM_IS_FOLLOW)!!

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val mView = inflater.inflate(R.layout.view_on_line, container)
        initView(mView)
        setUserInfo()
        return mView

    }

    private fun initView(mView: View?) {

        mConUserInfo = mView?.findViewById(R.id.con_user_info)

        mIvAvatar = mView?.findViewById(R.id.iv_avatar)

        mTvUserName = mView?.findViewById(R.id.tv_user_name)

        mSexAndAge = mView?.findViewById(R.id.sex_and_age)

        mULv = mView?.findViewById(R.id.u_lv)

        mClv = mView?.findViewById(R.id.c_lv)

        mTvId = mView?.findViewById(R.id.tv_id)

        mFamilyName = mView?.findViewById(R.id.tv_family_name)

        mRcyFun = mView?.findViewById(R.id.rcy_fun)


        mIvLh = mView?.findViewById(R.id.iv_lh)

        mIvClose = mView?.findViewById(R.id.iv_close)
        mIvJuBao = mView?.findViewById(R.id.iv_jubao)
        mIvNoble = mView?.findViewById(R.id.iv_noble)

        mIvClose?.setOnClickListener(this)
        mIvJuBao?.setOnClickListener(this)
        mIvAvatar?.setOnClickListener(this)

        if (isSelf()) {
            mConUserInfo?.setBackgroundResource(R.drawable.shape_b_r17_bg)
        } else {
            mConUserInfo?.setBackgroundResource(R.drawable.shape_b_top_r17_bg)

        }

        mRcyFun?.layoutManager = GridLayoutManager(context, 4)
        mRcyFun?.addItemDecoration(GridDecoration(0, 16, 0, 0, 4))
        mRcyFun?.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val funButton = mAdapter.getItem(position)
            when (funButton.buttonId) {

                0 -> {

                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.SEND_GIFT, userInfo!!)
                }

                1 -> {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.PRIVATE_MSG, userInfo!!)
                }

                2 -> {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.SEND_DRESS, userInfo!!)
                }

                3 -> {
                    if (funButton.isSelect!!) {
                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.UNSUBSCRIBE, userInfo!!)

                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.ATTENTION, userInfo!!)
                    }
                }
                4 -> {

                    if (funButton.isSelect!!) {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.CANCEL_MANAGER, userInfo!!)
                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.SET_MANAGER, userInfo!!)
                    }

                }
                5 -> {

                    if (mSelfKind == 2 && userInfo?.noble == "7") {
                        ToastUtils.showShort("皇帝大大不能被踢出房间哦")
                    } else {
//                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.OUT_ROOM, userInfo!!, userSeat!!)
                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.OUT_ROOM, userInfo!!)

                    }


                }
                6 -> {

                    if (funButton.isSelect!!) {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.REMOVE_BLACK_LIST, userInfo!!)
                    } else {

                        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.ADD_BLACK_LIST, userInfo!!)
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
            GlideAppUtil.loadImage(context,nobleImage,mIvNoble)
            mIvNoble?.visibility = View.VISIBLE
        }

        mFamilyName?.text = if (!TextUtils.isEmpty(userInfo?.clanId)) userInfo?.clanName else "未加入家族"

        val funEntity = getFunEntity(mSelfKind, userInfo!!, isFollow!!)
        mAdapter.data = funEntity

    }

    private fun getFunEntity(selfKind: Int, userInfo: UserInfo, isFollow: Boolean): MutableList<FunButtonEntity> {

        var buttonList: MutableList<FunButtonEntity> = ArrayList()

        if (userInfo?.id == SPUtils.getInstance().userInfo.id) {
            return buttonList
        }

//        val sendGift = FunButtonEntity(R.mipmap.icon_send_gift, R.mipmap.icon_send_gift, "送礼物", 0, false)
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


//        buttonList.add(sendGift)
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


        return buttonList

    }


    private fun isSelf(): Boolean {
        return userInfo?.id == SPUtils.getInstance().userInfo.id
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.iv_close -> {
                dismiss()
            }

            R.id.iv_jubao -> {
                mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.REPORT_USER, userInfo!!)
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
        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.WINDOWS_DISMISS, null)
    }

}