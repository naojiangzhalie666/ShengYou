package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.utils.AppUtils
import kotlinx.android.synthetic.main.act_about.*

class AboutUsAct : BaseActivity(), TitleBarClickCallback {


    override fun setLayoutId(): Int {
        return R.layout.act_about
    }

    override fun initView(savedInstanceState: Bundle?) {
        title_bar.setOnClickCallback(this)
        tv_name.text = "v "+AppUtils.getAppVersionName(this)
    }


    override fun titleLeftClick() {
        finish()
    }


    override fun titleRightClick(status: Int) {
    }


}