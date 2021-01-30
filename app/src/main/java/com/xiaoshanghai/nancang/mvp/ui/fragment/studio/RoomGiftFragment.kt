package com.xiaoshanghai.nancang.mvp.ui.fragment.studio

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpFragment
import com.xiaoshanghai.nancang.callback.OnGiftCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.RoomGiftContract
import com.xiaoshanghai.nancang.mvp.presenter.RoomGiftPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomFaceVPAdapter
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomGiftAdapter
import com.xiaoshanghai.nancang.net.bean.RoomGiftResult
import com.xiaoshanghai.nancang.utils.NumberUtils
import com.xiaoshanghai.nancang.utils.OutUtils
import com.xiaoshanghai.nancang.utils.ToastUtils
import com.xiaoshanghai.nancang.view.DialogOhteNumView
import com.xiaoshanghai.nancang.view.TipsDialog
import com.tencent.qcloud.tim.uikit.component.face.EmojiIndicatorView
import java.io.Serializable

class RoomGiftFragment private constructor() : BaseMvpFragment<RoomGiftPresenter>(), RoomGiftContract.View {

    @BindView(R.id.vp_gift)
    lateinit var mVpGift: ViewPager

    @BindView(R.id.index)
    lateinit var mIndex: EmojiIndicatorView

    @BindView(R.id.tv_money)
    lateinit var mTvMoney: TextView//礼物价值

    @BindView(R.id.tv_money_type)
    lateinit var mTvMoneyType: TextView //礼物价值属性

    @BindView(R.id.tv_recharge)
    lateinit var mTvRecharge: TextView//充值入口

    @BindView(R.id.tv_gift_num)
    lateinit var mTvGiftNum: TextView//送礼数

    @BindView(R.id.iv_arrow)
    lateinit var mIvArrow: ImageView//箭头

    @BindView(R.id.tv_give_away)
    lateinit var mTvGiveAway: TextView //赠送按钮

    @BindView(R.id.ll_options)
    lateinit var mLlOptions: LinearLayout//选项

    @BindView(R.id.ll_othe)
    lateinit var mLlOthe: LinearLayout //其他


    private var mGenerals: MutableList<RoomGiftResult>? = null

    private var mFragmentIndex: Int = -1

    private var rows = 3 //行

    private var columns = 5 //列

    private var mRoomGift: RoomGiftResult? = null

    private val ViewPagerItems = ArrayList<View>()

    private var isOpen: Boolean = false

    private var mGold: Double = 0.0

    private var mChili: Double = 0.0

    private var mCallback: OnGiftCallback? = null

    companion object {
        fun newInstance(generals: MutableList<RoomGiftResult>?, fragmentIndex: Int): RoomGiftFragment {
            val roomGiftFragment = RoomGiftFragment()
            val args = Bundle()
            args.putSerializable(Constant.ROOM_GENERALS, generals as Serializable)
            args.putInt(Constant.FRAGMENT_INDEX, fragmentIndex)
            roomGiftFragment.arguments = args
            return roomGiftFragment
        }
    }

    override fun setLayoutId(): Int = R.layout.fragment_room_gift


    override fun createPresenter(): RoomGiftPresenter = RoomGiftPresenter()

    fun setOnGiftCallback(onGiftCallback: OnGiftCallback) {
        mCallback = onGiftCallback
    }


    override fun initView(savedInstanceState: Bundle?) {

        mPresenter.attachView(this)


        mGenerals = arguments?.getSerializable(Constant.ROOM_GENERALS) as MutableList<RoomGiftResult>


        mFragmentIndex = arguments?.getInt(Constant.FRAGMENT_INDEX, -1)!!

        for (roomGiftResult in mGenerals!!) {
            roomGiftResult.isSelect = false
        }

        initPage(mGenerals!!, 4, 2);

        if (mGenerals != null && mGenerals?.size!! > 0) {
            mGenerals?.get(0)?.isSelect = true
            mRoomGift = mGenerals?.get(0)
            setPrice(mRoomGift)

            for (viewPagerItem in ViewPagerItems) {

                ((viewPagerItem as GridView).adapter as RoomGiftAdapter).notifyDataSetChanged()
            }
        }

    }

    private fun initPage(faces: MutableList<RoomGiftResult>, columns: Int, rows: Int) {
        this.columns = columns
        this.rows = rows

        intiIndicator(faces)

        ViewPagerItems.clear()

        val pageCont: Int = getPagerCount(faces)

        for (i in 0 until pageCont) {

            ViewPagerItems.add(getViewPagerItem(i, faces)!!)
        }

        val mVpAdapter = RoomFaceVPAdapter(ViewPagerItems)

        mVpGift.adapter = mVpAdapter
        mVpGift.offscreenPageLimit = 0

        mVpGift.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            var oldPosition = 0
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                mIndex.playBy(oldPosition, position)
                oldPosition = position

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun intiIndicator(list: MutableList<RoomGiftResult>) {
        mIndex.init(getPagerCount(list))
    }

    private fun getViewPagerItem(position: Int, list: MutableList<RoomGiftResult>): View? {

        val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val layout = inflater.inflate(R.layout.layout_room_gift_grid, null) //表情布局

        val gridview = layout.findViewById<GridView>(R.id.chart_face_gv)

        val subList: MutableList<RoomGiftResult> = ArrayList()

        subList.addAll(list.subList(position!! * (columns * rows), if (columns * rows * (position + 1) > list.size) list.size else columns * rows * (position + 1)))

        val mGvAdapter = RoomGiftAdapter(subList, activity)

        gridview.adapter = mGvAdapter

        gridview.numColumns = columns


        // 单击表情执行的操作
        gridview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val roomGift = subList[position]

            setPrice(roomGift)

            mGenerals?.forEach {
                if (it.id == roomGift.id) {
                    it.isSelect = true
                    mRoomGift = roomGift
                } else {
                    it.isSelect = false
                }
            }


            for (viewPagerItem in ViewPagerItems) {

                ((viewPagerItem as GridView).adapter as RoomGiftAdapter).notifyDataSetChanged()
            }

        }
        return gridview
    }

    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     *
     * @return
     */
    private fun getPagerCount(gift: MutableList<RoomGiftResult>?): Int {
        val count = gift?.size
        return if (count!! % (columns * rows) == 0) count / (columns * rows) else count / (columns * rows) + 1
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getMyGoldNum()
        mPresenter.getMyChili()
    }


