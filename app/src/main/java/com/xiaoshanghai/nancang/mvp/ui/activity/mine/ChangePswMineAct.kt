package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.mvp.contract.ChangePswMineContract
import com.xiaoshanghai.nancang.mvp.presenter.ChangePswMinePresenter
import com.xiaoshanghai.nancang.utils.AppUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import kotlinx.android.synthetic.main.act_modifyps.*
import kotlinx.android.synthetic.main.act_modifyps.title_bar

/**
 * 设置-修改登录密码
 */
class ChangePswMineAct : BaseMvpActivity<ChangePswMinePresenter>(),ChangePswMineContract.View {
    override fun getCodeSuccess() {
    }

    override fun getCodeError(msg: String?) {
    }

    override fun resetPswSuccess() {
    }

    override fun resetPswError(msg: String?) {
    }

    override fun changeSuccess() {
        ToastUtils.showShort("密码修改成功")
        finish()
    }

    override fun changeError(message: String?) {
        ToastUtils.showShort(message)
    }

    override fun createPresenter(): ChangePswMinePresenter {
        return ChangePswMinePresenter()
    }

    var ps_old: Boolean = false
    var ps_new: Boolean = false
    var ps_new_confirm: Boolean = false



    override fun setLayoutId(): Int {
        return R.layout.act_modifyps
    }


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        et_psw_old.transformationMethod=PasswordTransformationMethod.getInstance()
        et_psw_new.transformationMethod= PasswordTransformationMethod.getInstance()
        et_psw_new_confirm.transformationMethod= PasswordTransformationMethod.getInstance()


        tv_reset_ps.setOnClickListener {
            startActivity(Intent(this, ResetPsAct::class.java))
        }


        iv_ps_show_old.setOnClickListener {
            ps_old = if (!ps_old){
                iv_ps_show_old.setImageResource(R.mipmap.ic_show_ps)
                et_psw_old.transformationMethod=HideReturnsTransformationMethod.getInstance()
                true
            }else{
                iv_ps_show_old.setImageResource(R.mipmap.ic_hide_ps)
                et_psw_old.transformationMethod=PasswordTransformationMethod.getInstance()
                false
            }
        }


        iv_ps_show_new.setOnClickListener {
            ps_new = if (!ps_new){
                iv_ps_show_new.setImageResource(R.mipmap.ic_show_ps)
                et_psw_new.transformationMethod= HideReturnsTransformationMethod.getInstance()
                true
            }else{
                iv_ps_show_new.setImageResource(R.mipmap.ic_hide_ps)
                et_psw_new.transformationMethod= PasswordTransformationMethod.getInstance()
                false
            }
        }


        iv_ps_show_new_confirm.setOnClickListener {
            ps_new_confirm = if (!ps_new_confirm){
                iv_ps_show_new_confirm.setImageResource(R.mipmap.ic_show_ps)
                et_psw_new_confirm.transformationMethod= HideReturnsTransformationMethod.getInstance()
                true
            }else{
                iv_ps_show_new_confirm.setImageResource(R.mipmap.ic_hide_ps)
                et_psw_new_confirm.transformationMethod= PasswordTransformationMethod.getInstance()
                false
            }
        }


        tv_commit.setOnClickListener {
           if (TextUtils.isEmpty(et_psw_old.text.toString().trim())){
               ToastUtils.showShort("当前登录密码不能为空")
              return@setOnClickListener
           }
            if (TextUtils.isEmpty(et_psw_new.text.toString().trim())){
               ToastUtils.showShort("新的登录密码不能为空")
               return@setOnClickListener
           }
            if (TextUtils.isEmpty(et_psw_new_confirm.text.toString().trim())){
               ToastUtils.showShort("确认新的登录密码不能为空")
               return@setOnClickListener
           }

            if (!AppUtils.isPasswordNO(et_psw_old.text.toString().trim())){
                ToastUtils.showShort("当前登录密码格式错误")
                return@setOnClickListener
            }
             if (!AppUtils.isPasswordNO(et_psw_new.text.toString().trim()) || !AppUtils.isPasswordNO(et_psw_new_confirm.text.toString().trim())){
                 ToastUtils.showShort("新的登录密码格式错误")
                 return@setOnClickListener
            }


            if (et_psw_new.text.toString().trim() != et_psw_new_confirm.text.toString().trim()){
                ToastUtils.showShort("两次输入的密码不一致")
                return@setOnClickListener
            }

           mPresenter.changePsw("2",et_psw_new.text.toString().trim(),null,et_psw_old.text.toString().trim())
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