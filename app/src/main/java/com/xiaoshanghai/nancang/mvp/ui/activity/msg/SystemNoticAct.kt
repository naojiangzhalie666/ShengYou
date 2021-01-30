package com.xiaoshanghai.nancang.mvp.ui.activity.msg

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.mvp.contract.SystemNoticContract
import com.xiaoshanghai.nancang.mvp.presenter.SystemNoticPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.SysNoticAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.SystemNotic
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

class SystemNoticAct : BaseMvpActivity<SystemNoticPresenter>(), SystemNoticContract.View, OnRefreshListener, TitleBarClickCallback {


    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.rcy_sys_notic)
    lateinit var mRcyNotic: RecyclerView

    private var mAdapter: SysNoticAdapter = SysNoticAdapter()


    override fun setLayoutId(): Int = R.layout.activity_system_notic

    override fun createPresenter(): SystemNoticPresenter = SystemNoticPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
        mPresenter.getSystemNoic(null)

    }

    private fun init() {
        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshListener(this)
        mRefresh.setEnableLoadMore(false)

        mRcyNotic.layoutManager = LinearLayoutManager(this)
        mRcyNotic.adapter = mAdapter


    }

    override fun refreshSuccess(refresh: RefreshLayout?, systemNotics: MutableList<SystemNotic>?) {
        mAdapter.setList(systemNotics)
        if (systemNotics != null) {
            mRcyNotic.smoothScrollToPosition(mAdapter.itemCount)
        }
    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, systemNotics: MutableList<SystemNotic>?) {
        refresh?.finishRefresh()

        if (systemNotics == null || systemNotics.size <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.finishLoadMore()
            mAdapter?.addData(0, systemNotics)
        }
    }


    override fun onError(msg: String?) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.getSystemNoic(refreshLayout)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }


}