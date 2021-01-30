package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import butterknife.BindView
import butterknife.OnClick
import com.github.florent37.viewanimator.ViewAnimator
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.constant.SpConstant
import com.xiaoshanghai.nancang.mvp.contract.LotteryContract
import com.xiaoshanghai.nancang.mvp.presenter.LotteryPresenter
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyGoldAct
import com.xiaoshanghai.nancang.net.bean.*
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.DancingNumberView
import com.opensource.svgaplayer.SVGACallback
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import kotlin.math.cos
import kotlin.math.sin


class LotteryAct : BaseMvpActivity<LotteryPresenter>(), LotteryContract.View, SVGAParser.ParseCompletion {

    @BindView(R.id.tv_backpack)
    lateinit var mTvBackPack: TextView

    @BindView(R.id.tv_record)
    lateinit var mTvRecord: TextView

    @BindView(R.id.iv_close)
    lateinit var mIvClose: ImageView

    @BindView(R.id.rg_lottery_num)
    lateinit var mRgLotteryNum: RadioGroup

    @BindView(R.id.iv_bx_bg)
    lateinit var mIvBxBg: ImageView

    @BindView(R.id.svg_bx)
    lateinit var mSvgBox: SVGAImageView

    @BindView(R.id.iv_flag)
    lateinit var mIvFlag: ImageView

    @BindView(R.id.constraint_box)
    lateinit var mCostraint: ConstraintLayout

    @BindView(R.id.tv_comit)
    lateinit var mTvComit: TextView

    @BindView(R.id.tv_balance)
    lateinit var mTvBalance: DancingNumberView

    @BindView(R.id.ck_notie)
    lateinit var mCkNotic: CheckBox

    private var lotteryType = 1

    private var isLoader = false

    private var isNotice = true

    private var mPrize: PrizeResult? = null

    private var svgaParser: SVGAParser? = null /*by lazy { SVGAParser.shareParser() } //播放工具*/


    private val angleList = arrayOf(30, 60, 90, 120, 150, 150, 150, 150, 150, 150)

    private val constraintSet = ConstraintSet()

    private var mKind = 1

    private var isLotter: Boolean = false

    private var lotterView: MutableList<PositionEntity> = ArrayList()


    override fun setLayoutId(): Int = R.layout.activity_lottery

    override fun createPresenter(): LotteryPresenter {
        return LotteryPresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        svgaParser = SVGAParser.shareParser()
        mPresenter.attachView(this)
        initData()
        mPresenter.getMyGold()
        mPresenter.getPrize(mKind)


    }

    fun initData() {

        mKind = intent.extras.getInt(Constant.LOTTERY_TYPE)

        isNotice = SPUtils.getInstance().getBoolean(SpConstant.LOTTERY_NOTICE_SWITCH, true)
        mCkNotic.isChecked = isNotice



        if (mKind == 1) {
            mIvBxBg.setImageResource(R.mipmap.img_bybx)
        } else {
            mIvBxBg.setImageResource(R.mipmap.img_cjbx)
        }


        initSvg()

        mRgLotteryNum.setOnCheckedChangeListener { group, checkedId ->
            var radioButton = group.findViewById<RadioButton>(checkedId)
            var result = radioButton.text.toString()
            when (result) {
                "1次" -> {
                    lotteryType = 1
                    mTvComit.text = "${mPrize?.drawCost1}金币 开启"
                }
                "10次" -> {
                    lotteryType = 2
                    mTvComit.text = "${mPrize?.drawCost2}金币 开启"
                }
                "100次" -> {
                    lotteryType = 3
                    mTvComit.text = "${mPrize?.drawCost3}金币 开启"
                }
            }

        }

        mCkNotic.setOnCheckedChangeListener { compoundButton, b ->

            isNotice = b
            SPUtils.getInstance().put(SpConstant.LOTTERY_NOTICE_SWITCH, isNotice)
        }
    }

