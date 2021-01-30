package com.xiaoshanghai.nancang.mvp.ui.activity.studio

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import butterknife.OnTextChanged
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpActivity
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.EventConstant
import com.xiaoshanghai.nancang.mvp.contract.RoomNotiyContract
import com.xiaoshanghai.nancang.mvp.presenter.RoomNotiyPresenter
import com.xiaoshanghai.nancang.mvp.ui.view.TitleBarView
import com.xiaoshanghai.nancang.net.bean.Event
import com.xiaoshanghai.nancang.net.bean.RoomNotiyEntity
import com.xiaoshanghai.nancang.utils.EventBusUtil
import com.xiaoshanghai.nancang.utils.ToastUtils

class RoomNotiyAct : BaseMvpActivity<RoomNotiyPresenter>(), RoomNotiyContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.et_notiy_title)
    lateinit var mEtNotiyTitle: EditText

    @BindView(R.id.tv_title_num)
    lateinit var mTvTitleNum: TextView

    @BindView(R.id.et_notiy_commen)
    lateinit var mEtNotiyCommen: EditText

    @BindView(R.id.tv_notiy_commen_num)
    lateinit var mTvNotiyCommenNun: TextView

    @BindView(R.id.tv_commit)
    lateinit var mTvCommit: TextView


    private var mRoomTitle: String? = null
    private var mRoomComment: String? = null
    private var mRoomId: String? = null


    override fun setLayoutId(): Int = R.layout.activity_room_notiy

    override fun createPresenter(): RoomNotiyPresenter {
        return RoomNotiyPresenter()
    }


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)

        mTitleBar.setOnClickCallback(this)

        mRoomTitle = intent.extras.getString(Constant.ROOM_TITLE)

        mRoomComment = intent.extras.getString(Constant.ROOM_COMMENT)

        mRoomId = intent.extras.getString(Constant.ROOM_ID)
        if (!TextUtils.isEmpty(mRoomTitle)) {
            mEtNotiyTitle.setText(mRoomTitle)
        }
        if (!TextUtils.isEmpty(mRoomComment)) {
            mEtNotiyCommen.setText(mRoomComment)
        }
    }

    @OnTextChanged(R.id.et_notiy_title)
    fun onTitleChanged(editable: Editable) {
        mTvCommit.isEnabled = mEtNotiyTitle.length() > 0 && mEtNotiyCommen.length() > 0
        mTvTitleNum.text = mEtNotiyTitle.text.trim().length.toString()
    }

    @OnTextChanged(R.id.et_notiy_commen)
    fun onCommentChangexd(editable: Editable) {
        mTvCommit.isEnabled = mEtNotiyTitle.length() > 0 && mEtNotiyCommen.length() > 0

        mTvNotiyCommenNun.text = mEtNotiyCommen.text.trim().length.toString()
    }

    @OnClick(R.id.tv_commit)
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_commit -> {
                commit()
            }
        }
    }

    private fun commit() {
        mPresenter.editRoomNotify(mRoomId, mEtNotiyTitle.text.trim().toString(), mEtNotiyCommen.text.trim().toString())
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }

    override fun onSuccess(status: Int?) {
        if (status == 1) {
            ToastUtils.showShort("成功")
            var notiyEntity = RoomNotiyEntity(mEtNotiyTitle.text.trim().toString(), mEtNotiyCommen.text.trim().toString())
            EventBusUtil.sendEvent(Event(EventConstant.ROOM_NOTIY, notiyEntity))
            finish()
        } else {
            ToastUtils.showShort("设置公告失败")
        }
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }
}
