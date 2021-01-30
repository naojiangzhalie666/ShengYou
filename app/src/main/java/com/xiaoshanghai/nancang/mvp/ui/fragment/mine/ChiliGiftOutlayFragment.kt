package com.xiaoshanghai.nancang.mvp.ui.fragment.mine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpFragment
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.ChiliGiftOutlayContract
import com.xiaoshanghai.nancang.mvp.presenter.ChiliGiftOutlayPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.GoldGiftIncomeAdapter
import com.xiaoshanghai.nancang.net.bean.GoldGiftHeader
import com.xiaoshanghai.nancang.net.bean.GoldGiftHeaderResult
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult
import com.xiaoshanghai.nancang.utils.DateUtil
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*

class ChiliGiftOutlayFragment : BaseMvpFragment<ChiliGiftOutlayPresenter>(), ChiliGiftOutlayContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_date)
    lateinit  var tvDate: TextView
    @BindView(R.id.iv_calendar)
    lateinit var ivCalendar: ImageView
    @BindView(R.id.iv_now_day)
    lateinit var ivNowDay: ImageView
    @BindView(R.id.cv_date_info)
    lateinit var cvDateInfo: CardView
    @BindView(R.id.ryc_record)
    lateinit  var rycRecord: RecyclerView
    @BindView(R.id.refresh)
    lateinit var refresh: SmartRefreshLayout

    private val list = ArrayList<GoldGiftHeader>()

    private val header = ArrayList<GoldGiftHeaderResult>()

    lateinit var mAdapter: GoldGiftIncomeAdapter

    companion object {
        fun newInstance(): ChiliGiftOutlayFragment {
            return ChiliGiftOutlayFragment()
        }
    }

    override fun setLayoutId(): Int = R.layout.fragment_chili_gift_outlay

    override fun createPresenter(): ChiliGiftOutlayPresenter = ChiliGiftOutlayPresenter()

    private fun init() {
        mPresenter.attachView(this)
        refresh!!.setOnRefreshLoadMoreListener(this)
        val date = Date()
        val dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY)
        tvDate!!.text = dateToString
        mAdapter = GoldGiftIncomeAdapter(header, Constant.MY_GIFT_CHILI_OUTLAY)
        rycRecord!!.layoutManager = LinearLayoutManager(activity)
        rycRecord!!.adapter = mAdapter
    }

    override fun initView(savedInstanceState: Bundle?) {
        init()
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getChiliGiftOutlayList(null, tvDate.text.toString().trim())
    }

    @OnClick(R.id.iv_calendar, R.id.iv_now_day)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.iv_calendar -> mPresenter.selectTime(tvDate!!.text.toString().trim())
            R.id.iv_now_day -> {
                val date = Date()
                val dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY)
                tvDate!!.text = dateToString
                mPresenter.mPage = mPresenter.initPage
                mPresenter.getChiliGiftOutlayList(null, tvDate!!.text.toString().trim())
            }
        }
    }


    override fun setTime(date: String?) {
        tvDate!!.text = date
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getChiliGiftOutlayList(null, tvDate!!.text.toString().trim { it <= ' ' })
    }

    override fun refreshSuccess(refresh: RefreshLayout?, incomeList: List<GoldGiftResult?>?) {
        refresh?.finishRefresh()
        list.clear()
        header.clear()
        tissue(incomeList)
        initHeader()
        if (header.size <= 0) {
            mAdapter?.setEmptyView(R.layout.gift_outlay_empty)
        }
        mAdapter?.notifyDataSetChanged()
        if (incomeList!!.isEmpty()) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.resetNoMoreData()
        }
    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, incomeList: List<GoldGiftResult?>?) {
        if (incomeList!!.isNotEmpty()) {
            refresh?.finishLoadMore()
        } else {
            refresh?.finishLoadMoreWithNoMoreData()
        }
        tissue(incomeList)
        initHeader()
        mAdapter?.notifyDataSetChanged()
    }


    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getChiliGiftOutlayList(refreshLayout, tvDate!!.text.toString().trim ())
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getChiliGiftOutlayList(refreshLayout, tvDate!!.text.toString().trim ())
    }


    private fun initHeader() {
        header.clear()
        for (chiliIncomeHeaderResult in list) {
            val head = GoldGiftHeaderResult(true, chiliIncomeHeaderResult.date, null)
            header.add(head)
            val result = chiliIncomeHeaderResult.result
            for (chiliIncomeResult in result) {
                val body = GoldGiftHeaderResult(false, null, chiliIncomeResult)
                header.add(body)
            }
        }
    }

    private fun tissue(incomeList: List<GoldGiftResult?>?) {
        for (i in incomeList!!.indices) {
            var isSelect = false
            for (j in list.indices) {
                if (incomeList[i]!!.date == list[j].date) {
                    list[j].result.add(incomeList[i])
                    isSelect = true
                    break
                }
            }
            if (!isSelect) {
                val bean = GoldGiftHeader()
                bean.date = incomeList[i]!!.date
                val results = ArrayList<GoldGiftResult>()
                results.add(incomeList[i]!!)
                bean.result = results
                list.add(bean)
            }
        }
    }
}