package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.URLConstant
import com.xiaoshanghai.nancang.mvp.contract.SettingContract
import com.xiaoshanghai.nancang.mvp.presenter.SettingPresenter
import com.xiaoshanghai.nancang.mvp.ui.activity.login.LoginActivity
import com.xiaoshanghai.nancang.net.bean.BindEntity
import com.xiaoshanghai.nancang.net.bean.CheckVersionBean
import com.xiaoshanghai.nancang.utils.*
import com.xiaoshanghai.nancang.view.TipsDialog
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom
import kotlinx.android.synthetic.main.act_feedback.title_bar
import kotlinx.android.synthetic.main.activity_setting.*


class SettingAct : BaseMvpActivity<SettingPresenter>(), SettingContract.View, TitleBarClickCallback {

    @BindView(R.id.tv_phone_num)
    lateinit var mTvPhoneNum: TextView

    @BindView(R.id.tv_cache)
    lateinit var mTvCache: TextView

    @BindView(R.id.tv_bind_type)
    lateinit var mTvBindType: TextView

    private var isGet: Boolean = false

    private var mResult: BindEntity? = null

    private val mUserInfo by lazy { SPUtils.getInstance().userInfo }

    private val glideCachUtils by lazy { GlideCacheUtils.getInstance() }

    val context by lazy { this }

    override fun setLayoutId(): Int = R.layout.activity_setting

    override fun createPresenter(): SettingPresenter = SettingPresenter()


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        title_bar.setOnClickCallback(this)

        tv_version.text = "v " + AppUtils.getAppVersionName(this)


    }

    override fun onResume() {
        super.onResume()
        init()

    }

    private fun init() {
        mPresenter.attachView(this)
        mPresenter.getBoundPay()

        var phoneNumber = mUserInfo.userPhone

        var maskNumber = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length)

        mTvPhoneNum.text = maskNumber

    }

    @OnClick(R.id.ll_bind_phone, R.id.ll_about_us, R.id.ll_feedback, R.id.ll_modify_ps, R.id.ll_black_list,
            R.id.ll_check_version, R.id.rl_logout, R.id.ll_noble_setting, R.id.ll_help, R.id.ll_cache,
            R.id.ll_bind_money_num)
    fun onViewClick(view: View) {
        when (view.id) {
            R.id.ll_bind_phone -> {
                ActStartUtils.startAct(this, BindPhoneAct::class.java)
            }

            R.id.ll_about_us -> {
                startActivity(Intent(this, AboutUsAct::class.java))

            }
            R.id.ll_feedback -> {
                startActivity(Intent(this, FeedBackAct::class.java))

            }
            R.id.ll_modify_ps -> {
                startActivity(Intent(this, ChangePswMineAct::class.java))

            }
            R.id.ll_black_list -> {
                startActivity(Intent(this, BlackListMineAct::class.java))

            }
            R.id.ll_check_version -> {

                mPresenter.getVersion()

            }

            R.id.rl_logout -> {

                TipsDialog.createDialog(this, R.layout.dialog_logout)
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .bindClick(R.id.tv_confirm) { v, dialog ->
                            SPUtils.getInstance().clear()
                            ActStartUtils.startAct(this, LoginActivity::class.java)
                            TRTCVoiceRoom.sharedInstance(this).logout(null)
                            AppActivityStatusUtil.logout();

                        }
                        .bindClick(R.id.tv_cancel) { v, dialog ->
                            dialog.dismiss()
                        }
                        .show()
            }

            R.id.ll_noble_setting -> {
                ActStartUtils.startAct(this, NobleSettingAct::class.java)
            }

            R.id.ll_help -> {
                var bundle = Bundle()
                bundle.putString(Constant.WEB_URL, URLConstant.HELP_URL)
                bundle.putString(Constant.WEB_TITLE, "帮助")
                ActStartUtils.startAct(this, WebActivity::class.java, bundle)
            }

            R.id.ll_cache -> {

                TipsDialog.createDialog(this, R.layout.dialog_cache)
                        .bindClick(R.id.tv_cancel) { v, dialog ->
                            dialog.dismiss()
                        }
                        .bindClick(R.id.tv_confirm) { v, dialog ->
                            Thread(Runnable {
                                glideCachUtils.clearImageAllCache(this)
                            }).start()
                            dialog.dismiss()
                        }
                        .show()

            }

            R.id.ll_bind_money_num -> {
                if (isGet) {
                    if (mResult == null) {
                        ActStartUtils.startAct(this, BindPayAct::class.java)
                    } else {
                        ActStartUtils.startAct(this, BoundPayAct::class.java)
                    }
                }
            }

        }
    }


    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }

    override fun onVersionSuccess(version: CheckVersionBean?) {
        if (version != null) {
            if (AppUtils.getAppVersionName(this) != version.versionNumber) {
                TipsDialog.createDialog(this, R.layout.dialog_version)
                        .bindClick(R.id.iv_close) { v, dialog ->
                            dialog.dismiss()
                        }
                        .bindClick(R.id.tv_up_version) { v, dialog ->
                            openBrowser(version.androidUrl)
                            dialog.dismiss()
                        }
                        .show()
            }

        }

    }


    private fun openBrowser(url: String) {

        var intent = Intent()
        intent.action = "android.intent.action.VIEW"
        var url = Uri.parse(url)
        intent.data = url
        startActivity(intent)

    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun onBindSuccess(result: BindEntity?) {
        isGet = true
        if (result == null) {
            mResult = result
            mTvBindType.text = "未绑定"
            mTvBindType.setTextColor(resources.getColor(R.color.color_333333))
        } else {
            mResult = result
            mTvBindType.text = "已绑定"
            mTvBindType.setTextColor(resources.getColor(R.color.colorPrimary))
        }
    }

    override fun onBindError(msg: String?) {
        ToastUtils.showShort(msg)
    }
}