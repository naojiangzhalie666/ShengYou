package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.mvp.contract.RoomManagerContract
import com.xiaoshanghai.nancang.mvp.presenter.RoomManagerPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomManagerAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.Event
import com.xiaoshanghai.nancang.net.bean.RoomManagerEntity
import com.xiaoshanghai.nancang.utils.EventBusUtil
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.TipsDialog

class RoomManagerAct : BaseMvpActivity<RoomManagerPresenter>(), RoomManagerContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.rcy_manager)
    lateinit var mRcyManager: RecyclerView

    private val mAdapter by lazy { RoomManagerAdapter() }

    private var mRoomId: String? = null

    override fun setLayoutId(): Int = R.layout.activity_room_manager


    override fun createPresenter(): RoomManagerPresenter {
        return RoomManagerPresenter()
    }


    override fun initView(savedInstanceState: Bundle?) {
        mRoomId = intent.extras.getString(Constant.ROOM_ID)
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        init()

        mPresenter.getRoomManager(mRoomId)

    }

    fun init() {
        mRcyManager.layoutManager = LinearLayoutManager(this)
        mRcyManager.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->

            val item = mAdapter.getItem(position)

            var tipsDialog = TipsDialog.createDialog(this, R.layout.dialog_room_manager)

            tipsDialog.setText(R.id.tv_name, item.userName)
            tipsDialog.bindClick(R.id.tv_cancel) { v, dialog ->
                dialog.dismiss()
            }
                    .bindClick(R.id.tv_confirm) { v, dialog ->
                        mPresenter.removeRoomManager(mRoomId, item)
                        dialog.dismiss()
                    }
                    .show()
        }


    }

    override fun onRoomManagerSuccess(roomManager: MutableList<RoomManagerEntity>?) {
        mAdapter.data = roomManager!!
        mAdapter.notifyDataSetChanged()
    }

    override fun onRemoveSuccess(status: Int?, user: RoomManagerEntity?) {

        if (status == 1) {
            ToastUtils.showShort("移除管理员成功")
            EventBusUtil.sendEvent(Event(EventConstant.ROOM_REMOVE_MANAGER, user?.userId))
            mAdapter.remove(user!!)
            mAdapter.notifyDataSetChanged()

        }

    }

    override fun onError(msg: String?) {

    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }
}
