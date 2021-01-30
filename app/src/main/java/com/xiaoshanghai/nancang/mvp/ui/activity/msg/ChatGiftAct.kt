package com.xiaoshanghai.nancang.mvp.ui.activity.msg

import android.os.Bundle
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.mvp.contract.ChatGiftContract
import com.xiaoshanghai.nancang.mvp.presenter.ChatGiftPresenter

class ChatGiftAct : BaseMvpActivity<ChatGiftPresenter>(), ChatGiftContract.View {

    override fun setLayoutId(): Int = R.layout.activity_chat_gift

    override fun createPresenter(): ChatGiftPresenter = ChatGiftPresenter()


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun isFull(): Boolean = false
}
