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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpFragment
import com.xiaoshanghai.nancang.callback.DisMissCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.HalfRankFragment
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.RoomListFragment

class RoomRankView private constructor() : BottomSheetDialogFragment(), View.OnClickListener {

    private var mBgView: ConstraintLayout? = null

    private var mTvHalf: TextView? = null

    private var mTvRoom: TextView? = null

//    private var mViewPager: NoScrollViewPager? = null

    private var mRoomId: String? = null

//    private var mFragments: MutableList<Fragment> = ArrayList()

    private var baseFragment: BaseMvpFragment<*>? = null

    private var mHalfRankFragment: HalfRankFragment? = null
    private var mRoomListFragment: RoomListFragment? = null

    private var mCallback: DisMissCallback? = null

    companion object {
        fun newInstance(roomId: String?): RoomRankView {
            val roomGiftView = RoomRankView()
            val args = Bundle()
            args.putString(Constant.ROOM_ID, roomId)
            roomGiftView.arguments = args
            return roomGiftView
        }
    }

    fun setOnDissmissCallback(callback:DisMissCallback?){
        this.mCallback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
        mRoomId = arguments?.getString(Constant.ROOM_ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.view_room_rank, container, false)
        initView(mView)
        return mView
    }

    private fun initView(mView: View) {
        mBgView = mView.findViewById(R.id.bg_view)
        mTvHalf = mView.findViewById(R.id.tv_half)
        mTvRoom = mView.findViewById(R.id.tv_room)
//        mViewPager = mView.findViewById(R.id.nso_viewpager)

        initFragment()

        mTvHalf?.setOnClickListener(this)
        mTvRoom?.setOnClickListener(this)

    }

    private fun initFragment() {
//        mFragments.clear()

         mHalfRankFragment = HalfRankFragment.newInstance(mRoomId)
         mRoomListFragment = RoomListFragment.newInstance(mRoomId)
//        mFragments.add(mHalfRankFragment)
//        mFragments.add(mRoomListFragment)

//        val adapter = HomeRadioPageAdapter(childFragmentManager, mFragments)
//        mViewPager?.adapter = adapter


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
            behavior.isHideable = false
        }

    }

    override fun onResume() {
        super.onResume()
        selectType(0)
    }

    private fun getPeekHeight(): Int {
        //设置弹窗高度为屏幕高度的3/4
        return dp2px(500f)
    }

    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_half -> {
                selectType(0)
            }

            R.id.tv_room -> {
                selectType(1)
            }
        }
    }

    private fun selectType(type: Int) {
        when (type) {
            0 -> {
//                mViewPager?.currentItem = 0
                mTvHalf?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvRoom?.setTextColor(ContextCompat.getColor(context!!, R.color.color_80ffffff))
                mTvHalf?.textSize = 16f
                mTvRoom?.textSize = 12f
                mBgView?.setBackgroundResource(R.drawable.shape_room_rank_pink)
                replace(mHalfRankFragment!!)
            }

            1 -> {
//                mViewPager?.currentItem = 1
                mTvRoom?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvHalf?.setTextColor(ContextCompat.getColor(context!!, R.color.color_80ffffff))
                mTvHalf?.textSize = 12f
                mTvRoom?.textSize = 16f
                mBgView?.setBackgroundResource(R.drawable.shape_room_rank_purple)
                replace(mRoomListFragment!!)
            }
        }
    }

    /**
     * 替换页面
     *
     * @param fragment
     */
    private fun replace(fragment: BaseMvpFragment<*>) {
        if (fragment === baseFragment) return
        val supportFragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            if (baseFragment == null) {
                fragmentTransaction.add(R.id.flContainer, fragment).show(fragment)
            } else if (baseFragment !== fragment) {
                fragmentTransaction.add(R.id.flContainer, fragment).hide(baseFragment!!).show(fragment)
            }
        } else {
            fragmentTransaction.hide(baseFragment!!).show(fragment)
        }
        baseFragment = fragment
        fragmentTransaction.commit()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mCallback?.onDismiss()
    }
}