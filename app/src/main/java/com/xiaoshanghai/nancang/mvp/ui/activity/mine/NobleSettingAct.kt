package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import androidx.appcompat.widget.SwitchCompat
import butterknife.BindView
import com.google.gson.Gson
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.SpConstant
import com.xiaoshanghai.nancang.mvp.contract.NobleSettingContract
import com.xiaoshanghai.nancang.mvp.presenter.NobleSettingPresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils

class NobleSettingAct : BaseMvpActivity<NobleSettingPresenter>(), NobleSettingContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.switch_noble_enter)
    lateinit var mEnterRoom: SwitchCompat

    private val mUserInfo by lazy { SPUtils.getInstance().userInfo }

    override fun setLayoutId(): Int = R.layout.activity_noble_setting

    override fun createPresenter(): NobleSettingPresenter = NobleSettingPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        init()
    }

    private fun init() {

        mTitleBar.setOnClickCallback(this)

        mEnterRoom.isChecked = mUserInfo.isInvisible == 1

        mEnterRoom.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {

                if (!isChecked) {
                    mPresenter.setRoomStatus(mUserInfo.id, "0")
                } else {
                    mPresenter.setRoomStatus(mUserInfo.id, "1")
                }
            }
        }
    }

    override fun onStatusSuccess(status: String?, result: String?) {

        if (status == "0") {
            //关闭隐身
            mEnterRoom.isChecked = result != "1"


        } else if (status == "1") {
            //开启隐身

            mEnterRoom.isChecked = result == "1"

        }

        mUserInfo.isInvisible = if (mEnterRoom.isChecked) 1 else 0

        val strUser = Gson().toJson(mUserInfo)
        SPUtils.getInstance().put(SpConstant.USER_INFO, strUser)

    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}