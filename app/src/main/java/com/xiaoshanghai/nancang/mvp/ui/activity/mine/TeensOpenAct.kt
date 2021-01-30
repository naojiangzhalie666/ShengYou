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
import com.xiaoshanghai.nancang.mvp.contract.TeensOpenContract
import com.xiaoshanghai.nancang.mvp.presenter.TeensOpenPresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.InputEditViewLayout

class TeensOpenAct : BaseMvpActivity<TeensOpenPresenter>(), TeensOpenContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.tv_set_psw)
    lateinit var mTvSetPsw: TextView

    @BindView(R.id.tv_prompt)
    lateinit var mTvPrompt: TextView

    @BindView(R.id.et_input)
    lateinit var mEtInput: InputEditViewLayout

    @BindView(R.id.bt_open)
    lateinit var mBtOpen: Button

    var isFrist = true

    var fristPsw = ""

    var secondPse = ""

    override fun setLayoutId(): Int = R.layout.activity_teens_open

    override fun createPresenter(): TeensOpenPresenter = TeensOpenPresenter()


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)

    }

    @OnTextChanged(R.id.et_input)
    fun onTextChanged(editable: Editable) {
        mBtOpen.isEnabled = mEtInput.length() == 4
    }

    @OnClick(R.id.bt_open)
    fun onClick() {
        if (isFrist) {
            fristPsw =  mEtInput.text.toString()
            mEtInput.cleanData()
            isFrist = false
            mBtOpen.isEnabled = false
            mTvSetPsw.text = "再次输入密码"
            mTvPrompt.text = "我们将不提供找回密码服务 请牢记已设置的密码"
        } else {
            secondPse = mEtInput.text.toString()
            if (secondPse == fristPsw){

                mPresenter.openTeens(SPUtils.getInstance().userInfo.id,secondPse)

            } else {
                ToastUtils.showShort("两次输入密码不一致，请再次输入密码")
                mEtInput.cleanData()
            }
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }

    override fun onSuccess(status: String) {
        when (status) {
            "0" -> {
                ToastUtils.showShort("开启青少年保护失败")
            }
            "1" -> {
                ToastUtils.showShort("开启成功")
                finish()
            }
            "2" -> {
                ToastUtils.showShort("密码错误")
            }

            else -> {
                ToastUtils.showShort("开启青少年保护失败")
            }
        }
    }

    override fun onError(msg: String) {
        ToastUtils.showShort(msg)
    }
}
