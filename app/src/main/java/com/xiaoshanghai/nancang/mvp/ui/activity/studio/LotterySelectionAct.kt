package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.LotterSelectionConstract
import com.xiaoshanghai.nancang.mvp.presenter.LotterySelectionPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.PrizePoolAdapter
import com.xiaoshanghai.nancang.net.bean.PrizePoolEntity
import com.xiaoshanghai.nancang.net.bean.PrizeResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.GridDecoration

class LotterySelectionAct : BaseMvpActivity<LotterySelectionPresenter>(), LotterSelectionConstract.View {

    @BindView(R.id.constrain_1)
    lateinit var mConstrain: ConstraintLayout

    @BindView(R.id.con_prize_pool)
    lateinit var mConPrizePool: ConstraintLayout

    @BindView(R.id.iv_bx)
    lateinit var mIvBx: ImageView

    @BindView(R.id.tv_bx_name)
    lateinit var mTvBxName: TextView

    @BindView(R.id.tv_bx_jcwp)
    lateinit var mTvBxJcwp: TextView

    @BindView(R.id.rcy_lottery)
    lateinit var mRcyLottery: RecyclerView

    private val mAdapter by lazy { PrizePoolAdapter() }

    override fun setLayoutId(): Int = R.layout.activity_lottery_selection


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
    }

    private fun init() {
        mRcyLottery.layoutManager = GridLayoutManager(this, 5)
        mRcyLottery.addItemDecoration(GridDecoration(10, 15, 20, 20, 5))
        mRcyLottery.adapter = mAdapter
    }

    @OnClick(R.id.iv_finish, R.id.iv_bybx, R.id.iv_hjbx, R.id.tv_byjp, R.id.tv_hjjp, R.id.iv_right_finish)
    fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_finish -> {
                finish()
            }

            R.id.iv_bybx -> {
                finish()
                var bundle = Bundle()
                bundle.putInt(Constant.LOTTERY_TYPE,1)
                ActStartUtils.startAct(this,LotteryAct::class.java,bundle)
            }

            R.id.iv_hjbx -> {
                finish()
                var bundle = Bundle()
                bundle.putInt(Constant.LOTTERY_TYPE,2)
                ActStartUtils.startAct(this,LotteryAct::class.java,bundle)
            }

            R.id.tv_byjp -> {
                mPresenter.getPrize(1)

            }

            R.id.tv_hjjp -> {
                mPresenter.getPrize(2)

            }

            R.id.iv_right_finish -> {
                mConstrain.visibility = View.VISIBLE
                mConPrizePool.visibility = View.GONE
            }
        }
    }

    override fun createPresenter(): LotterySelectionPresenter {
        return LotterySelectionPresenter()
    }

    override fun onPrizeSuccess(mPrize: PrizeResult?) {
        setBoxResult(mPrize?.drawKind!!, mPrize.items)

    }

    override fun onError(msg: String?) {
        ToastUtils.showLong(msg)
    }

    fun setBoxResult(type: Int, prize: MutableList<PrizePoolEntity>?) {
        if (prize == null || prize.size == 0) return
        if (type == 1) {
            mIvBx.setImageResource(R.mipmap.icon_bybx)
            mTvBxName.setTextColor(ContextCompat.getColor(this, R.color.color_e0e2f0))
            mTvBxJcwp.setTextColor(ContextCompat.getColor(this, R.color.color_eef0f6))
            mTvBxName.text = "白银宝箱"

        } else if (type == 2) {
            mIvBx.setImageResource(R.mipmap.icon_hjbx)
            mTvBxName.setTextColor(ContextCompat.getColor(this, R.color.color_fbb332))
            mTvBxJcwp.setTextColor(ContextCompat.getColor(this, R.color.color_e9ba2a))
            mTvBxName.text = "黄金宝箱"
        }

        mAdapter.setType(type)
        mAdapter.data = prize!!
        mAdapter.notifyDataSetChanged()
        mConstrain.visibility = View.GONE
        mConPrizePool.visibility = View.VISIBLE
    }


}