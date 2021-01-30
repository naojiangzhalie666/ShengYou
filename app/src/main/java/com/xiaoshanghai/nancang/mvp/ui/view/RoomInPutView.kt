package com.xiaoshanghai.nancang.mvp.ui.view

import android.app.Dialog
import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.RoomInPutCallback


class RoomInPutView : Dialog, View.OnClickListener {

    private var mTvDialogTitle: TextView? = null
    private var mEtInPut: EditText? = null
    private var mTvCancel: TextView? = null
    private var mTvConfirm: TextView? = null

    private var mContext: Context? = null

    private var mCallback: RoomInPutCallback? = null

    constructor(context: Context?) : super(context) {
        mContext = context
        setContentView(R.layout.view_room_input)
        window.setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false)
        initView()
    }

    private fun initView() {
        mTvDialogTitle = findViewById(R.id.tv_dialog_title)
        mEtInPut = findViewById(R.id.et_input)
        mTvCancel = findViewById(R.id.tv_cancel)
        mTvConfirm = findViewById(R.id.tv_confirm)

        mTvCancel!!.setOnClickListener(this)
        mTvConfirm!!.setOnClickListener(this)
    }

    fun setDialogCallback(title: String?, input: String?, intputType:Int,callback: RoomInPutCallback?) {
        if (intputType == 1) {
            mEtInPut?.inputType = InputType.TYPE_CLASS_TEXT
        } else if (intputType == 2) {
            mEtInPut?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
        }
        mTvDialogTitle?.text = title
        mEtInPut?.setText(input)
        this.mCallback = callback
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_cancel -> {
                mCallback?.onInputDismiss()
                dismiss()
            }

            R.id.tv_confirm -> {
                mCallback?.onInPutResult(mEtInPut?.text?.trim().toString())
                dismiss()
            }
        }
    }
}