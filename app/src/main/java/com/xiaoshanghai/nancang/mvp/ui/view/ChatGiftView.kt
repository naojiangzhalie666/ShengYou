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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.callback.OnChatGiftCallback
import com.xiaoshanghai.nancang.callback.OnGiftCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.MyGoldAct
import com.xiaoshanghai.nancang.mvp.ui.adapter.ChatGiftSeatAdapter
import com.xiaoshanghai.nancang.mvp.ui.adapter.HomeRadioPageAdapter
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.RoomGiftFragment
import com.xiaoshanghai.nancang.mvp.ui.fragment.studio.RoomMyGiftFragment
import com.xiaoshanghai.nancang.net.HttpClient
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.ChatGiftSeat
import com.xiaoshanghai.nancang.net.bean.GiftAllResult
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.NoScrollViewPager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChatGiftView private constructor() : BottomSheetDialogFragment(), View.OnClickListener, OnGiftCallback {

    private var mView: View? = null

    private var mRcySeat: RecyclerView? = null

    private var mTvGift: TextView? = null

    private var mTvNoble: TextView? = null

    private var mTvMagic: TextView? = null

    private var mTvBackPack: TextView? = null

    private var mTvNobleName: TextView? = null

    private var mViewPager: NoScrollViewPager? = null

    private var mRoomGift: GiftAllResult? = null

    private var mSelfNoble: String? = null

    private var mGiftSeat: MutableList<ChatGiftSeat> = ArrayList()

    private var mSeatAdapter: ChatGiftSeatAdapter? = null

    private var mGiveAway: ChatGiftSeat? = null

    private var mFragments: MutableList<Fragment> = ArrayList()

    private var isTotal: Boolean? = false

    private var mCallback: OnChatGiftCallback? = null

    fun setOnGiftSendCallback(callback: OnChatGiftCallback) {
        mCallback = callback
    }

    companion object {
        fun newInstance(roomGift: GiftAllResult?, selfNoble: String?): ChatGiftView {
            val chatGiftView = ChatGiftView()
            val args = Bundle()
            args.putSerializable(Constant.ROOM_GIFT, roomGift)
            args.putString(Constant.SELF_NOBLE, selfNoble)
            chatGiftView.arguments = args
            return chatGiftView
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
        mRoomGift = arguments?.getSerializable(Constant.ROOM_GIFT) as GiftAllResult?
        mSelfNoble = arguments?.getString(Constant.SELF_NOBLE)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.view_chat_gift, container, false)
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


        mSeatAdapter = ChatGiftSeatAdapter(mGiftSeat)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRcySeat?.layoutManager = linearLayoutManager
        mRcySeat?.adapter = mSeatAdapter

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


    fun setGiveAway(giftSeat: ChatGiftSeat?) {
        mGiveAway = giftSeat
        mGiftSeat.clear()
        mGiftSeat.add(mGiveAway!!)

        mSeatAdapter?.notifyDataSetChanged()

    }

    override fun setGiftResult(result: RoomGiftResult?, num: Int, fragmentIndex: Int) {


        if (mGiveAway != null) {

            var userIds:MutableList<String> = ArrayList()
            userIds.add(mGiveAway?.userId!!)

            if (fragmentIndex == 3) {

                sendGift( result, userIds, 2, num, fragmentIndex, isTotal,mGiveAway)
            } else {
                sendGift( result, userIds, 1, num, fragmentIndex, isTotal,mGiveAway)
            }

        } else {
            ToastUtils.showShort("当前没有可以接受礼物的人")
        }

    }

    override fun rechargeEntry() {
        ActStartUtils.startAct(activity, MyGoldAct::class.java)
        dismiss()
    }

    private fun sendGift( giftResult: RoomGiftResult?, userIds: MutableList<String>?, type: Int, number: Int, mFragmentIndex: Int, isTotal: Boolean?,mGiveAway: ChatGiftSeat?) {

        HttpClient.getApi().giveGift("-1", giftResult?.id, userIds, type, number)
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
                            mCallback?.onSendGiftResult(bean, userIds, giftResult, number, isTotal,mGiveAway)
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