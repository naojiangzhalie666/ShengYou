package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.mvp.contract.GoldCoinGiftContract
import com.xiaoshanghai.nancang.mvp.presenter.GoldCoinGiftPresenter
import kotlinx.android.synthetic.main.activity_gold_coin_gift.*
import kotlinx.android.synthetic.main.activity_my_gift.title_bar

class GoldCoinGiftAct : BaseMvpActivity<GoldCoinGiftPresenter>(), GoldCoinGiftContract.View, TitleBarClickCallback {


    override fun createPresenter(): GoldCoinGiftPresenter = GoldCoinGiftPresenter()

    override fun setLayoutId(): Int = R.layout.activity_gold_coin_gift;

    override fun initView(savedInstanceState: Bundle?) {
        title_bar.setOnClickCallback(this)
        this.mPresenter.attachView(this)
        mPresenter.initFragment(index = index, viewPager = viewpager)

    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }
}
