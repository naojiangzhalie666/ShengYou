package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnNobleOrderHeaderCallback
import com.xiaoshanghai.nancang.mvp.ui.adapter.NobleAdapter

class NobleOrderHeader : RelativeLayout {

    @BindView(R.id.tv_gold_num)
    lateinit var mGoldNum: TextView

    @BindView(R.id.ll_gold)
    lateinit var mGold: LinearLayout

    @BindView(R.id.ll_zfb)
    lateinit var mZfb: LinearLayout

    @BindView(R.id.ll_wx)
    lateinit var mWx: LinearLayout

    lateinit var mView: View

    lateinit var mContext: Context

    lateinit var mCallback: OnNobleOrderHeaderCallback

    var mAdapter: NobleAdapter? = null

    var type: Int? = 1

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        mContext = context!!
        mView = LayoutInflater.from(context).inflate(R.layout.view_noble_order_header, this, true)
        ButterKnife.bind(mView)
    }

    fun setAdapter(adaprer: NobleAdapter) {
        this.mAdapter = adaprer
    }

    fun setOnNobleOrderHeaderCallback(callback: OnNobleOrderHeaderCallback) {
        this.mCallback = callback
    }

    fun getType(): Int {
        return type!!
    }

    fun setGodleNum(godle: Double) {
        mGoldNum.text = String.format("%.1f", godle)
    }


    @OnClick(R.id.ll_gold, R.id.ll_zfb, R.id.ll_wx)
    fun onClick(v: View) {
        when (v.id) {
            R.id.ll_gold -> {
                type = 1
                mGold.setBackgroundResource(R.drawable.shape_noble_type_select)
                mZfb.setBackgroundResource(R.drawable.shape_noble_un_select)
                mWx.setBackgroundResource(R.drawable.shape_noble_un_select)
                if (mAdapter != null) {
                    mAdapter?.setType(type!!)
                }
                if (mCallback != null) {
                    mCallback.onHeaderClick(type!!)
                }
            }

            R.id.ll_zfb -> {
                type = 2
                mGold.setBackgroundResource(R.drawable.shape_noble_un_select)
                mZfb.setBackgroundResource(R.drawable.shape_noble_type_select)
                mWx.setBackgroundResource(R.drawable.shape_noble_un_select)
                if (mAdapter != null) {
                    mAdapter?.setType(type!!)
                }
                if (mCallback != null) {
                    mCallback.onHeaderClick(type!!)
                }
            }

            R.id.ll_wx -> {
                type = 3
                mGold.setBackgroundResource(R.drawable.shape_noble_un_select)
                mZfb.setBackgroundResource(R.drawable.shape_noble_un_select)
                mWx.setBackgroundResource(R.drawable.shape_noble_type_select)
                if (mAdapter != null) {
                    mAdapter?.setType(type!!)
                }
                if (mCallback != null) {
                    mCallback.onHeaderClick(type!!)
                }
            }
        }
    }


}