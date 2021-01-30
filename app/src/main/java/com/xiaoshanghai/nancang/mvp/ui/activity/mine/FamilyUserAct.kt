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
import com.xiaoshanghai.nancang.mvp.contract.FamilyUserContract
import com.xiaoshanghai.nancang.mvp.presenter.FamilyUserPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.FamilyIncomeAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.CharmLevelView
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.mvp.ui.view.UserLevelView
import com.xiaoshanghai.nancang.net.bean.*
import com.xiaoshanghai.nancang.utils.DateUtil
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.CircleImageView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*
import kotlin.collections.ArrayList

class FamilyUserAct : BaseMvpActivity<FamilyUserPresenter>(), FamilyUserContract.View, OnRefreshLoadMoreListener, TitleBarClickCallback {


    @BindView(R.id.tv_date)
    lateinit var tvDate: TextView

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.ryc_record)
    lateinit var mRcyRecord: RecyclerView

    @BindView(R.id.civ_avatar)
    lateinit var mCivAvatar: CircleImageView

    @BindView(R.id.tv_user_name)
    lateinit var mTvUserName: TextView

    @BindView(R.id.ul_user_lv)
    lateinit var mUlv: UserLevelView

    @BindView(R.id.clv_charm_lv)
    lateinit var mClv: CharmLevelView

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

    private var mFamilyUser: FamilyMemberResult? = null

    private var mFamilyResult: StartRecommendResult? = null

    //二级列表
    private var mIncomelist: MutableList<UserIncomeBean> = ArrayList()

    private var mIncome: MutableList<FamilyIncome> = ArrayList()

    private var userId: String? = ""

    private lateinit var mAdapter: FamilyIncomeAdapter

    override fun setLayoutId(): Int = R.layout.activity_family_user

    override fun createPresenter(): FamilyUserPresenter = FamilyUserPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
        mPresenter.getUserIncome(null, userId, mFamilyResult?.clanId,tvDate.text.toString().trim())

//        mPresenter.getMyFamilyInfo(userId)
    }

    private fun init() {

        mFamilyUser = intent.extras.getSerializable(Constant.FAMILY_USER) as FamilyMemberResult?
        mFamilyResult = intent.extras.getSerializable(Constant.FAMILY_RESULT) as StartRecommendResult

        if (mFamilyUser != null) {
            userId = mFamilyUser?.id
            setUserResult(mFamilyUser)
        }

        setMyFamilyInfo(mFamilyResult)

        mTitleBar.setOnClickCallback(this)

        mRefresh.setOnRefreshLoadMoreListener(this)

        val date = Date()
        val dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY)
        tvDate.text = dateToString

        mAdapter = FamilyIncomeAdapter(mIncome)
        mRcyRecord.layoutManager = LinearLayoutManager(this)
        mRcyRecord.adapter = mAdapter

    }

    override fun setTime(date: String?) {
        tvDate.text = date
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getUserIncome(null, userId,mFamilyResult?.clanId, tvDate.text.toString().trim())
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getUserIncome(refreshLayout, userId,mFamilyResult?.clanId, tvDate.text.toString().trim())
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getUserIncome(refreshLayout, userId, mFamilyResult?.clanId,tvDate.text.toString().trim())

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

    fun setUserResult(result: FamilyMemberResult?) {
        if (result == null) return
//        mCivAvatar
        GlideAppUtil.loadImage(this, result.userPicture, mCivAvatar, R.mipmap.icon_default_avatar)
        mTvUserName.text = result.userName
        mUlv.setUserLevel(result.userLevel)
        mClv.setCharmLevel(result.charmLevel)
    }

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
                mPresenter.getUserIncome(null, userId,mFamilyResult?.clanId, tvDate.text.toString().trim())

            }
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }

}