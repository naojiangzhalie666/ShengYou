package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyFamilyIncomeAct
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class FamilyDiamondHeaderView : RelativeLayout {

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

    @BindView(R.id.ll_family_num)
    lateinit var llFamilyNum: LinearLayout

    @BindView(R.id.tv_family_members)
    lateinit var tvFamilyMembers: TextView

    @BindView(R.id.tv_total_num)
    lateinit var mTvTotalNum: TextView

    lateinit var mView: View

    lateinit var mContext: Context

    private var mResult: StartRecommendResult? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        mContext = context!!
        mView = LayoutInflater.from(context).inflate(R.layout.view_family_diamond, this, true)
        ButterKnife.bind(mView)
    }

    fun setFamilyInfo(result: StartRecommendResult?) {
        if (result == null) return
        mResult = result
        GlideAppUtil.loadImage(mContext, result.clanPicture, ivFamilyBg)
        GlideAppUtil.loadImage(mContext, result.clanPicture, ivFamilyLogo)

        tvFamilyName.text = result.clanName

        tvFamilyId.text = result.clanNumber.toString()

        tvFamilyNum.text = result.member.toString()

        tvFamilyMembers.text = result.member.toString()

        mTvTotalNum.text = "${result.coins.toInt()}"

    }

    @OnClick(R.id.cad_family)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.cad_family -> {
                var bundle = Bundle()
                bundle.putSerializable(Constant.FAMILY_RESULT, mResult)
                ActStartUtils.startAct(mContext, MyFamilyIncomeAct::class.java, bundle)
            }

        }
    }

}