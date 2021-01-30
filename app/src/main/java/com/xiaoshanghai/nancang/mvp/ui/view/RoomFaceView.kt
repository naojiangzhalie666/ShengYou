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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnFaceFragmentClick
import com.xiaoshanghai.nancang.callback.RoomFaceCallback
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.RoomFaceFragment
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.VipFaceFragment
import com.xiaoshanghai.nancang.net.bean.RoomFace
import com.xiaoshanghai.nancang.view.NoScrollViewPager
import java.util.*


open class RoomFaceView private constructor(): BottomSheetDialogFragment(), View.OnClickListener, OnFaceFragmentClick {

    var mView: View? = null

    var mViewPager: NoScrollViewPager? = null

    var mTvFace: TextView? = null

    var mTvVipFace: TextView? = null


    private val mFragmetns: MutableList<Fragment> = ArrayList()

    var mCallback: RoomFaceCallback? = null

    companion object {
        fun newInstance(): RoomFaceView {
            return RoomFaceView()
        }
    }

    fun setCallback(callback: RoomFaceCallback?) {
        this.mCallback = callback
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mView = inflater.inflate(R.layout.view_room_face, container, false)
        initView()
        return mView

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

    private fun initView() {
        mViewPager = mView?.findViewById(R.id.viewpager)
        mTvFace = mView?.findViewById(R.id.tv_face)
        mTvVipFace = mView?.findViewById(R.id.tv_vip_face)

        mTvFace?.setOnClickListener(this)
        mTvVipFace?.setOnClickListener(this)

        var faceFragment = RoomFaceFragment()
        faceFragment.setOnClick(this)
        var vipFaceFragment = VipFaceFragment()
        vipFaceFragment.setOnClick(this)
        mFragmetns.add(faceFragment)
        mFragmetns.add(vipFaceFragment)

        var mRoomFaceFragment = HomeRadioPageAdapter(childFragmentManager, mFragmetns)
        mViewPager?.adapter = mRoomFaceFragment

        mViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    mTvFace?.setTextColor(ContextCompat.getColor(context!!, R.color.color_ff5f85))
                    mTvVipFace?.setTextColor(ContextCompat.getColor(context!!, R.color.white))

                } else if (position == 1) {
                    mTvVipFace?.setTextColor(ContextCompat.getColor(context!!, R.color.color_ff5f85))
                    mTvFace?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                }
            }

        })


    }

    override fun onResume() {
        super.onResume()

    }


    private fun getPeekHeight(): Int {
        //设置弹窗高度为屏幕高度的3/4
        return dp2px(300f)
    }

    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_face -> {
                mViewPager?.currentItem = 0
            }

            R.id.tv_vip_face -> {
                mViewPager?.currentItem = 1
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (mCallback != null) {
            mCallback?.onDialogDismiss()
        }

    }

    override fun onClickItemResult(roomFace: RoomFace) {
        if (mCallback != null) {

            if (roomFace.isGeneral) {
                mCallback?.onFaceClickResult(roomFace, !roomFace.isGeneral, "0")
            } else {
                mCallback?.onFaceClickResult(roomFace, !roomFace.isGeneral, getFaceNoble(roomFace))

            }
        }
    }

    fun getFaceNoble(roomFace:RoomFace):String {

        when(roomFace.filter) {
            "666" -> {
                return "1"
            }

            "无敌" -> {
                return "2"
            }

            "么么哒" -> {
                return "3"
            }
            "老铁没毛病" -> {
                return "4"
            }
            "嗨起来" -> {
                return "5"
            }

            "滚刀" -> {
                return "6"
            }

            "牛哔了" -> {
                return "7"
            }

            else -> {
                return "0"
            }


        }

    }


}