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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnGiftCallback
import com.xiaoshanghai.nancang.callback.OnGiftSendCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyGoldAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomGiftSeatAdapter
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.RoomGiftFragment
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.RoomMyGiftFragment
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GiftAllResult
import com.xiaoshanghai.nancang.net.bean.GiftViewSeat
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.NoScrollViewPager
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomGiftView private constructor() : BottomSheetDialogFragment(), View.OnClickListener, OnGiftCallback {

    private var mView: View? = null

    private var mRcySeat: RecyclerView? = null

    private var mTvGift: TextView? = null

    private var mTvNoble: TextView? = null

    private var mTvMagic: TextView? = null

    private var mTvBackPack: TextView? = null

    private var mTvNobleName: TextView? = null

    private var mViewPager: NoScrollViewPager? = null

    private var mRoomGift: GiftAllResult? = null

    private var mRoomId: String? = null

    private var mSelfNoble: String? = null

    private var mGiftSeat: MutableList<GiftViewSeat> = ArrayList()

    private var mSeatAdapter: RoomGiftSeatAdapter? = null

    private var mGiveAway: GiftViewSeat? = null

    private var mFragments: MutableList<Fragment> = ArrayList()

    private var isTotal: Boolean? = false

    private var mCallback: OnGiftSendCallback? = null

    fun setOnGiftSendCallback(callback: OnGiftSendCallback) {
        mCallback = callback
    }

    companion object {
        fun newInstance(roomGift: GiftAllResult?, roomId: String?, selfNoble: String?): RoomGiftView {
            val roomGiftView = RoomGiftView()
            val args = Bundle()
            args.putSerializable(Constant.ROOM_GIFT, roomGift)
            args.putString(Constant.ROOM_ID, roomId)
            args.putString(Constant.SELF_NOBLE, selfNoble)
            roomGiftView.arguments = args
            return roomGiftView
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
        mRoomGift = arguments?.getSerializable(Constant.ROOM_GIFT) as GiftAllResult?
        mRoomId = arguments?.getString(Constant.ROOM_ID)
        mSelfNoble = arguments?.getString(Constant.SELF_NOBLE)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.view_room_gift, container, false)
        initView()
        return mView
    }

    private fun initView() {
        mRcySeat = mView?.findViewById(R.id.rcy_seat)
        mTvGift = mView?.findViewById(R.id.tv_gift)
        mTvNoble = mView?.findViewById(R.id.tv_noble)
        mTvMagic = mView?.findViewById(R.id.tv_magic)
        mTvBackPack = mView?.findViewById(R.id.tv_backpack)
        mViewPager = mView?.findViewById(R.id.viewpager)
        mTvNobleName = mView?.findViewById(R.id.tv_noble_name)

        mTvGift?.setOnClickListener(this)
        mTvNoble?.setOnClickListener(this)
        mTvMagic?.setOnClickListener(this)
        mTvBackPack?.setOnClickListener(this)
        mTvNobleName?.setOnClickListener(this)

        initFragment()

        setNobleName(mSelfNoble)

    }

    private fun setNobleName(noble: String?) {
        when (noble) {
            "0" -> {
                mTvNobleName?.text = "成为贵族"
            }

            "1" -> {
                mTvNobleName?.text = "男爵贵族"
            }

            "2" -> {
                mTvNobleName?.text = "子爵贵族"
            }

            "3" -> {
                mTvNobleName?.text = "伯爵贵族"
            }

            "4" -> {
                mTvNobleName?.text = "侯爵贵族"
            }

            "5" -> {
                mTvNobleName?.text = "公爵贵族"
            }

            "6" -> {
                mTvNobleName?.text = "国王贵族"

            }

            "7" -> {

                mTvNobleName?.text = "皇帝贵族"
            }

            else -> {
                mTvNobleName?.text = "成为贵族"
            }

        }
    }


    private fun initFragment() {

        mFragments.clear()

        val mRoomGiftFragment = RoomGiftFragment.newInstance(mRoomGift?.generals, 0)
        mRoomGiftFragment.setOnGiftCallback(this)

        val mRoomNobleFragment = RoomGiftFragment.newInstance(mRoomGift?.nobles, 1)
        mRoomNobleFragment.setOnGiftCallback(this)

        val mRoomMagicFragment = RoomGiftFragment.newInstance(mRoomGift?.magics, 2)
        mRoomMagicFragment.setOnGiftCallback(this)

        val mPackageFragment = RoomMyGiftFragment.newInstance(mRoomGift?.packages, 3)
        mPackageFragment.setOnGiftCallback(this)

        mFragments.add(mRoomGiftFragment)
        mFragments.add(mRoomNobleFragment)
        mFragments.add(mRoomMagicFragment)
        mFragments.add(mPackageFragment)

        val adapter = HomeRadioPageAdapter(childFragmentManager, mFragments)
        mViewPager!!.adapter = adapter


        mSeatAdapter = RoomGiftSeatAdapter(mGiftSeat)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRcySeat?.layoutManager = linearLayoutManager
        mRcySeat?.adapter = mSeatAdapter


        mSeatAdapter?.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int ->
            val giftViewSeat = mGiftSeat[position]

            giftViewSeat.isSelect = !giftViewSeat.isSelect!!

            //是否为全麦按钮
            if (giftViewSeat.isAllSelect!!) {

                for (seat in mGiftSeat) {
                    seat.isSelect = giftViewSeat.isSelect
                }

                isTotal = giftViewSeat.isSelect

            } else {   //不为全麦

                if (mGiftSeat[0].isAllSelect!!) {

                    var isAll = true

                    for (seat in mGiftSeat) {
                        if (!seat.isSelect!! && !seat.isAllSelect!!) {
                            isAll = false
                            break
                        }
                    }
                    mGiftSeat[0].isSelect = isAll
                    isTotal = isAll
                }
            }

            mSeatAdapter?.notifyDataSetChanged()

        }
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
        mViewPager?.currentItem = 0
        mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
        mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
        mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
        mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))

    }

    private fun getPeekHeight(): Int {
        //设置弹窗高度为屏幕高度的3/4
        return dp2px(420f)
    }

    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_gift -> {
                mViewPager?.currentItem = 0
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))

            }

            R.id.tv_noble -> {
                mViewPager?.currentItem = 1
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
            }

            R.id.tv_magic -> {
                mViewPager?.currentItem = 2
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))

            }

            R.id.tv_backpack -> {
                mViewPager?.currentItem = 3
                mTvGift?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvNoble?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvMagic?.setTextColor(ContextCompat.getColor(context!!, R.color.color_b3b3b3))
                mTvBackPack?.setTextColor(ContextCompat.getColor(context!!, R.color.white))
            }

            R.id.tv_noble_name -> {
                if (mCallback != null) {
                    mCallback?.onNobleClick()
                }
            }
        }

    }


    fun setGiveAway(giftSeat: GiftViewSeat?, mainSeat: VoiceRoomSeatEntity?, seatInfo: MutableList<VoiceRoomSeatEntity>?) {
        mGiveAway = giftSeat

        setSeatAdapter(mainSeat, seatInfo)

    }


    fun setSeatAdapter(mainSeat: VoiceRoomSeatEntity?, seatInfo: MutableList<VoiceRoomSeatEntity>?) {

        var mSeat: MutableList<VoiceRoomSeatEntity> = ArrayList()
        if (mainSeat == null || seatInfo == null) return
        mSeat.add(mainSeat!!)
        mSeat.addAll(seatInfo!!)

        mGiftSeat.clear()


        //整合主麦位和麦位上的所有人 剔除自己
        for (i in mSeat.indices) {

            if (mSeat?.get(i).isUsed) {
                if (mSeat[i].userId != SPUtils.getInstance().userInfo.id) {
                    var seat: GiftViewSeat?
                    seat = if (i == 0) {
                        GiftViewSeat(mSeat[i].userId, mSeat[i].userName, mSeat[i].userAvatar, mSeat[i].userInfo.noble, index = i, isAllSelect = false, isRoom = true, isSelect = false,isSeat = true)
                    } else {
                        GiftViewSeat(mSeat[i].userId, mSeat[i].userName, mSeat[i].userAvatar, mSeat[i].userInfo.noble, index = i, isAllSelect = false, isRoom = false, isSelect = false,isSeat = true)
                    }

                    mGiftSeat.add(seat)
                }
            }
        }

        //判断有没有传用户进来看是否为单独点击右下角
        if (mGiveAway == null) {

            val giftViewSeat = GiftViewSeat(null, null, null, null, null, isAllSelect = true, isRoom = false, isSelect = false, isSeat = true)
            mGiftSeat.add(0, giftViewSeat)

        } else {

            var isInSeat = false  //判断是否在座位上

            for (giftViewSeat in mGiftSeat) {
                if (giftViewSeat.userId == mGiveAway?.userId) {
                    isInSeat = true
                    giftViewSeat.isSelect = true
                    break
                }
            }

            if (isInSeat) { //在麦位上添加全麦按钮
                val giftViewSeat = GiftViewSeat(null, null, null, null, null, isAllSelect = true, isRoom = false, isSelect = false,isSeat = true)
                mGiftSeat.add(0, giftViewSeat)
            } else {    //不在麦位上单独添加到座位列表上
                mGiveAway?.index = -1
                mGiveAway?.isSelect = true
                mGiveAway?.isSeat = false
                mGiftSeat.clear()
                mGiftSeat.add(mGiveAway!!)

            }

        }

        mSeatAdapter?.data = mGiftSeat
        mSeatAdapter?.notifyDataSetChanged()

    }

    override fun setGiftResult(result: RoomGiftResult?, num: Int, mFragmentIndex: Int) {
        var select: MutableList<GiftViewSeat> = ArrayList()

        for (giftViewSeat in mGiftSeat) {
            if (!giftViewSeat.isAllSelect!! && giftViewSeat.isSelect!!) {
                select.add(giftViewSeat)
            }
        }

        if (select.isNotEmpty()) {

            if (mFragmentIndex == 3) {
                sendGift(mRoomId, result, select, 2, num, mFragmentIndex, isTotal)
            } else {
                sendGift(mRoomId, result, select, 1, num, mFragmentIndex, isTotal)
            }

        } else {
            ToastUtils.showShort("当前没有可以接受礼物的人")
        }

    }

    override fun rechargeEntry() {
        ActStartUtils.startAct(activity, MyGoldAct::class.java)
        dismiss()
    }

    private fun sendGift(roomId: String?, giftResult: RoomGiftResult?, userIds: MutableList<GiftViewSeat>?, type: Int, number: Int, mFragmentIndex: Int, isTotal: Boolean?) {


        var userIdList: MutableList<String> = ArrayList()
        userIds?.forEach {
            userIdList.add(it.userId!!)
        }


        HttpClient.getApi().giveGift(roomId, giftResult?.id, userIdList, type, number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        if (bean == 1) {
                            if (mFragmentIndex == 3) {
                                (mFragments[mFragmentIndex] as RoomMyGiftFragment).getPackageGift()
                            } else {
                                (mFragments[mFragmentIndex] as RoomGiftFragment).getGoldAndChili()
                            }
                        }

                        if (mCallback != null) {
                            mCallback?.onSendGiftResult(bean, userIds, giftResult, number, isTotal)
                        }

                    }

                    override fun error(msg: String?) {

                    }

                })

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mCallback?.onGiftDismiss()
    }

}