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
import com.google.gson.Gson
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.constant.SpConstant
import com.xiaoshanghai.nancang.mvp.contract.ChangePhoneContract
import com.xiaoshanghai.nancang.mvp.presenter.ChangePhonePresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.Event
import com.xiaoshanghai.nancang.utils.*

class ChangePhoneAct : BaseMvpActivity<ChangePhonePresenter>(), ChangePhoneContract.View, TitleBarClickCallback {

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

    //倒计时
    private var downTimerUtils: CountDownTimerUtils? = null

    private var isSend = false

    private var isCheck = false

    override fun setLayoutId(): Int = R.layout.activity_change_phone

    override fun createPresenter(): ChangePhonePresenter = ChangePhonePresenter()


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
    }

    private fun init() {

        mTitleBar.setOnClickCallback(this)
        downTimerUtils = CountDownTimerUtils(mTvSendCode, Constant.WAIT_SMS_TIME.toLong(), Constant.SMS_INTERVAL.toLong())

    }

    @OnTextChanged(R.id.tv_phone_num, R.id.et_code)
    fun codeChanged(editable: Editable?) {

        mIvDelete.visibility = if (mEtCode.length() > 0) View.VISIBLE else View.GONE


        mTvNext.isEnabled = mEtCode.length() == 4 && mTvPhoneNum.length() == 11

        mTvNext.alpha = if (mEtCode.isEnabled) 1f else 0.5f
    }


    @OnClick(R.id.tv_send_code, R.id.iv_delete, R.id.tv_next)
    fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_send_code -> {
                if (!isSend) {
                    if (StringUtils.isPhoneNum(mTvPhoneNum.text.toString().trim())) {
                        mPresenter.getStatusCode(mTvPhoneNum.text.toString().trim())
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
                if(!isCheck) {
                    isCheck = true
                    var code = mEtCode.text.toString().trim()
                    val userPhone = mTvPhoneNum.text.toString().trim()
                    mPresenter.changePhone(userPhone,SPUtils.getInstance().userInfo.id, code)
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

    override fun onChangeSuccess(status: String?) {
        isCheck = false
        if (status == "1") {
            ToastUtils.showShort("手机号码修改成功")

            val userInfo = SPUtils.getInstance().userInfo
            userInfo.userPhone = mTvPhoneNum.text.toString().trim()

            val strUser = Gson().toJson(userInfo)
            SPUtils.getInstance().put(SpConstant.USER_INFO, strUser)
            SPUtils.getInstance().put(SpConstant.USER_INFO, strUser)

            EventBusUtil.sendEvent(Event(EventConstant.CHANGE_PHONE_SUCCESS,null))
            finish()

        } else {
            ToastUtils.showShort("修改失败")
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
}