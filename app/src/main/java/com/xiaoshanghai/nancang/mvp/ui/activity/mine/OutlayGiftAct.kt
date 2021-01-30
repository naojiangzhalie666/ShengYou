package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.mvp.contract.OutlayGiftContract
import com.xiaoshanghai.nancang.mvp.presenter.OutlayGiftPresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import net.lucode.hackware.magicindicator.MagicIndicator

class OutlayGiftAct : BaseMvpActivity<OutlayGiftPresenter>(), OutlayGiftContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var titleBar: TitleBarView

    @BindView(R.id.index)
    lateinit var index: MagicIndicator

    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager

    override fun createPresenter(): OutlayGiftPresenter = OutlayGiftPresenter()

    override fun setLayoutId(): Int = R.layout.activity_outlay_gift

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        titleBar.setOnClickCallback(this)

        mPresenter.initFragment(viewPager = viewPager, index = index)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}
