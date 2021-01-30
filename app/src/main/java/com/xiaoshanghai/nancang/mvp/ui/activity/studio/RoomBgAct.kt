package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.mvp.contract.RoomBgConstract
import com.xiaoshanghai.nancang.mvp.presenter.RoomBgPresenter
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.NobleWebActivity
import com.xiaoshanghai.nancang.mvp.ui.view.NobleDialog
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.Event
import com.xiaoshanghai.nancang.net.bean.RoomBgEntity
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.CircleImageView

class RoomBgAct : BaseMvpActivity<RoomBgPresenter>(), RoomBgConstract.View, TitleBarClickCallback {

    protected val TAG = VoiceBaseRoomAct::class.java.name

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.iv_room_bg)
    lateinit var mIvRoomBg: ImageView

    @BindView(R.id.civ_room_bg)
    lateinit var mCivRoomBg: CircleImageView

    @BindView(R.id.iv_noble)
    lateinit var mIvNoble: ImageView

    @BindView(R.id.tv_bg_name)
    lateinit var mTvBgName: TextView

    @BindView(R.id.tv_text)
    lateinit var mTvText: TextView

    private var mRoomBgEntity: RoomBgEntity? = null

    private var mRoomId: String? = null

    private var selfNoble = 0

    override fun setLayoutId(): Int = R.layout.activity_room_bg

    override fun createPresenter(): RoomBgPresenter = RoomBgPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mRoomBgEntity = intent.extras.getSerializable(Constant.ROOM_BG) as RoomBgEntity?
        mRoomId = intent.extras.getString(Constant.ROOM_ID)
        mPresenter.getMyNoble()
        init()
    }

    private fun init() {

        mTitleBar.setOnClickCallback(this)

        GlideAppUtil.loadImage(this, mRoomBgEntity?.backgroundUrl, mIvRoomBg)

        GlideAppUtil.loadImage(this, mRoomBgEntity?.backgroundUrl, mCivRoomBg)

        if (UserManagerUtils.nobleImage(mRoomBgEntity?.nobleId!!) == 0) {

            mIvNoble.visibility = View.GONE
        } else {
            mIvNoble.visibility = View.VISIBLE
        }

        mIvNoble.setImageResource(UserManagerUtils.nobleImage(mRoomBgEntity?.nobleId!!))


        mTvBgName.text = mRoomBgEntity?.backgroundName

        mTvText.text = "${UserManagerUtils.userNoble(mRoomBgEntity?.nobleId!!)}及以上贵族可设置"


    }

    @OnClick(R.id.tv_confirm)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_confirm -> {
                mPresenter.setRoomBg(mRoomId, SPUtils.getInstance().userInfo.id, mRoomBgEntity)
            }
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }

    override fun onSuccess(status: Int?, roomBg: RoomBgEntity?) {
        if (status == 1) {

            EventBusUtil.sendEvent(Event(EventConstant.ROOM_BG,roomBg?.backgroundUrl))

            ToastUtils.showShort("房间背景设置成功")

        } else {
            NobleDialog.newInstance(selfNoble.toString(),roomBg?.nobleId.toString()).also {
                it.setOnClick(View.OnClickListener {
                    ActStartUtils.startAct(this, NobleWebActivity::class.java)
                })

                it.show(supportFragmentManager, TAG)
            }
        }
    }

    override fun onNobleSuccess(noble: Int?) {
        selfNoble = noble!!
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }
}
