package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.OnLineContract
import com.xiaoshanghai.nancang.mvp.presenter.OnLinePresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.OnLineAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.CreateRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLinePick
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class OnLineAct : BaseMvpActivity<OnLinePresenter>(), OnLineContract.View, TitleBarClickCallback, OnRefreshLoadMoreListener {

    @BindView(R.id.iv_room_bg)
    lateinit var mIvRoomBg: ImageView

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.rcy_online)
    lateinit var mRcyOnLine: RecyclerView

    private var mOnLineMark = -1

    private var mRoomSeatIndex = -1

    private val mAdapter by lazy { OnLineAdapter() }

    private var mRoomInfo: CreateRoomResult? = null   //联网获取的房间信息


    override fun setLayoutId(): Int = R.layout.activity_on_line

    override fun createPresenter(): OnLinePresenter {
        return OnLinePresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
        mPresenter.loadOnLineUser(null, mRoomInfo?.id)

    }

    private fun init() {

        mRoomInfo = intent.extras.getSerializable(Constant.ROOM_INFO) as CreateRoomResult?

        mOnLineMark = intent.extras.getInt(Constant.ROOM_MARK, -1)

        mRoomSeatIndex = intent.extras.getInt(Constant.ROOM_SEAT_INDEX, -1)

        GlideAppUtil.loadImage(this, mRoomInfo?.roomBgPicture, mIvRoomBg)

        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshLoadMoreListener(this)

        mRcyOnLine.layoutManager = LinearLayoutManager(this)
        mRcyOnLine.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mAdapter.getItem(position)
            if (item.userType != 0) {
                if (mOnLineMark == Constant.ROOM_PICK) {
                    val onLinePick = OnLinePick(item.userId, mRoomSeatIndex)
                    val intent = Intent()
                    intent.putExtra(Constant.ON_LINE_USER_INFO, onLinePick)
                    setResult(RESULT_OK, intent)
                    finish()
                }
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


}