package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.google.gson.Gson
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.FollowCallback
import com.xiaoshanghai.nancang.callback.OnLineClickCallback
import com.xiaoshanghai.nancang.callback.RoomUserCallback
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.CustomMsgConstant
import com.xiaoshanghai.nancang.constant.RoomSeatConstant
import com.xiaoshanghai.nancang.mvp.contract.RoomOnLineContract
import com.xiaoshanghai.nancang.mvp.presenter.RoomOnLinePresenter
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.HeadgearAct
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ReportAct
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.SpeakAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.OnLineAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.OnLineView
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.CreateRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.xiaoshanghai.nancang.net.bean.RoomManagerMsgEntity
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo

class RoomOnLineAct : BaseMvpActivity<RoomOnLinePresenter>(), RoomOnLineContract.View, TitleBarClickCallback, OnRefreshLoadMoreListener, OnLineClickCallback {

    protected val TAG = RoomOnLineAct::class.java.name

    @BindView(R.id.iv_room_bg)
    lateinit var mIvRoomBg: ImageView

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.rcy_online)
    lateinit var mRcyOnLine: RecyclerView

    protected val mTRTCVoiceRoom: TRTCVoiceRoom by lazy { TRTCVoiceRoom.sharedInstance(this) }//直播间管理类

    private val mAdapter by lazy { OnLineAdapter() }

    private var mRoomInfo: CreateRoomResult? = null   //联网获取的房间信息

    private var mRoomId: String? = null

    private var mSelfKind: Int = -1   //自己在房间里的身份

    private var isSeatWindows: Boolean = false


    override fun setLayoutId(): Int = R.layout.activity_on_line

    override fun createPresenter(): RoomOnLinePresenter {
        return RoomOnLinePresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
        mPresenter.loadOnLineUser(null, mRoomInfo?.id)

    }

    private fun init() {

        mRoomInfo = intent.extras.getSerializable(Constant.ROOM_INFO) as CreateRoomResult?
        mRoomId = mRoomInfo?.id

        mSelfKind = intent.extras.getInt(Constant.ROOM_SELF_KIND, -1)

        GlideAppUtil.loadImage(this, mRoomInfo?.roomBgPicture, mIvRoomBg)

        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshLoadMoreListener(this)

        mRcyOnLine.layoutManager = LinearLayoutManager(this)
        mRcyOnLine.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.getItem(position)

            if (!TextUtils.isEmpty(item.userId) && item.noble?.toInt()!! < 7) {
                mPresenter.getUser(item.userId, mRoomInfo?.id, object : RoomUserCallback {
                    override fun onRoomUser(userInfo: VoiceRoomSeatEntity.UserInfo?) {

                        mPresenter.queryFollow(item.userId!!, object : FollowCallback {
                            override fun onFollow(status: Int) {

                                if (!isSeatWindows) {

                                    var isFollow = status == 1

                                    val roomSeatDialog = OnLineView.newInstance(userInfo, mSelfKind, isFollow)
                                    roomSeatDialog.setOnSeatClickCallback(this@RoomOnLineAct)
                                    roomSeatDialog.show(supportFragmentManager, TAG)
                                    isSeatWindows = true
                                }
                            }
                        })
                    }
                })
            }


        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter?.loadOnLineUser(refreshLayout, mRoomInfo?.id)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.loadOnLineUser(refreshLayout, mRoomInfo?.id)
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun refreshSuccess(refresh: RefreshLayout?, onLineUser: MutableList<OnLineUserResult>) {

        refresh?.finishRefresh()

        mAdapter.setList(onLineUser)

        if (onLineUser.size <= 0) {
//            mAdapter.setEmptyView(R.layout.fans_empty)
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.resetNoMoreData()
        }

    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, onLineUser: MutableList<OnLineUserResult>) {

        if (onLineUser.size <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.finishLoadMore()
            mAdapter.addData(onLineUser)
        }

    }


    override fun titleLeftClick() {
        finish()

    }

    override fun titleRightClick(status: Int) {

    }

    override fun onSeatItemClick(status: Int, userInfo: VoiceRoomSeatEntity.UserInfo?) {
        when (status) {

            RoomSeatConstant.PRIVATE_MSG -> {        //私信
                val chatInfo = ChatInfo()
                chatInfo.type = V2TIMConversation.V2TIM_C2C
                chatInfo.id = userInfo?.id
                chatInfo.chatName = userInfo?.userName

                var iconUrlList :MutableList<Any> = ArrayList()
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


            RoomSeatConstant.WINDOWS_DISMISS -> {       //弹窗消失
                isSeatWindows = false
            }


            RoomSeatConstant.REPORT_USER -> {
                var bundle = Bundle()
                bundle.putString(Constant.REPOTR_TYPE, Constant.REPORT_TYPE_USER)
                bundle.putString(Constant.REPORT_ID, userInfo?.id)
                ActStartUtils.startAct(this, ReportAct::class.java, bundle)
            }

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


}