    @OnClick(R.id.tv_gift_num, R.id.ll_1, R.id.ll_10, R.id.ll_66, R.id.ll_99, R.id.ll_188, R.id.ll_520,
            R.id.ll_1314, R.id.tv_give_away, R.id.ll_othe,R.id.tv_recharge)
    fun onClick(v: View?) {

        when (v?.id) {

            R.id.tv_gift_num -> {

                if (isOpen) {
                    mLlOptions.visibility = View.GONE
                    mIvArrow.setImageResource(R.mipmap.icon_xiala)
                    isOpen = false

                } else {
                    mLlOptions.visibility = View.VISIBLE
                    mIvArrow.setImageResource(R.mipmap.icon_shangla)
                    isOpen = true
                }
            }

            R.id.ll_1 -> {
                mTvGiftNum.text = "1"
                mLlOptions.visibility = View.GONE
                isOpen = false
                mIvArrow.setImageResource(R.mipmap.icon_xiala)
            }
            R.id.ll_10 -> {
                mTvGiftNum.text = "10"
                mLlOptions.visibility = View.GONE
                isOpen = false
                mIvArrow.setImageResource(R.mipmap.icon_xiala)
            }
            R.id.ll_66 -> {
                mTvGiftNum.text = "66"
                mLlOptions.visibility = View.GONE
                isOpen = false
                mIvArrow.setImageResource(R.mipmap.icon_xiala)
            }
            R.id.ll_99 -> {
                mTvGiftNum.text = "99"
                mLlOptions.visibility = View.GONE
                isOpen = false
                mIvArrow.setImageResource(R.mipmap.icon_xiala)
            }
            R.id.ll_188 -> {
                mTvGiftNum.text = "188"
                mLlOptions.visibility = View.GONE
                isOpen = false
                mIvArrow.setImageResource(R.mipmap.icon_xiala)
            }
            R.id.ll_520 -> {
                mTvGiftNum.text = "520"
                mLlOptions.visibility = View.GONE
                isOpen = false
                mIvArrow.setImageResource(R.mipmap.icon_xiala)
            }
            R.id.ll_1314 -> {
                mTvGiftNum.text = "1314"
                mLlOptions.visibility = View.GONE
                isOpen = false
                mIvArrow.setImageResource(R.mipmap.icon_xiala)
            }

            R.id.ll_othe -> {
                val dialogView = DialogOhteNumView(context)
                TipsDialog.createDialog(context, dialogView)
                        .bindClick(R.id.tv_cancel) { v, dialog ->
                            dialog.dismiss()
                        }
                        .bindClick(R.id.tv_confirm) { v, dialog ->
                            var etNum: EditText = dialogView.getView(R.id.et_psw)
                            mTvGiftNum.text = etNum.text.toString().trim()
                            mLlOptions.visibility = View.GONE
                            isOpen = false
                            mIvArrow.setImageResource(R.mipmap.icon_xiala)
                            dialog.dismiss()
                        }
                        .show()

            }

            R.id.tv_give_away -> {

                if (OutUtils.isFastClick()) {

                    if (mCallback != null && mRoomGift != null) {
                        mCallback?.setGiftResult(mRoomGift, Integer.valueOf(mTvGiftNum.text.toString().trim()), mFragmentIndex)
                    }
                }

            }
            R.id.tv_recharge -> {

                if (mCallback != null){
                    mCallback?.rechargeEntry()
                }
            }
        }
    }

    override fun onGoldSuccess(gold: Double?) {
        mGold = gold!!
        setPrice(mRoomGift)
    }

    override fun onChiliSuccess(chili: Double?) {
        mChili = chili!!
        setPrice(mRoomGift)
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    private fun setPrice(roomGift: RoomGiftResult?) {
        if (roomGift != null) {
            if (roomGift.giftPriceType == 1) {
                mTvMoneyType.text = "辣椒"
                mTvMoney.text = setGoldOrChili(mChili)
            } else if (roomGift.giftPriceType == 2) {
                mTvMoneyType.text = "金币"
                mTvMoney.text = setGoldOrChili(mGold)
            }
        }
    }

    private fun setGoldOrChili(gold: Double): String {
        return NumberUtils.NumberToStr(gold.toString())
    }

    fun getGoldAndChili() {
        mPresenter.getMyGoldNum()
        mPresenter.getMyChili()
    }

}