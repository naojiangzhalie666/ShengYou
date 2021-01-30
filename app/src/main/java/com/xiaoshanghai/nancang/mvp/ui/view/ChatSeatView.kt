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
import com.xiaoshanghai.nancang.callback.OnChatSeatClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.RoomSeatConstant
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HomePageAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.SeatButtonAdapter
import com.xiaoshanghai.nancang.net.bean.FunButtonEntity
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.GridDecoration
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity.UserInfo

class ChatSeatView private constructor() : DialogFragment(), View.OnClickListener {

    var mConUserInfo:ConstraintLayout? = null

    var mIvAvatar: AvatarView? = null

    var mTvUserName: TextView? = null

    var mSexAndAge: SexAndAgeView? = null

    var mULv: UserLevelView? = null

    var mClv: CharmLevelView? = null

    var mIvLh: ImageView? = null

    var mTvId: TextView? = null

    var mFamilyName: TextView? = null

    var mRcyFun: RecyclerView? = null

    var userInfo: UserInfo? = null

    var isFollow: Boolean = false

    var mIvClose: ImageView? = null

    var mIvJuBao: ImageView? = null

    var mIvNoble: ImageView? = null


    var mOnSeatClickCallback: OnChatSeatClickCallback? = null

    val mAdapter: SeatButtonAdapter by lazy { SeatButtonAdapter() }

    companion object {
        fun newInstance(userInfo: UserInfo?, isFollow: Boolean): ChatSeatView {
            val mRoomSeatView = ChatSeatView()
            val args = Bundle()
            args.putSerializable(Constant.ROOM_USER, userInfo)
            args.putBoolean(Constant.ROOM_IS_FOLLOW, isFollow)
            mRoomSeatView.arguments = args
            return mRoomSeatView
        }
    }

    fun setOnSeatClickCallback(onSeatClick: OnChatSeatClickCallback?) {
        this.mOnSeatClickCallback = onSeatClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userInfo = arguments?.getSerializable(Constant.ROOM_USER) as UserInfo?


        isFollow = arguments?.getBoolean(Constant.ROOM_IS_FOLLOW)!!

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val mView = inflater.inflate(R.layout.view_chat_seat, container)
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

        mIvLh = mView?.findViewById(R.id.iv_lh)

        mTvId = mView?.findViewById(R.id.tv_id)

        mFamilyName = mView?.findViewById(R.id.tv_family_name)

        mRcyFun = mView?.findViewById(R.id.rcy_fun)

        mIvClose = mView?.findViewById(R.id.iv_close)
        mIvJuBao = mView?.findViewById(R.id.iv_jubao)
        mIvNoble = mView?.findViewById(R.id.iv_noble)

        mIvClose?.setOnClickListener(this)
        mIvJuBao?.setOnClickListener(this)
        mIvAvatar?.setOnClickListener(this)

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

                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.STEP_ON, userInfo!!)
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

        val funEntity = getFunEntity(userInfo!!, isFollow!!)
        mAdapter.data = funEntity

        if (isSelf()) {
            mConUserInfo?.setBackgroundResource(R.drawable.shape_b_r17_bg)
        } else {
            mConUserInfo?.setBackgroundResource(R.drawable.shape_b_top_r17_bg)
        }

    }

    private fun getFunEntity(userInfo: UserInfo, isFollow: Boolean): MutableList<FunButtonEntity> {

        var buttonList: MutableList<FunButtonEntity> = ArrayList()

        if (userInfo?.id == SPUtils.getInstance().userInfo.id) {
            return buttonList
        }

        val sendGift = FunButtonEntity(R.mipmap.icon_send_gift, R.mipmap.icon_send_gift, "送礼物", 0, false)
        var privateMsg = FunButtonEntity(R.mipmap.icon_private_msg, R.mipmap.icon_private_msg, "私信", 1, false)
        val sendDress = FunButtonEntity(R.mipmap.icon_send_dress, R.mipmap.icon_send_dress, "送装扮", 2, false)

        var note = FunButtonEntity(R.mipmap.icon_not_note, R.mipmap.icon_note, "关注", 3, false)

        if (isFollow) {
            note.buttonName = "取消关注"
            note.isSelect = true
        } else {
            note.buttonName = "关注"
            note.isSelect = false
        }

        var step = FunButtonEntity(R.mipmap.icon_caita, R.mipmap.icon_caita, "踩Ta", 4, false)


        buttonList.add(sendGift)
        buttonList.add(privateMsg)
        buttonList.add(sendDress)
        buttonList.add(note)
        buttonList.add(step)


        return buttonList

    }


    private fun isSelf(): Boolean {
        return userInfo?.id == SPUtils.getInstance().userInfo.id
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.WINDOWS_DISMISS, null)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
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

}