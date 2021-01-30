package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.BlackListConstract
import com.xiaoshanghai.nancang.mvp.presenter.BlackListPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomBlackListAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.RoomBlackListEntity
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.TipsDialog
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class BlackListAct : BaseMvpActivity<BlackListPresenter>(), BlackListConstract.View, TitleBarClickCallback, OnRefreshLoadMoreListener {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.rcy_black_list)
    lateinit var mRcyBlackList: RecyclerView

    private var mRoomId: String? = null


    private val mAdapter by lazy { RoomBlackListAdapter() }

    override fun setLayoutId(): Int = R.layout.activity_black_list


    override fun createPresenter(): BlackListPresenter {
        return BlackListPresenter()
    }


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)

        mRoomId = intent.extras.getString(Constant.ROOM_ID)
        init()
        mPresenter.getRoomBlackList(null, mRoomId)

    }

    fun init() {
        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshLoadMoreListener(this)

        mRcyBlackList.layoutManager = LinearLayoutManager(this)
        mRcyBlackList.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->

            val item = mAdapter.getItem(position)

            var tipsDialog = TipsDialog.createDialog(this, R.layout.dialog_room_manager)

            tipsDialog.setText(R.id.tv_name, item.userName)
            tipsDialog.bindClick(R.id.tv_cancel) { v, dialog ->
                dialog.dismiss()
            }
                    .bindClick(R.id.tv_confirm) { v, dialog ->
//                        mPresenter.removeRoomManager(mRoomId, item)
                        mPresenter.removeBlack(mRoomId!!, item)
                        dialog.dismiss()
                    }
                    .show()
        }

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getRoomBlackList(refreshLayout, mRoomId)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getRoomBlackList(refreshLayout, mRoomId)
    }

    override fun refreshSuccess(refresh: RefreshLayout?, roomBlack: MutableList<RoomBlackListEntity>) {
        refresh?.finishRefresh()

        mAdapter.setList(roomBlack)

        if (roomBlack.size <= 0) {
//            mAdapter.setEmptyView(R.layout.fans_empty)
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.resetNoMoreData()
        }

    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, roomBlack: MutableList<RoomBlackListEntity>) {
        if (roomBlack.size <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.finishLoadMore()
            mAdapter.addData(roomBlack)
        }
    }

    override fun removeSuccess(status: Int, blackUser: RoomBlackListEntity) {
        if (status == 1) {
            mAdapter.remove(blackUser)
        } else {
            ToastUtils.showShort("移除失败")
        }
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }


}
