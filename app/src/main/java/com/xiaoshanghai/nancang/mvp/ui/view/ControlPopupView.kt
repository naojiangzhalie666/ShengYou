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
        //获取dialog对象
        val dialog = dialog as BottomSheetDialog?
        dialog?.window?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        //获取diglog的根部局
        val bottomSheet = dialog!!.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)

        if (bottomSheet != null) {
            //获取根部局的LayoutParams对象
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = getPeekHeight()
            //修改弹窗的最大高度，不允许上滑（默认可以上滑）
            bottomSheet.layoutParams = layoutParams
            val behavior = BottomSheetBehavior.from(bottomSheet)
            //peekHeight即弹窗的最大高度
            behavior.peekHeight = getPeekHeight()
            // 初始为展开状态
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

    }

    private fun getPeekHeight(): Int {
        //设置弹窗高度为屏幕高度的3/4
        return dp2px(328f)
    }


    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.tv_onSeat -> {     //随机进入嗨聊房
                if (mCallback != null && !TextUtils.isEmpty(mRoomId)) {
                    mCallback?.onEnterRoom(mRoomId)
                }
                dismiss()
            }

            R.id.tv_pick_seat -> {//退出房间
                mCallback?.onFinishRoom()
                dismiss()
            }

            R.id.tv_close_mike -> {//最小化房间
                mCallback?.onMinimize()
                dismiss()
            }

            R.id.tv_lock_seat -> {    //举报房间
                mCallback?.onReport()
                dismiss()
            }

            R.id.tv_cancel -> {//取消
                dismiss()
            }
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mCallback?.onCounDismiss()
    }
}