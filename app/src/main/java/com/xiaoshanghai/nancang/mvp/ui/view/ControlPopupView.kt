package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnCounrolClickListener

class ControlPopupView private constructor() : BottomSheetDialogFragment(), View.OnClickListener {

    private var mTvOnSeat: TextView? = null

    private var mTvPickSeat: TextView? = null

    private var mTvCloseMike: TextView? = null

    private var mTvLockSeat: TextView? = null

    private var mTvCancel: TextView? = null


    private var mCallback: OnCounrolClickListener? = null

    private var mRoomId: String? = null

    companion object {
        fun newInstance(): ControlPopupView {
            var mManagerSeatView = ControlPopupView()
//            val args = Bundle()
//            args.putSerializable(Constant.ROOM_ID, roomId)
//            mManagerSeatView.arguments = args
            return mManagerSeatView
        }
    }

    fun setOnCounrolClick(onSeatClick: OnCounrolClickListener?) {
        this.mCallback = onSeatClick
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mView = inflater.inflate(R.layout.view_control_popup, container, false)
        initView(mView)
        return mView
    }

    fun onSetRoomId(roomId: String) {
        this.mRoomId = roomId
    }

    private fun initView(mView: View?) {
        mTvOnSeat = mView?.findViewById(R.id.tv_onSeat)
        mTvPickSeat = mView?.findViewById(R.id.tv_pick_seat)
        mTvCloseMike = mView?.findViewById(R.id.tv_close_mike)
        mTvLockSeat = mView?.findViewById(R.id.tv_lock_seat)
        mTvCancel = mView?.findViewById(R.id.tv_cancel)

        mTvOnSeat?.setOnClickListener(this)
        mTvPickSeat?.setOnClickListener(this)
        mTvCloseMike?.setOnClickListener(this)
        mTvLockSeat?.setOnClickListener(this)
        mTvCancel?.setOnClickListener(this)

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
        return dp2px(328f)
    }


    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.tv_onSeat -> {     //?????????????????????
                if (mCallback != null && !TextUtils.isEmpty(mRoomId)) {
                    mCallback?.onEnterRoom(mRoomId)
                }
                dismiss()
            }

            R.id.tv_pick_seat -> {//????????????
                mCallback?.onFinishRoom()
                dismiss()
            }

            R.id.tv_close_mike -> {//???????????????
                mCallback?.onMinimize()
                dismiss()
            }

            R.id.tv_lock_seat -> {    //????????????
                mCallback?.onReport()
                dismiss()
            }

            R.id.tv_cancel -> {//??????
                dismiss()
            }
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mCallback?.onCounDismiss()
    }
}