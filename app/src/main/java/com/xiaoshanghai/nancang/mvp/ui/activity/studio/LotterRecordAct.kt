package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.LotteryRecordContract
import com.xiaoshanghai.nancang.mvp.presenter.LotteryRecordPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.LotterRecordAdapter
import com.xiaoshanghai.nancang.net.bean.AwardsEntity
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.ToastUtils

class LotterRecordAct : BaseMvpActivity<LotteryRecordPresenter>(), LotteryRecordContract.View {

    @BindView(R.id.iv_bx_bg)
    lateinit var mIvBxBg: ImageView

    @BindView(R.id.tv_open)
    lateinit var mTvOpen: TextView

    @BindView(R.id.tv_bx_type)
    lateinit var mTvBoxType: TextView

    @BindView(R.id.rcy_record)
    lateinit var mRcyRecord: RecyclerView


    private var mKind = 1

    private val mAdapter: LotterRecordAdapter by lazy { LotterRecordAdapter() }

    override fun setLayoutId(): Int = R.layout.activity_lotter_record

    override fun createPresenter(): LotteryRecordPresenter {
        return LotteryRecordPresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mKind = intent.extras.getInt(Constant.LOTTERY_TYPE)
        init()
        mPresenter.getLotterRecord(mKind)
    }

    private fun init() {
        if (mKind == 1) {
            mIvBxBg.setImageResource(R.mipmap.img_bybx_bg)
            mTvOpen.setTextColor(ContextCompat.getColor(this, R.color.color_e0e2f0))
            mTvBoxType.setTextColor(ContextCompat.getColor(this, R.color.color_e0e2f0))
            mTvBoxType.text = "白银宝箱获得了"
        } else if (mKind == 2) {
            mTvOpen.setTextColor(ContextCompat.getColor(this, R.color.color_f2b600))
            mTvBoxType.setTextColor(ContextCompat.getColor(this, R.color.color_f2b600))
            mTvBoxType.text = "黄金宝箱获得了"
            mIvBxBg.setImageResource(R.mipmap.img_hjbx_bg)

        }

        mRcyRecord.layoutManager = LinearLayoutManager(this)
        mRcyRecord.adapter = mAdapter
    }

    @OnClick(R.id.iv_close, R.id.tv_know)
    fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_close -> {
                if (mKind == 1) {
                    finish()
                    var bundle = Bundle()
                    bundle.putInt(Constant.LOTTERY_TYPE, mKind)
                    ActStartUtils.startAct(this, LotteryAct::class.java, bundle)
                } else if (mKind == 2) {
                    finish()
                    var bundle = Bundle()
                    bundle.putInt(Constant.LOTTERY_TYPE, mKind)
                    ActStartUtils.startAct(this, LotteryAct::class.java, bundle)
                }
            }

            R.id.tv_know -> {
                if (mKind == 1) {
                    finish()
                    var bundle = Bundle()
                    bundle.putInt(Constant.LOTTERY_TYPE, mKind)
                    ActStartUtils.startAct(this, LotteryAct::class.java, bundle)
                } else if (mKind == 2) {
                    finish()
                    var bundle = Bundle()
                    bundle.putInt(Constant.LOTTERY_TYPE, mKind)
                    ActStartUtils.startAct(this, LotteryAct::class.java, bundle)
                }
            }
        }
    }

    override fun onRecordSuccess(record: MutableList<AwardsEntity>?) {

        if (record != null && record.size > 0) {
            mAdapter.data = record!!
            mAdapter.notifyDataSetChanged()
        }

    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }
}