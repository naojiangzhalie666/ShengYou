package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import butterknife.OnTextChanged
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.mvp.contract.PhoneTestContract
import com.xiaoshanghai.nancang.mvp.presenter.PhoneTestPresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.Event
import com.xiaoshanghai.nancang.utils.*

class PhoneTestAct : BaseMvpActivity<PhoneTestPresenter>(), PhoneTestContract.View, TitleBarClickCallback {

    @BindView(R.id.title)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.tv_phone_num)
    lateinit var mTvPhoneNum: TextView

    @BindView(R.id.tv_send_code)
    lateinit var mTvSendCode: TextView

    @BindView(R.id.et_code)
    lateinit var mEtCode: EditText

    @BindView(R.id.iv_delete)
    lateinit var mIvDelete: ImageView

    @BindView(R.id.tv_next)
    lateinit var mTvNext: TextView

    private val mUserInfo by lazy { SPUtils.getInstance().userInfo }

    //倒计时
    private var downTimerUtils: CountDownTimerUtils? = null

    private var isSend = false

    private var isCheck = false

    override fun isRegisterEventBus(): Boolean = true

    override fun setLayoutId(): Int = R.layout.activity_phone_test

    override fun createPresenter(): PhoneTestPresenter = PhoneTestPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
        downTimerUtils = CountDownTimerUtils(mTvSendCode, Constant.WAIT_SMS_TIME.toLong(), Constant.SMS_INTERVAL.toLong())
    }

    private fun init() {

        mTitleBar.setOnClickCallback(this)

        var phoneNumber = mUserInfo.userPhone

        var maskNumber = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length)

        mTvPhoneNum.text = maskNumber
    }

    @OnTextChanged(R.id.et_code)
    fun codeChanged(editable: Editable?) {

        mIvDelete.visibility = if (mEtCode.length() > 0) View.VISIBLE else View.GONE


        mTvNext.isEnabled = mEtCode.length() == 4

        mTvNext.alpha = if (mEtCode.isEnabled) 1f else 0.5f
    }

    @OnClick(R.id.tv_send_code, R.id.iv_delete, R.id.tv_next)
    fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_send_code -> {
                if (!isSend) {
                    if (StringUtils.isPhoneNum(mUserInfo.userPhone)) {
                        mPresenter.getStatusCode(mUserInfo.userPhone)
                        downTimerUtils?.start()
                        isSend = true
                    } else {
                        ToastUtils.showLong(resources.getString(R.string.input_phone))
                    }
                }
            }

            R.id.iv_delete -> {
                mEtCode.text = null
            }

            R.id.tv_next -> {
                if (!isCheck) {
                    isCheck = true
                    var code = mEtCode.text.toString().trim()
                    val userPhone = mUserInfo.userPhone
                    mPresenter.checkCode(userPhone, code)
                }

            }
        }
    }

    override fun codeSuccess(code: String?) {
        ToastUtils.showShort("验证码发送成功")
    }

    override fun codeError(msg: String?) {
        downTimerUtils?.cancle()
        mTvSendCode.text = resources.getString(R.string.get_code_num)
        mTvSendCode.isEnabled = true
        isSend = false
        ToastUtils.showShort("验证码发送失败")
    }

    override fun onCheckSuccess(status: String?) {
        isCheck = false
        if (status == "1") {
            ActStartUtils.startAct(this, ChangePhoneAct::class.java)
        } else {
            ToastUtils.showShort("验证码错误")
        }
    }

    override fun onError(msg: String?) {
        isCheck = false
        ToastUtils.showShort(msg)
    }

    override fun onDestroy() {
        if (downTimerUtils != null) {
            downTimerUtils?.cancel()
            downTimerUtils = null
        }
        super.onDestroy()
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }


    override fun receiveEvent(event: Event<*>?) {
        if (event != null && event.code == EventConstant.CHANGE_PHONE_SUCCESS) {
            finish()
        }
    }

}