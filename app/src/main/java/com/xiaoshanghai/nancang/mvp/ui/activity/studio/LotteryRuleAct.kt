package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.LotteryRuleContract
import com.xiaoshanghai.nancang.mvp.presenter.LotteryRulePresenter
import com.xiaoshanghai.nancang.net.bean.PrizeResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import java.lang.StringBuilder

class LotteryRuleAct : BaseMvpActivity<LotteryRulePresenter>(), LotteryRuleContract.View {

    @BindView(R.id.tv_rule)
    lateinit var mTvRule: TextView

    @BindView(R.id.iv_bx)
    lateinit var mIvBox: ImageView

    private var mKind = 1

    override fun setLayoutId(): Int = R.layout.activity_lottery_rule


    override fun createPresenter(): LotteryRulePresenter {
        return LotteryRulePresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mKind = intent.extras.getInt(Constant.LOTTERY_TYPE)

        if (mKind == 1) {
            mIvBox.setImageResource(R.mipmap.icon_bybx)
        } else if (mKind == 2) {
            mIvBox.setImageResource(R.mipmap.icon_hjbx)

        }

        mPresenter.getPrize(mKind)

    }

    @OnClick(R.id.iv_close_bx)
    fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_close_bx -> {
                if (mKind == 1) {
                    finish()
                    var bundle = Bundle()
                    bundle.putInt(Constant.LOTTERY_TYPE,mKind)
                    ActStartUtils.startAct(this,LotteryAct::class.java,bundle)
                } else if (mKind == 2) {
                    finish()
                    var bundle = Bundle()
                    bundle.putInt(Constant.LOTTERY_TYPE,mKind)
                    ActStartUtils.startAct(this,LotteryAct::class.java,bundle)
                }
            }
        }

    }

    override fun onPrizeSuccess(mPrize: PrizeResult?) {
        splicingRule(mPrize)
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    private fun splicingRule(mPrize: PrizeResult?) {
        if (mPrize == null) return
        var rule = StringBuilder()

        val one = resources.getString(R.string.rule_one)
        rule.append(one)

        val two = resources.getString(R.string.rule_two)
        rule.append(two)

        val three = resources.getString(R.string.rule_three)
        rule.append(three)

        var four = resources.getString(R.string.rule_four)
        rule.append(four)

        val fives = resources.getString(R.string.rule_fives)
        val fivesFormat = String.format(fives, "${mPrize?.drawCost1}")
        rule.append(fivesFormat)

        var six = resources.getString(R.string.rule_six)
        rule.append(six)

        var seven = resources.getString(R.string.rule_seven)
        rule.append(seven)

        val eight = resources.getString(R.string.rule_eight)
        rule.append(eight)

        val items = mPrize?.items
        items?.reverse()


        for (item in items!!) {
            val eight_1 = resources.getString(R.string.rule_eight_1)
            val eightFrom = String.format(eight_1, "${item.gift?.giftPrice}", "${item.drawRate}%")
            rule.append(eightFrom)
        }

        val nine = resources.getString(R.string.rule_nine)
        rule.append(nine)

        val ten = resources.getString(R.string.rule_ten)
        rule.append(ten)


        val ruleString = rule.toString()

        mTvRule.text = ruleString

    }
}