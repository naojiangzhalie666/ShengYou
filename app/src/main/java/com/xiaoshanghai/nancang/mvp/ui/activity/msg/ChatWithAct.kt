package com.xiaoshanghai.nancang.mvp.ui.activity.msg

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.mvp.contract.ChatWithContract
import com.xiaoshanghai.nancang.mvp.presenter.ChatWithPresenter
import com.xiaoshanghai.nancang.net.bean.Event
import net.lucode.hackware.magicindicator.MagicIndicator

class ChatWithAct : BaseMvpActivity<ChatWithPresenter>(), ChatWithContract.View {

    @BindView(R.id.magic_indicator)
    lateinit var magicIndicator: MagicIndicator

    @BindView(R.id.ll_index)
    lateinit var llIndex: LinearLayout

    @BindView(R.id.ns_view_pager)
    lateinit var nsViewPager: ViewPager

    override fun setLayoutId(): Int = R.layout.activity_chat_with

    override fun createPresenter(): ChatWithPresenter = ChatWithPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mPresenter.initFragment(magicIndicator, nsViewPager)
    }

    @OnClick(R.id.view_finish)
    fun onClick(v: View?){
        when(v?.id) {
            R.id.view_finish -> {
                finish()
            }
        }
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    override fun receiveEvent(event: Event<*>?) {
      if (event?.code == EventConstant.FINIS_CHAT_WITH){
          finish()
      }
    }


}