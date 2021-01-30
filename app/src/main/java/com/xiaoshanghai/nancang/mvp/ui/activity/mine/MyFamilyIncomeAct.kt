package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.MyFamilyIncomeContract
import com.xiaoshanghai.nancang.mvp.presenter.MyFamilyIncomePresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.MyFamilyIncomeAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.*
import com.xiaoshanghai.nancang.utils.DateUtil
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.CircleImageView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*
import kotlin.collections.ArrayList

class MyFamilyIncomeAct : BaseMvpActivity<MyFamilyIncomePresenter>(), MyFamilyIncomeContract.View, OnRefreshLoadMoreListener, TitleBarClickCallback {

    @BindView(R.id.tv_date)
    lateinit var tvDate: TextView

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.ryc_record)
    lateinit var mRcyRecord: RecyclerView

    @BindView(R.id.iv_family_logo)
    lateinit var ivFamilyLogo: CircleImageView

    @BindView(R.id.iv_family_bg)
    lateinit var ivFamilyBg: ImageView

    @BindView(R.id.tv_family_name)
    lateinit var tvFamilyName: TextView

    @BindView(R.id.tv_family_id)
    lateinit var tvFamilyId: TextView

    @BindView(R.id.tv_family_num)
    lateinit var tvFamilyNum: TextView


    private var mFamilyResult: StartRecommendResult? = null

    //二级列表
    private var mIncomelist: MutableList<UserIncomeBean> = ArrayList()

    private var mIncome: MutableList<FamilyIncome> = ArrayList()

    private var userId: String? = ""

    private lateinit var mAdapter: MyFamilyIncomeAdapter

    override fun setLayoutId(): Int = R.layout.activity_my_family_income

    override fun createPresenter(): MyFamilyIncomePresenter = MyFamilyIncomePresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
        mPresenter.getFamilyIncome(null, userId, mFamilyResult?.clanId, tvDate.text.toString().trim())

    }

    private fun init() {

        mFamilyResult = intent.extras.getSerializable(Constant.FAMILY_RESULT) as StartRecommendResult

        userId = SPUtils.getInstance().userInfo.id

        setMyFamilyInfo(mFamilyResult)

        mTitleBar.setOnClickCallback(this)

        mRefresh.setOnRefreshLoadMoreListener(this)

        val date = Date()
        val dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY)
        tvDate.text = dateToString

        mAdapter = MyFamilyIncomeAdapter(mIncome)
        mRcyRecord.layoutManager = LinearLayoutManager(this)
        mRcyRecord.adapter = mAdapter

    }

    override fun setTime(date: String?) {
        tvDate.text = date
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getFamilyIncome(null, userId, mFamilyResult?.clanId, tvDate.text.toString().trim())
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getFamilyIncome(refreshLayout, userId,mFamilyResult?.clanId,  tvDate.text.toString().trim())
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getFamilyIncome(refreshLayout, userId, mFamilyResult?.clanId, tvDate.text.toString().trim())

    }

    override fun refreshSuccess(refresh: RefreshLayout?, incomeList: MutableList<UserIncome>?) {

        refresh?.finishRefresh()

        mIncomelist.clear()

        mIncome.clear()

        dataSortOut(incomeList)

        sortOutList()

        mAdapter.notifyDataSetChanged()

        if (incomeList?.size!! <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.resetNoMoreData()
        }

    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, incomeList: MutableList<UserIncome>?) {
        refresh?.finishRefresh()

        if (incomeList?.size!! <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.finishLoadMore()
        }

        dataSortOut(incomeList)

        sortOutList()

        mAdapter.notifyDataSetChanged()
    }

    override fun onFamilySuccess(fammilyResult: MyFamilyResult?) {
//        setMyFamilyInfo(fammilyResult)
    }

    private fun setMyFamilyInfo(fammilyResult: StartRecommendResult?) {
        if (fammilyResult == null) return

        GlideAppUtil.loadImage(this, fammilyResult.clanPicture, ivFamilyBg)
        GlideAppUtil.loadImage(this, fammilyResult.clanPicture, ivFamilyLogo)

        tvFamilyName.text = fammilyResult.clanName

        tvFamilyId.text = fammilyResult.clanNumber.toString()

        tvFamilyNum.text = fammilyResult.member.toString()

    }

//    fun setUserResult(result: FamilyMemberResult? ){
//        if (result == null) return
////        mCivAvatar
//        GlideAppUtil.loadImage(this,result.userPicture,mCivAvatar,R.mipmap.icon_default_avatar)
//        mTvUserName.text = result.userName
//        mUlv.setUserLevel(result.userLevel)
//        mClv.setCharmLevel(result.charmLevel)
//    }

    private fun dataSortOut(incomeList: MutableList<UserIncome>?) {
        if (incomeList == null) return
        for (i in incomeList.indices) {

            var isSelect = false

            for (j in mIncomelist.indices) {

                if (incomeList[i].date == mIncomelist[j].date) {
                    mIncomelist[j].userIncomes?.add(incomeList[i])
                    isSelect = true
                    break
                }
            }

            if (!isSelect) {
                var userIncomes: MutableList<UserIncome> = ArrayList()
                userIncomes.add(incomeList[i])
                var userIncomeBean = UserIncomeBean(incomeList[i].date, userIncomes)
                mIncomelist.add(userIncomeBean)
            }
        }
    }

    private fun sortOutList() {
        mIncome.clear()
        for (incomeBean in mIncomelist) {
            var familyIncome = FamilyIncome(true, incomeBean.date, null)
            mIncome.add(familyIncome)
            val userIncomes = incomeBean.userIncomes
            if (userIncomes != null)
                for (userIncome in userIncomes) {
                    var familyIncome = FamilyIncome(false, null, userIncome)
                    mIncome.add(familyIncome)
                }
        }
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }


    @OnClick(R.id.iv_calendar, R.id.iv_now_day)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.iv_calendar -> {
                mPresenter.selectTime(tvDate.text.toString().trim())
            }
            R.id.iv_now_day -> {
                val date = Date()
                val dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY)
                tvDate.text = dateToString
                mPresenter.mPage = mPresenter.initPage
                mPresenter.getFamilyIncome(null, userId,mFamilyResult?.clanId,  tvDate.text.toString().trim())

            }
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}