    private fun initSvg() {

//        svgaParser.init(this)


        mSvgBox.callback = object : SVGACallback {
            override fun onFinished() {
                if (::mIvBxBg.isInitialized) {
                    mIvBxBg.visibility = View.VISIBLE
                }
            }

            override fun onPause() {

            }

            override fun onRepeat() {

            }

            override fun onStep(frame: Int, percentage: Double) {
                if (percentage == 0.5) {
                    for (positionEntity in lotterView) {
                        playAnimation(positionEntity)
                    }
                }
            }

        }
    }

    @OnClick(R.id.tv_backpack, R.id.tv_record, R.id.iv_close, R.id.tv_comit, R.id.tv_rule, R.id.tv_cz)
    fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_backpack -> {
                EventBusUtil.sendEvent(Event(EventConstant.OPEN_GIFT_WINDOWS, ""))
                finish()

            }

            R.id.tv_record -> {
                finish()
                var bundle = Bundle()
                bundle.putInt(Constant.LOTTERY_TYPE, mKind)
                ActStartUtils.startAct(this, LotterRecordAct::class.java, bundle)
            }

            R.id.iv_close -> {
                finish()
            }

            R.id.tv_comit -> {

                if (!isLotter) {
                    isLotter = true
                    mPresenter.lottery(lotteryType, mKind)
                }

            }

            R.id.tv_rule -> {
                finish()
                var bundle = Bundle()
                bundle.putInt(Constant.LOTTERY_TYPE, mKind)
                ActStartUtils.startAct(this, LotteryRuleAct::class.java, bundle)
            }

