package com.xiaoshanghai.nancang.mvp.ui.activity.mine

import android.os.Bundle
import android.text.TextUtils
import android.widget.CompoundButton
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.mvp.contract.FeedBackContract
import com.xiaoshanghai.nancang.mvp.presenter.FeedBackPresenter
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import kotlinx.android.synthetic.main.act_feedback.*


/**
 * 提交反馈
 */
class FeedBackAct : BaseMvpActivity<FeedBackPresenter>(), FeedBackContract.View {
    var type :String ="1" //1 QQ   2 wx
    override fun feedBackSuccess() {
        ToastUtils.showShort("反馈提交成功")
        finish()
    }

    override fun feedBackError(msg: String) {
        ToastUtils.showShort(msg)
    }


    override fun createPresenter(): FeedBackPresenter {
        return FeedBackPresenter()
    }


    override fun setLayoutId(): Int {
        return R.layout.act_feedback

    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        title_bar.setOnClickCallback(object : TitleBarClickCallback {

            override fun titleLeftClick() {
                finish()
            }


            override fun titleRightClick(status: Int) {
            }

        })

        btn_switch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {

            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (!isChecked) {
                    tv_qq.setTextColor(resources.getColor(R.color.color_ff5f85))
                    tv_wx.setTextColor(resources.getColor(R.color.color_787878))
                    type="1"
                } else {
                    tv_wx.setTextColor(resources.getColor(R.color.color_ff5f85))
                    tv_qq.setTextColor(resources.getColor(R.color.color_787878))
                    type="2"

                }
            }

        })
        tv_feedback.setOnClickListener {
            if(TextUtils.isEmpty(et_message.text.toString().trim()))   {
                ToastUtils.showShort("请先填写反馈内容")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(et_num.text.toString().trim()))   {
                ToastUtils.showShort("请先输入您的号码")
                return@setOnClickListener
            }
            mPresenter.feedBack(et_message.text.toString().trim(),type,et_num.text.toString().trim(),SPUtils.getInstance().userInfo.id)
        }
    }

}