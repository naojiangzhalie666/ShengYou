package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.xiaoshanghai.nancang.callback.OnSeatClickCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.RoomSeatConstant
import com.xiaoshanghai.nancang.net.bean.RoomSeat

class ManagerSeatView private constructor() : BottomSheetDialogFragment(), View.OnClickListener {

    private var mTvOnSeat: TextView? = null

    private var mTvPickSeat: TextView? = null

    private var mTvCloseMike: TextView? = null

    private var mTvLockSeat: TextView? = null

    private var mTvCancel: TextView? = null

    private var userSeat: RoomSeat? = null

    private var mOnSeatClickCallback: OnSeatClickCallback? = null

    companion object {
        fun newInstance(userSeat: RoomSeat): ManagerSeatView {
            var mManagerSeatView = ManagerSeatView()
            val args = Bundle()
//            args.putInt(Constant.ROOM_SELF_KIND, selfKind)
            args.putSerializable(Constant.ROOM_USER_SEAT, userSeat)
            mManagerSeatView.arguments = args
            return mManagerSeatView
        }
    }

    fun setOnSeatClickCallback(onSeatClick: OnSeatClickCallback?) {
        this.mOnSeatClickCallback = onSeatClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userSeat = arguments?.getSerializable(Constant.ROOM_USER_SEAT) as RoomSeat?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mView = inflater.inflate(R.layout.view_manager_seat, container, false)
        initView(mView)
        return mView
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

        initData(userSeat)
    }

    private fun initData(userSeat: RoomSeat?) {
        mTvCloseMike?.text = if (userSeat?.isMute!!) "开麦" else "闭麦"
        mTvLockSeat?.text = if (userSeat?.isClose!!) "解锁麦" else "锁麦"
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

            R.id.tv_onSeat -> {     //上麦
                mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.UP_SEAT, null, userSeat)
                dismiss()
            }

            R.id.tv_pick_seat -> {
                mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.PICK_SEAT, null, userSeat)
                dismiss()
            }

            R.id.tv_close_mike -> {
                if (userSeat?.isMute!!) {       //麦位处于静音状态 打开麦克风
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.OPEN_MIKE, null, userSeat)
                } else {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.CLOSE_MIKE, null, userSeat)
                }
                dismiss()
            }

            R.id.tv_lock_seat -> {
                if (userSeat?.isClose!!) {       //麦位处于关闭状态 打开麦位
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.UNLOCK_MIKE, null, userSeat)
                } else {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.LOCK_MIKE, null, userSeat)
                }
                dismiss()
            }

            R.id.tv_cancel -> {
                dismiss()
            }
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.MANAGER_VIEW_DISMISS, null, null)
    }
}