            R.id.tv_cz -> {
                ActStartUtils.startAct(this, MyGoldAct::class.java)
            }
        }

    }

    override fun onComplete(videoItem: SVGAVideoEntity) {
        mSvgBox.stopAnimation()
        mSvgBox.setVideoItem(videoItem)
        mSvgBox.startAnimation()
        mIvBxBg.visibility = View.INVISIBLE

    }

    override fun onError() {

    }

    private fun getLotterView(resultItems: MutableList<AwardsEntity>): MutableList<PositionEntity> {

        val list: MutableList<PositionEntity> = ArrayList()

       var angle = if (resultItems.size >= angleList.size){
            angleList[angleList.size-1]
        } else {
            angleList[resultItems.size - 1]
        }

            for (i in resultItems.indices) {

                val gift = resultItems[i].item?.gift
                if (gift?.giftPriceType == 2 && gift?.giftPrice > Constant.PUBLICITY_PRICE_1 && isNotice) {
                    gift.giftCont = resultItems[i].times!!
                    gift.boxType = mKind
                    EventBusUtil.sendEvent(Event(EventConstant.LOTTER_PRICE, gift))
                }

                val radian = if (resultItems.size == 1) {
                    ((180 - angle) / 2)

                } else {
                    ((180 - angle) / 2) + (i * (angle / (resultItems.size - 1)))
                }


                val originX = getOrigin()[0]

                val originY = getOrigin()[1]


                val currentAdius = ScreenUtils.dp2px(this, 130f)

                val image = ImageView(this)
                image.id = View.generateViewId()
                image.scaleType = ImageView.ScaleType.CENTER_INSIDE
                GlideAppUtil.loadImage(this, gift?.giftStaticUrl, image)

                val toAxisX = getAxisX(originX.toFloat(), radian.toDouble(), currentAdius.toFloat())/*+ScreenUtils.dp2px(this,22.5f)*/

                val toAxisY = getAxisY(originY.toFloat(), radian.toDouble(), currentAdius.toFloat())/*+ScreenUtils.dp2px(this,22.5f)*/


                val positionEntity = PositionEntity(image, 0f, 0f, originX - toAxisX, originY - toAxisY, null)

                list.add(positionEntity)

            }

        return list

    }


    private fun getAxisX(centerx: Float, radian: Double, currentAdius: Float): Float {
        return (centerx + currentAdius * cos(radian * Math.PI / 180)).toFloat()
    }

    private fun getAxisY(centery: Float, radian: Double, currentAdius: Float): Float {
        return (centery + currentAdius * sin(radian * Math.PI / 180)).toFloat()
    }

    private fun getOrigin(): IntArray {

        val location = IntArray(2)
        mIvFlag.getLocationInWindow(location) //获取在当前窗口内的绝对坐标
        return location
    }

    private fun playAnimation(bean: PositionEntity) {
        val view = bean.image!!
        view.visibility = View.GONE

        constraintSet.constrainWidth(view.id, ScreenUtils.dp2px(this, 55f))
        constraintSet.constrainHeight(view.id, ScreenUtils.dp2px(this, 55f))
        constraintSet.setDimensionRatio(view.id, "h,1:1")
        constraintSet.connect(view.id, ConstraintSet.TOP, R.id.iv_flag, ConstraintSet.TOP)
        constraintSet.connect(view.id, ConstraintSet.START, R.id.iv_flag, ConstraintSet.START)
        constraintSet.connect(view.id, ConstraintSet.END, R.id.iv_flag, ConstraintSet.END)
        constraintSet.connect(view.id, ConstraintSet.BOTTOM, R.id.iv_flag, ConstraintSet.BOTTOM)

        mCostraint.removeView(view)

        mCostraint.addView(view)
        TransitionManager.beginDelayedTransition(mCostraint)
        constraintSet.applyTo(mCostraint)

        ViewAnimator.animate(view)
                .translationX(bean.fromAxisX!!, bean.toAxisX!!)
                .translationY(bean.fromAxisY!!, bean.toAxisY!!)
                .scale(0.1f, 1f)
                .interpolator(DecelerateInterpolator())
                .duration(1000)
                .thenAnimate(view)
                .scale(1f, 1f)
                .alpha(1f, 0f)
                .duration(1000)
                .onStart {
                    view.visibility = View.VISIBLE
                }
                .onStop {
                    if (::mCostraint.isInitialized) {
                        mCostraint.removeView(view)
                    }
                }
                .start()
    }

    override fun onPrizeSuccess(prize: PrizeResult?) {
        isLoader = true
        this.mPrize = prize
        setPrizeResult(mPrize)
    }

    override fun onGoldSuccess(balance: Double) {

        val strToNumber = NumberUtils.strToNumber(balance.toString())

        mTvBalance.text = strToNumber
        mTvBalance.duration = 1000;//设置完成跳动的持续时长(单位ms)
        mTvBalance.format = "%.2f";//设置数组的显示格式
        mTvBalance.dance()
    }

    override fun onLotterySuccess(result: LotteryPirzeResult?) {

        when (result?.status) {
            1 -> {
                isLotter = false
                val resultItems = result?.resultItems
                showPrize(resultItems)
                mPresenter.getMyGold()
            }
            0 -> {
                isLotter = true
                ToastUtils.showShort("抽奖失败")
            }
            2 -> {
                isLotter = true
                ToastUtils.showShort("金币不足")
            }
        }


    }

    private fun showPrize(resultItems: MutableList<AwardsEntity>?) {
        if (resultItems?.size!! <= 0) return

        lotterView.clear()

        val viewList = getLotterView(resultItems)

        lotterView.addAll(viewList)

//        SVGAParser.shareParser().init(this);
        svgaParser?.init(this)
        if (mKind == 1) {
            svgaParser?.decodeFromAssets("box/silver_box.svga", this)
        } else if (mKind == 2) {
            svgaParser?.decodeFromAssets("box/glod_box.svga", this)
        }


    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
        isLotter = false
    }

    private fun setPrizeResult(prize: PrizeResult?) {
        when (lotteryType) {
            1 -> {
                mTvComit.text = "${prize?.drawCost1}金币 开启"
            }
            2 -> {
                mTvComit.text = "${prize?.drawCost2}金币 开启"
            }
            3 -> {
                mTvComit.text = "${prize?.drawCost3}金币 开启"
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
//        svgaParser.
        svgaParser = null
    }


}