package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.FamilyDiamondContract
import com.xiaoshanghai.nancang.mvp.presenter.FamilyDiamondPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.FamilyDiamondAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.FamilyDiamondHeaderView
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class FamilyDiamondAct : BaseMvpActivity<FamilyDiamondPresenter>(), FamilyDiamondContract.View, TitleBarClickCallback, OnRefreshLoadMoreListener {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.rcy_friend)
    lateinit var mRcyFriend: RecyclerView

    private var mFamilyInfo:StartRecommendResult? = null

    private val mAdapter by lazy { FamilyDiamondAdapter() }

    private val headerView by lazy { FamilyDiamondHeaderView(this) }

    override fun setLayoutId(): Int = R.layout.activity_family_diamond

    override fun createPresenter(): FamilyDiamondPresenter = FamilyDiamondPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
        mPresenter.getFamilyInfo(SPUtils.getInstance().userInfo.id)
        mPresenter.getFamilyUser(null, SPUtils.getInstance().userInfo.id)
    }

    fun init() {
        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshLoadMoreListener(this)
        mAdapter.setHeaderView(headerView)
        mRcyFriend.layoutManager = LinearLayoutManager(this)
        mRcyFriend.adapter = mAdapter

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = mAdapter.getItem(position)
            var bundle = Bundle()
            bundle.putSerializable(Constant.FAMILY_USER, item)
            bundle.putSerializable(Constant.FAMILY_RESULT, mFamilyInfo)
            ActStartUtils.startAct(this, FamilyUserAct::class.java,bundle)

        }

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getFamilyUser(refreshLayout, SPUtils.getInstance().userInfo.id)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getFamilyUser(refreshLayout, SPUtils.getInstance().userInfo.id)
    }

    override fun refresh(refreshLayout: RefreshLayout?, result: MutableList<FamilyMemberResult>?) {
        refreshLayout?.finishRefresh()
        mAdapter.setList(result)


        if (result?.size!! <= 0) {
            refreshLayout?.finishLoadMoreWithNoMoreData()
        } else {
            refreshLayout?.resetNoMoreData()
        }
    }

    override fun loadMore(refreshLayout: RefreshLayout?, result: MutableList<FamilyMemberResult>?) {


        if (result?.size!! <= 0) {
            refreshLayout?.finishLoadMoreWithNoMoreData()
        } else {
            refreshLayout?.finishLoadMore()
        }

        mAdapter.addData(result)
    }

    override fun onFamilyInfoSuccess(result: StartRecommendResult?) {
        if (result == null) return
        mFamilyInfo = result
        headerView.setFamilyInfo(result)
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

