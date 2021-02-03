package com.xiaoshanghai.nancang.mvp.ui.activity.square

import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.TopicNotifcContract
import com.xiaoshanghai.nancang.mvp.presenter.TopicNotifcPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.TopicNotificAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.TopicMsg
import com.xiaoshanghai.nancang.net.bean.UserBean
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoshanghai.nancang.bean.MessageWrap
import org.greenrobot.eventbus.EventBus

class TopicNotificAct : BaseMvpActivity<TopicNotifcPresenter>(), TopicNotifcContract.View, TitleBarClickCallback, OnRefreshLoadMoreListener {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: RefreshLayout

    @BindView(R.id.rcy_msg)
    lateinit var mRcyMsg: RecyclerView

    private var userInfo: UserBean? = null

    private val mAdapter by lazy { TopicNotificAdapter() }

    override fun setLayoutId(): Int = R.layout.activity_topic_notific

    override fun createPresenter(): TopicNotifcPresenter = TopicNotifcPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        userInfo = SPUtils.getInstance().userInfo
        init()
        mPresenter.getTopicList(null, userInfo?.id)
    }

    private fun init() {
        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshLoadMoreListener(this)

        mRcyMsg.layoutManager = LinearLayoutManager(this)
        mRcyMsg.adapter = mAdapter

        mAdapter.setOnItemClickListener{
            adapter, view, position ->
            var item = mAdapter.getItem(position)
            var bundle = Bundle()
            bundle.putString(Constant.TOPIC_ID, item.topicId)
            ActStartUtils.startAct(this, MessageDetailsAct::class.java, bundle)
        }

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter?.getTopicList(refreshLayout, userInfo?.id)

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getTopicList(refreshLayout, userInfo?.id)

    }

    override fun refreshSuccess(refresh: RefreshLayout?, topicMsgs: MutableList<TopicMsg>) {
        refresh?.finishRefresh()

        mAdapter.setList(topicMsgs)

        if (topicMsgs.size <= 0) {
//            mAdapter.setEmptyView(R.layout.fans_empty)
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.resetNoMoreData()
        }
    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, topicMsgs: MutableList<TopicMsg>) {
        if (topicMsgs.size <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.finishLoadMore()
            mAdapter.addData(topicMsgs)
        }

    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }
    override fun titleLeftClick() {
        EventBus.getDefault().post( MessageWrap())
        finish()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        EventBus.getDefault().post( MessageWrap())
        finish()
        return true
    }
    override fun titleRightClick(status: Int) {
    }


}