package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.ChangePswMineContract
import com.xiaoshanghai.nancang.mvp.presenter.ResetPsPresenter
import com.xiaoshanghai.nancang.utils.*
import kotlinx.android.synthetic.main.act_reset_ps.*
import kotlinx.android.synthetic.main.act_reset_ps.et_psw_new
import kotlinx.android.synthetic.main.act_reset_ps.et_psw_new_confirm
import kotlinx.android.synthetic.main.act_reset_ps.tv_commit


/**
 *设置-重置密码
 */
class ResetPsAct : BaseMvpActivity<ResetPsPresenter>(), ChangePswMineContract.View {

    override fun changeSuccess() {
    }

    override fun changeError(message: String?) {
    }

    override fun getCodeSuccess() {
        ToastUtils.showShort("验证码发送成功")
    }

    override fun getCodeError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun resetPswSuccess() {
        ToastUtils.showShort("密码重置成功")
        finish()
    }

    override fun resetPswError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    lateinit var downTimerUtils: CountDownTimerUtils

    override fun createPresenter(): ResetPsPresenter {
        return ResetPsPresenter()
    }


    override fun setLayoutId(): Int {
        return R.layout.act_reset_ps
    }


    override fun initView(savedInstanceState: Bundle?) {
    mPresenter.attachView(this)
        downTimerUtils = CountDownTimerUtils(tv_get_code, Constant.WAIT_SMS_TIME.toLong(), Constant.SMS_INTERVAL.toLong())

        et_phone_num.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length == 11) {
                    tv_get_code.isEnabled = true
                    tv_get_code.alpha = 1.0f
                } else {
                    tv_get_code.isEnabled = false
                    tv_get_code.alpha = 0.5f
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        var phone = SPUtils.getInstance().userInfo.userPhone
        if (phone != null) {
            et_phone_num.setText(phone)
        }
        tv_get_code.setOnClickListener {
            if (StringUtils.isPhoneNum(et_phone_num.text.toString().trim())) {
                downTimerUtils.start()
                mPresenter.getCode(phone)
            } else {
                ToastUtils.showShort(resources.getString(R.string.input_phone))
            }
        }

        tv_commit.setOnClickListener {
            if (TextUtils.isEmpty(et_phone_num.text.toString().trim())) {
                ToastUtils.showShort("请输入手机号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_captcha.text.toString().trim())) {
                ToastUtils.showShort("请输入手机验证码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_psw_new.text.toString().trim())) {
                ToastUtils.showShort("请输入新密码")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(et_psw_new_confirm.text.toString().trim())) {
                ToastUtils.showShort("请确认新密码")
                return@setOnClickListener
            }
            if (!AppUtils.isPasswordNO(et_psw_new.text.toString().trim()) || !AppUtils.isPasswordNO(et_psw_new_confirm.text.toString().trim())) {
                ToastUtils.showShort("密码格式错误")
                return@setOnClickListener
            }

            if (et_psw_new.text.toString().trim() != et_psw_new_confirm.text.toString().trim()) {
                ToastUtils.showShort("两次输入的密码不一致")
                return@setOnClickListener
            }

          mPresenter.changePsw("1",et_psw_new.text.toString().trim(),et_captcha.text.toString().trim(),"")
        }

        title_bar.setOnClickCallback(object : TitleBarClickCallback {
            override fun titleLeftClick() {
                finish()
            }

            override fun titleRightClick(status: Int) {
            }

        })
    }

}