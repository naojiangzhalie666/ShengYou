package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.RoomSettingCallback

class RoomSettingView private constructor() : BottomSheetDialogFragment(), View.OnClickListener {

    private var mIvTx: ImageView? = null

    private var mTvTx: TextView? = null


    private var mIvGp: ImageView? = null

    private var mTvGp: TextView? = null

    private var mIvLwz: ImageView? = null

    private var mTvLwz: TextView? = null

    private var mIvRoomSetting: ImageView? = null

    private var mTvRoomSetting: TextView? = null

    private var isOpenTx = true

    private var isOpenGp = true

    private var isOpenLwz = true

    private var mUserKind: Int = 3

    private var mCallback: RoomSettingCallback? = null


    companion object {
        fun newInstance(): RoomSettingView {
            return RoomSettingView()
        }
    }

    fun setRoomSettingCallback(callback: RoomSettingCallback?) {
        this.mCallback = callback
    }

    fun setSwitch(isOpenTx: Boolean?, isOpenGp: Boolean?, isOpenLwz: Boolean?, userKind: Int?) {
        if (isOpenTx != null) {
            this.isOpenTx = isOpenTx
        }
        if (isOpenGp != null) {
            this.isOpenGp = isOpenGp
        }

        if (isOpenLwz != null) {
            this.isOpenLwz = isOpenLwz
        }

        this.mUserKind = userKind!!
        setSwitch()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var mView = inflater.inflate(R.layout.view_room_setting, container, false)

        initView(mView)

        return mView

    }

    private fun initView(view: View) {

        mIvTx = view.findViewById(R.id.iv_tx)
        mTvTx = view.findViewById(R.id.tv_tx)
        mIvGp = view.findViewById(R.id.iv_gp)
        mTvGp = view.findViewById(R.id.tv_gp)
        mIvLwz = view.findViewById(R.id.iv_lwz)
        mTvLwz = view.findViewById(R.id.tv_lwz)
        mIvRoomSetting = view.findViewById(R.id.iv_room_setting)
        mIvTx?.setOnClickListener(this)
        mIvGp?.setOnClickListener(this)
        mIvLwz?.setOnClickListener(this)
        mIvRoomSetting?.setOnClickListener(this)

        setSwitch()


    }

    private fun setSwitch() {
        if (isOpenTx != null) {
            mIvTx?.setImageResource(if (isOpenTx) R.mipmap.icon_open_tx else R.mipmap.icon_close_tx)
            mTvTx?.text = if (isOpenTx) "??????????????????" else "??????????????????"
        }

        if (isOpenGp != null) {
            mIvGp?.setImageResource(if (isOpenGp) R.mipmap.icon_open_gp else R.mipmap.icon_close_gp)
            mTvGp?.text = if (isOpenGp) "????????????" else "????????????"
        }

        if (isOpenLwz != null) {
            mIvLwz?.setImageResource(if (isOpenLwz) R.mipmap.icon_open_lw else R.mipmap.icon_close_lw)
            mTvLwz?.text = if (isOpenLwz) "???????????????" else "???????????????"
        }

        if (mUserKind == 1 || mUserKind == 2) {

            mIvGp?.visibility = View.VISIBLE
            mTvGp?.visibility = View.VISIBLE

            mIvLwz?.visibility = View.VISIBLE
            mTvLwz?.visibility = View.VISIBLE

            mIvRoomSetting?.visibility = View.VISIBLE
            mTvRoomSetting?.visibility = View.VISIBLE
        } else {
            mIvGp?.visibility = View.INVISIBLE
            mTvGp?.visibility = View.INVISIBLE

            mIvLwz?.visibility = View.INVISIBLE
            mTvLwz?.visibility = View.INVISIBLE

            mIvRoomSetting?.visibility = View.INVISIBLE
            mTvRoomSetting?.visibility = View.INVISIBLE
        }

    }


    override fun onStart() {
        super.onStart()
        //??????dialog??????
        val dialog = dialog as BottomSheetDialog?
        dialog?.window?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        //??????diglog????????????
        val bottomSheet = dialog!!.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)

        if (bottomSheet != null) {
            //??????????????????LayoutParams??????
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = getPeekHeight()
            //?????????????????????????????????????????????????????????????????????
            bottomSheet.layoutParams = layoutParams
            val behavior = BottomSheetBehavior.from(bottomSheet)
            //peekHeight????????????????????????
            behavior.peekHeight = getPeekHeight()
            // ?????????????????????
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

    }

    private fun getPeekHeight(): Int {
        //????????????????????????????????????3/4
        return dp2px(150f)
    }


    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.iv_tx -> {

                mCallback?.onTxSwitch(!isOpenTx)
                dismiss()
            }

            R.id.iv_gp -> {
                mCallback?.onGpSwitch(!isOpenGp)
                dismiss()
            }

            R.id.iv_lwz -> {
                mCallback?.onLwzSwitch(!isOpenLwz)
                dismiss()
            }

            R.id.iv_room_setting -> {
                mCallback?.onSetting()
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mCallback?.onSettingDismiss()
    }


}