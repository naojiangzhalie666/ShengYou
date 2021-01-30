package com.xiaoshanghai.nancang.mvp.ui.fragment.studio

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpFragment
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.HalfRankConstract
import com.xiaoshanghai.nancang.mvp.presenter.HalfRankPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.RoomRankAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.RoomRankHeader
import com.xiaoshanghai.nancang.net.bean.HalfRoomRankResult
import com.xiaoshanghai.nancang.net.bean.RoomRankResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.ToastUtils

class HalfRankFragment : BaseMvpFragment<HalfRankPresenter>(), HalfRankConstract.View {

    @BindView(R.id.tv_mine_rank)
    lateinit var mTvMineRank: TextView  //自己的当前排名

    @BindView(R.id.tv_mine_avatar)
    lateinit var mTvMineAvatar: ImageView //自己的头像

    @BindView(R.id.tv_name)
    lateinit var mTvName: TextView //自己的名字

    @BindView(R.id.tv_id)
    lateinit var mTvId: TextView //自己的ID

    @BindView(R.id.tv_up_one)
    lateinit var mTvUpOne: TextView //自己距离上一名还差多少钱

    @BindView(R.id.tv_up)
    lateinit var mTvUp: TextView //距离上一名文字

    @BindView(R.id.rcy_rank)
    lateinit var mRcyRank: RecyclerView//rank列表

    private val mRoomRankHeader: RoomRankHeader by lazy { RoomRankHeader(context) }


    private var mRoomId: String? = null

    private var mSelfRank: RoomRankResult? = null

    private var mRankings: MutableList<RoomRankResult>? = null

    private var mRankList: MutableList<RoomRankResult> = ArrayList()

    private val mRoomRankAdapter: RoomRankAdapter by lazy { RoomRankAdapter() }

    companion object {
        fun newInstance(roomId: String?): HalfRankFragment {
            val roomGiftView = HalfRankFragment()
            val args = Bundle()
            args.putString(Constant.ROOM_ID, roomId)
            roomGiftView.arguments = args
            return roomGiftView
        }
    }


    override fun setLayoutId(): Int = R.layout.fragment_half_rank


    override fun createPresenter(): HalfRankPresenter {
        return HalfRankPresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {

        mPresenter.attachView(this)

        mRoomId = arguments?.getString(Constant.ROOM_ID)
        mRoomRankAdapter.setHeaderView(mRoomRankHeader)

        mRoomRankAdapter.data = mRankList

        mRcyRank.layoutManager = LinearLayoutManager(context)

        mRcyRank.adapter = mRoomRankAdapter

    }

    override fun onResume() {
        super.onResume()
        mPresenter.getRoomRank(mRoomId)
    }

    /**
     * 请求排行榜成功
     */
    override fun rankSuccess(rank: HalfRoomRankResult?) {
        if (rank != null) {

            mSelfRank = rank.thisRanking

            mRankings = rank.rankings

            setResult(mSelfRank, mRankings)
        } else {
            ToastUtils.showShort("请求出错")
        }

    }

    /**
     * 请求排行榜失败
     */
    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    private fun setResult(self: RoomRankResult?, ranks: MutableList<RoomRankResult>?) {
        setSelfRoom(self)
        mRoomRankHeader.setRank(ranks)

        mRankList = getRankList(ranks)
        mRoomRankAdapter.data = mRankList
        mRoomRankAdapter.notifyDataSetChanged()


    }

    /**
     * 设置自己房间信息
     */
    private fun setSelfRoom(selfRank: RoomRankResult?) {

        if (selfRank == null) return

        if (selfRank.rankNo == null) {


            mTvMineRank.let {
                it.textSize = 12f
                it.text = "未上榜"
            }

            //不在榜并且榜单无人
            if (mRankings == null || mRankings?.size == 0) {

                mTvUpOne.text = "0"
                mTvUp.visibility = View.INVISIBLE

            } else {    //不在榜但是榜单有人

                val ranking = mRankings?.get(mRankings?.size!! - 1)

                val listCount = ranking?.countPrice

                val selfCount = selfRank.countPrice

                if (listCount!! > selfCount) {
                    mTvUpOne.text = (listCount - selfCount).toString()
                    mTvUp.visibility = View.VISIBLE
                } else {
                    mTvUpOne.text = "0"
                    mTvUp.visibility = View.INVISIBLE
                }

            }


        } else {

            mTvMineRank.let {
                it.textSize = 20f
                it.text = selfRank.rankNo.toString()
            }

            if (selfRank.rankNo == 1) {
                mTvUpOne.text = "No.1"
                mTvUp.visibility = View.INVISIBLE
            } else {

                var index = -1

                for (i in mRankings!!.indices) {
                    if (mRankings!![i].roomId == selfRank.roomId) {
                        index = i
                        break
                    }
                }

                if (index > 0) {
                    val ranking = mRankings?.get(index - 1)
                    val listCount = ranking?.countPrice
                    val selfCount = selfRank.countPrice

                    if (listCount!! > selfCount) {
                        mTvUpOne.text = (listCount - selfCount).toString()
                        mTvUp.visibility = View.VISIBLE
                    } else {
                        mTvUpOne.text = "0"
                        mTvUp.visibility = View.INVISIBLE
                    }
                }

            }


        }

        if (!TextUtils.isEmpty(selfRank.roomPicture)) {

            GlideAppUtil.loadImage(context, selfRank.roomPicture, mTvMineAvatar)
        }

        mTvName.text = if (TextUtils.isEmpty(selfRank.roomName)) "" else selfRank.roomName

        mTvId.text = if (TextUtils.isEmpty(selfRank.roomNumber)) "" else "ID:${selfRank.roomNumber}"


    }


    /**
     * 获取三名之后数据
     */
    fun getRankList(ranks: MutableList<RoomRankResult>?): MutableList<RoomRankResult> {

        var rankList: MutableList<RoomRankResult> = ArrayList()

        if (ranks?.size!! <= 3) {
            return rankList
        } else {
            for (i in ranks!!.indices) {
                if (i > 2) {
                    val newRank = ranks[i]
                    val upRank = ranks[i - 1]
                    val upPrice = upRank.countPrice - newRank.countPrice
                    if (upPrice > 0) {
                        newRank.upPrice = upPrice
                    } else {
                        newRank.upPrice = 0
                    }
                    rankList.add(newRank)
                }
            }

            return rankList
        }

    }

}