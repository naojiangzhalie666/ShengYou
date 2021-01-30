package com.xiaoshanghai.nancang.mvp.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnRowMikeCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.ui.adapter.RowSeatViewAdapter
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.RowSeatResult
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomRowMikeView private constructor() : BottomSheetDialogFragment(), View.OnClickListener {

    lateinit var mTvTis: TextView
    lateinit var mTvTis2: TextView

    lateinit var mRcySeatUser: RecyclerView

    lateinit var mTvSingUp: TextView

    private var mKind = -1

    private var mRoomId: String? = null

    private var mAdapter: RowSeatViewAdapter? = null

    private var userList: MutableList<RowSeatResult>? = null

    private var mCallback: OnRowMikeCallback? = null

    companion object {
        fun newInstance(kind: Int, roomId: String?): RoomRowMikeView {
            var mRoomRowMike = RoomRowMikeView()
            var bundle = Bundle()
            bundle.putInt(Constant.ROOM_SELF_KIND, kind)
            bundle.putString(Constant.ROOM_ID, roomId)
            mRoomRowMike.arguments = bundle
            return mRoomRowMike
        }
    }

    fun setOnRowMikeCallback(callback: OnRowMikeCallback){
        this.mCallback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mKind = arguments?.getInt(Constant.ROOM_SELF_KIND, -1)!!
        mRoomId = arguments?.getString(Constant.ROOM_ID)
        mAdapter = RowSeatViewAdapter(mKind)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var mView = inflater.inflate(R.layout.view_room_row_mike, container, false)

        initView(mView)

        return mView

    }

    private fun initView(view: View) {
        mTvTis = view.findViewById(R.id.tv_tis)
        mTvTis2 = view.findViewById(R.id.tv_tis2)
        mRcySeatUser = view.findViewById(R.id.rcy_seat_user)
        mTvSingUp = view.findViewById(R.id.tv_sing_up)

        mTvSingUp.setOnClickListener(this)

        mRcySeatUser.layoutManager = LinearLayoutManager(context)
        mRcySeatUser.adapter = mAdapter

        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            if (mKind == 1 || mKind == 2) {
                val item = mAdapter?.getItem(position)
                managerRemove(item)
            }

        }


        getSeatUser(mRoomId!!)
    }

    fun managerRemove(rowSeat: RowSeatResult?) {
        HttpClient.getApi().removeSing(rowSeat?.id, rowSeat?.userId, rowSeat?.roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        if (bean == "1") {
                            getSeatUser(mRoomId!!)
                            mCallback?.onRowMikeCallback(rowSeat)

                        } else {
                            ToastUtils.showShort("取消报名失败")
                        }
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                })
    }

    private fun getSeatUser(roomId: String) {
        HttpClient.getApi().getRowSeatUser(roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<MutableList<RowSeatResult>>() {
                    override fun success(bean: MutableList<RowSeatResult>?, response: BaseResponse<MutableList<RowSeatResult>>?) {
                        userList = bean

                        setRowSeatResult(bean)
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                })
    }

    private fun setRowSeatResult(bean: MutableList<RowSeatResult>?) {


        if (mKind == 3) {

            val selfUserId = SPUtils.getInstance().userInfo.id

            val mine = getMine(selfUserId, bean)

            setAudienceResult(mine, audienceList = bean)
        } else if (mKind == 1 || mKind == 2) {
            setManagerResult(bean)
        }


    }

    private fun setManagerResult(audienceList: MutableList<RowSeatResult>?) {


        mTvSingUp.visibility = View.GONE

        if (audienceList != null && audienceList.size > 0) {
            mTvTis.text = "当前排麦人数:"
            mTvTis.visibility = View.VISIBLE
            mTvTis2.text = audienceList.size.toString()
            mTvTis2.visibility = View.VISIBLE

            mAdapter?.data = audienceList
            mAdapter?.notifyDataSetChanged()

        } else {
            mTvTis.visibility = View.GONE
            mTvTis2.visibility = View.GONE
            mAdapter?.setEmptyView(R.layout.empty_row_manager_mike)
            mAdapter?.data = audienceList!!
            mAdapter?.notifyDataSetChanged()
        }
    }

    private fun setAudienceResult(mine: RowSeatResult?, audienceList: MutableList<RowSeatResult>?) {
        mTvSingUp.visibility = View.VISIBLE
        if (audienceList != null && audienceList.size > 0) {

            if (mine == null) {
                mTvTis.text = "报名才能排麦喔~"
                mTvTis.visibility = View.VISIBLE
                mTvTis2.visibility = View.GONE

                mTvSingUp.text = "点击报名"
                mTvSingUp.setBackgroundResource(R.drawable.shape_p_r20_bg)
            } else {
                mTvTis.text = "我在队列中排列位置:"
                mTvTis2.text = mine?.index.toString()
                mTvTis.visibility = View.VISIBLE
                mTvTis2.visibility = View.VISIBLE

                mTvSingUp.text = "取消报名"
                mTvSingUp.setBackgroundResource(R.drawable.shape_g_r20_bg)
            }

            mAdapter?.data = audienceList
            mAdapter?.notifyDataSetChanged()

        } else {
            mTvTis.text = "报名才能排麦喔~"
            mTvTis.visibility = View.VISIBLE
            mTvTis2.visibility = View.GONE
            mAdapter?.setEmptyView(R.layout.empty_row_mike)
            mTvSingUp.text = "点击报名"
            mTvSingUp.setBackgroundResource(R.drawable.shape_p_r20_bg)
            mAdapter?.data = audienceList!!
            mAdapter?.notifyDataSetChanged()

        }
    }


    private fun getMine(selfUserId: String, rowSeat: MutableList<RowSeatResult>?): RowSeatResult? {
        var selfRowSeat: RowSeatResult? = null
        if (rowSeat == null || rowSeat.size <= 0) {
            return selfRowSeat
        }
        for (i in rowSeat!!.indices) {
            if (rowSeat[i].userId == selfUserId) {
                rowSeat[i].index = i + 1
                selfRowSeat = rowSeat[i]
                break
            }
        }

        return selfRowSeat
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

    private fun getPeekHeight(): Int {
        //设置弹窗高度为屏幕高度的3/4
        return dp2px(484f)
    }


    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_sing_up -> {

                if (mKind == 3) {
                    val mine = getMine(SPUtils.getInstance().userInfo.id, userList)
                    if (mine == null) {
                        ToastUtils.showShort("报名")
                        singUp(SPUtils.getInstance().userInfo.id)
                    } else {
                        ToastUtils.showShort("取消报名")
                        removeSing(mine)
                    }
                }
            }
        }

    }

    private fun singUp(userId: String) {
        HttpClient.getApi().singUp(userId, mRoomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        if (bean == 1) {
                            getSeatUser(mRoomId!!)
                        } else {
                            ToastUtils.showShort("报名失败")
                        }
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }


                })
    }

    private fun removeSing(rowSeat: RowSeatResult?) {
        HttpClient.getApi().removeSing(rowSeat?.id, rowSeat?.userId, rowSeat?.roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        if (bean == "1") {
                            getSeatUser(mRoomId!!)
                        } else {
                            ToastUtils.showShort("取消报名失败")
                        }
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                })
    }


}