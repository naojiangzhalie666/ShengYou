package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import butterknife.BindView
import butterknife.OnCheckedChanged
import butterknife.OnClick
import butterknife.OnTextChanged
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.mvp.contract.VerifiedContract
import com.xiaoshanghai.nancang.mvp.presenter.VerifiedPresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.TipsDialog

class VerifiedAct : BaseMvpActivity<VerifiedPresenter>(), VerifiedContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.et_name)
    lateinit var mEtName: EditText

    @BindView(R.id.et_car_num)
    lateinit var mEtCarNum: EditText

    @BindView(R.id.bt_verified)
    lateinit var mBtVerified: Button

    var isSelect = false

    var isRequest = false

    override fun setLayoutId(): Int = R.layout.activity_verified

    override fun createPresenter(): VerifiedPresenter = VerifiedPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        mPresenter.queryVerified(SPUtils.getInstance().userInfo.id)
    }

    @OnTextChanged(R.id.et_name, R.id.et_car_num)
    fun onTextChanged(editable: Editable) {
        mBtVerified.isEnabled = mEtName.length() > 0 && mEtCarNum.length() > 0 && isSelect && isRequest
    }


    @OnCheckedChanged(R.id.cb_box)
    fun onChecked(view: CompoundButton, ischanged: Boolean) {
        isSelect = ischanged
        mBtVerified.isEnabled = mEtName.length() > 0 && mEtCarNum.length() > 0 && isSelect && isRequest
    }

    override fun querySuccess(status: String) {
        if (status == "0") {

            isRequest = true

        } else if (status == "1") {
            isRequest = false
            TipsDialog.createDialog(this, R.layout.dialog_verified)
                    .OnCancelListener {
                        finish()
                    }
                    .show()
        }

        mBtVerified.isEnabled = mEtName.length() > 0 && mEtCarNum.length() > 0 && isSelect && isRequest

    }

    override fun verifiedSuccess(status: String) {

        if (status == "1") {

            isRequest = false
            TipsDialog.createDialog(this, R.layout.dialog_verified)
                    .OnCancelListener {
                        finish()
                    }
                    .show()

        } else {
            isRequest = false
        }

    }

    override fun onError(msg: String) {
        ToastUtils.showShort(msg)
    }

    @OnClick(R.id.bt_verified)
    fun onClick() {
        mPresenter.verified(SPUtils.getInstance().userInfo.id, mEtName.text.toString(), mEtCarNum.text.toString())
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }
}
