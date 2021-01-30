package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import butterknife.OnTextChanged
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.mvp.contract.TeensCloseContract
import com.xiaoshanghai.nancang.mvp.presenter.TeensClosePresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.InputEditViewLayout

class TeensCloseAct : BaseMvpActivity<TeensClosePresenter>(), TeensCloseContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.tv_set_psw)
    lateinit var mTvSetPsw: TextView

    @BindView(R.id.et_input)
    lateinit var mEtInput: InputEditViewLayout

    @BindView(R.id.bt_open)
    lateinit var mBtOpen: Button

    var password = ""

    override fun setLayoutId(): Int = R.layout.activity_teens_close

    override fun createPresenter(): TeensClosePresenter = TeensClosePresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)

    }

    @OnTextChanged(R.id.et_input)
    fun onTextChanged(editable: Editable) {
        mBtOpen.isEnabled = mEtInput.length() == 4
        if (mEtInput.length() == 4) password = mEtInput.text.toString()

    }

    @OnClick(R.id.bt_open)
    fun onClick() {
        mPresenter.closeTeens(SPUtils.getInstance().userInfo.id, password)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }

    override fun onSuccess(status: String) {
        when (status) {
            "0" -> {
                ToastUtils.showShort("关闭青少年保护失败")
            }
            "1" -> {
                ToastUtils.showShort("关闭成功")
                finish()
            }
            "2" -> {
                ToastUtils.showShort("密码错误")
            }

            else -> {
                ToastUtils.showShort("关闭青少年保护失败")
            }
        }
    }

    override fun onError(msg: String) {
        ToastUtils.showShort(msg)
    }
}
