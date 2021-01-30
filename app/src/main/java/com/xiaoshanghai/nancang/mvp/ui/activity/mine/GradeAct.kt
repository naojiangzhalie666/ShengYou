package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.GradeContract
import com.xiaoshanghai.nancang.mvp.presenter.GradePresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import net.lucode.hackware.magicindicator.MagicIndicator

class GradeAct : BaseMvpActivity<GradePresenter>(), GradeContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.index)
    lateinit var mIndex: MagicIndicator

    @BindView(R.id.viewpager)
    lateinit var mViewPager:ViewPager

    private var mGradeKey = ""

    override fun setLayoutId(): Int = R.layout.activity_grade

    override fun createPresenter(): GradePresenter = GradePresenter()


    override fun initView(savedInstanceState: Bundle?) {
        mGradeKey =  intent.extras.getString(Constant.GRADE_KEY)
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        mPresenter.initFragment(mViewPager,mIndex)

        if (mGradeKey == Constant.GRADE_USER_LV) {
            mViewPager.currentItem = 0
        } else if (mGradeKey == Constant.GRADE_CHARM_LV) {
            mViewPager.currentItem = 1
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}