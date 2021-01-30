package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseApplication
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.OnNobleOrderHeaderCallback
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.NobleOrderContract
import com.xiaoshanghai.nancang.mvp.presenter.NobleOrderPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.NobleAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.NobleOrderHeader
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.BuyGoldResult
import com.xiaoshanghai.nancang.net.bean.NobleListBean
import com.xiaoshanghai.nancang.net.bean.NobleResult
import com.xiaoshanghai.nancang.net.bean.PayResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.HeardGridDecoration
import com.xiaoshanghai.nancang.view.TipsDialog
import java.text.NumberFormat
import java.util.*

class NobleOrderAct : BaseMvpActivity<NobleOrderPresenter>(), NobleOrderContract.View,
        TitleBarClickCallback, OnNobleOrderHeaderCallback {

    @BindView(R.id.title_bar)
    lateinit var titleBar: TitleBarView

    @BindView(R.id.rcy_noble)
    lateinit var rcyNoble: RecyclerView

    @BindView(R.id.tv_money)
    lateinit var mTvMoney: TextView

    @BindView(R.id.tv_money_type)
    lateinit var mTvMoneyType: TextView


    private val SDK_PAY_FLAG = 1
    private val SDK_AUTH_FLAG = 2

    private val mAdapter: NobleAdapter by lazy { NobleAdapter() }

    private val mHeader: NobleOrderHeader by lazy { NobleOrderHeader(this) }

    private var mGold: Double = 0.0

    private var mNoble: Int = 0

    lateinit var mResults: MutableList<NobleResult>

    var result: NobleResult? = null

    override fun setLayoutId(): Int = R.layout.activity_noble_order

    override fun createPresenter(): NobleOrderPresenter = NobleOrderPresenter()

    private fun init() {
        mPresenter.attachView(this)
        titleBar.setOnClickCallback(this)
        val extras = intent.extras
        var nobleResult = extras.getSerializable(Constant.NOBLE_LIST) as NobleListBean
        mResults = nobleResult.result


        for (result in mResults) {
            if (result.isSelect) {
                this.result = result
            }
        }

        setButtonResutl()


        rcyNoble.layoutManager = GridLayoutManager(this, 2)
        rcyNoble.addItemDecoration(HeardGridDecoration(16, 16, 17, 17, 2))

        mHeader.setAdapter(mAdapter)
        mHeader.setOnNobleOrderHeaderCallback(this)
        rcyNoble.adapter = mAdapter
        mAdapter.setHeaderView(mHeader)
        mAdapter.setType(1)
        mAdapter.setList(mResults)

        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            this@NobleOrderAct.result?.isSelect = false
            this@NobleOrderAct.result = item
            this@NobleOrderAct.result?.isSelect = true
            setButtonResutl()
            mAdapter.notifyDataSetChanged()
        }

    }


    override fun initView(savedInstanceState: Bundle?) {
        init()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getMyGoldNum()
        mPresenter.getNoble()
    }

    @OnClick(R.id.ll_buy)
    fun onClick(v: View) {
        when (v.id) {
            R.id.ll_buy -> {

                if (result == null) return

                var title: String? = null
                if (mHeader.getType() == 1) {

                    title = if (result?.ifBuy == 0) {
                        result?.againCoinPrice.toString() + "金币"
                    } else {
                        result?.coinPrice.toString() + "金币"
                    }
                } else if (mHeader.getType() == 2 || mHeader.getType() == 3) {

                    title = if (result?.ifBuy == 0) {
                        "¥" + NumberFormat.getNumberInstance(Locale.US).format(result?.againRmbPrice)
                    } else {
                        "¥" + NumberFormat.getNumberInstance(Locale.US).format(result?.rmbPrice)
                    }
                }

                TipsDialog.createDialog(this, R.layout.dialog_headwear_store)
                        .setText(R.id.tv_title, "支付提示")
                        .setText(R.id.tv_gift_info, "是否确认支付")
                        .setText(R.id.tv_price, title)
                        .bindClick(R.id.tv_cancel) { _, dialog -> dialog?.dismiss() }
                        .bindClick(R.id.tv_comit) { _, _ -> buyNoble(type = mHeader.getType()) }
                        .show()


            }
        }
    }

    private fun buyNoble(type: Int) {

        if (result == null) return

        if ((Integer.valueOf(result?.id)) <= this.mNoble) {
            ToastUtils.showShort("低于当前贵族无法购买")
            return
        }

        when (type) {
            1 -> {
                //金币购买判断是否为续费
                if (result?.ifBuy == 0) {

                    if (this.mGold >= result!!.againCoinPrice) {
                        // 所持金币大于需要续费金币  续费
                        mPresenter.goldBuy(result!!.id)

                    } else {
                        ToastUtils.showShort("金币余额不足")
                    }

                } else {

                    if (this.mGold >= result!!.coinPrice) {
                        // 所持金币大于需要续费金币  续费
                        mPresenter.goldBuy(result!!.id)

                    } else {
                        ToastUtils.showShort("金币余额不足")
                    }

                }

            }

            2 -> {
                mPresenter.aliBuy(result!!.id)
            }

            3 -> {
                mPresenter.wxBuy(result!!.id)

            }
        }


    }

    override fun goldNumSuccess(gold: Double) {
        this.mGold = gold
        mHeader.setGodleNum(godle = gold)
    }

    override fun nobleSuccess(noble: Int) {
        this.mNoble = noble
    }

    override fun goldBuyNobleSuccess(status: Int) {
        when (status) {
            1 -> {
                ToastUtils.showShort("购买成功")
                finish()
            }
            2 -> {
                ToastUtils.showShort("金币不足")
            }
            else -> {
                ToastUtils.showShort("无此贵族")
            }
        }
    }

    override fun aliBuySuccess(buyResult: BuyGoldResult) {
        if (buyResult.orderString.isEmpty()) return
        mPresenter.aliPay(mHandler, buyResult.orderString, SDK_PAY_FLAG)

    }

    override fun wxBuySuccess(buyResult: BuyGoldResult) {
        mPresenter.weCharPay(BaseApplication.getApi(), buyResult)
    }

    override fun onError(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }

    override fun onHeaderClick(type: Int) {
        setButtonResutl()
    }

    private fun setButtonResutl() {

        if (result == null) return

        if (mHeader.getType() == 1) {

            if (result?.ifBuy == 0) {


                mTvMoney.text = NumberFormat.getNumberInstance(Locale.US).format(result?.againCoinPrice)

            } else {
                mTvMoney.text = NumberFormat.getNumberInstance(Locale.US).format(result?.coinPrice)
            }

            mTvMoneyType.text = "金币"

        } else if (mHeader.getType() == 2 || mHeader.getType() == 3) {

            mTvMoney.text = "¥"

            if (result?.ifBuy == 0) {


                mTvMoneyType.text = NumberFormat.getNumberInstance(Locale.US).format(result?.againRmbPrice)

            } else {
                mTvMoneyType.text = NumberFormat.getNumberInstance(Locale.US).format(result?.rmbPrice)
            }


        }

    }

    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String?, String?>)

                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.result // 同步返回需要验证的信息
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        val bundle = Bundle()
                        bundle.putString(Constant.PAY_RESULT, resultInfo)
                        ActStartUtils.startAct(this@NobleOrderAct, PayResultAct::class.java, bundle)
                        finish()
                    } else {

                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showShort("支付失败")
                    }
                }
            }
        }
    }
}
