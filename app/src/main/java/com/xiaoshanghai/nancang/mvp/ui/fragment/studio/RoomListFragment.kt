package com.xiaoshanghai.nancang.mvp.ui.fragment.studio

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpFragment
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.contract.RoomListConstart
import com.xiaoshanghai.nancang.mvp.presenter.RoomListPresenter
import com.xiaoshanghai.nancang.mvp.ui.adapter.RankListAdapter
import com.xiaoshanghai.nancang.mvp.ui.view.RankListHeader
import com.xiaoshanghai.nancang.net.bean.UserRankResult

class RoomListFragment : BaseMvpFragment<RoomListPresenter>(), RoomListConstart.View {

    @BindView(R.id.tv_gxb)
    lateinit var mTvGxb: TextView

    @BindView(R.id.tv_mlb)
    lateinit var mTvMlb: TextView

    @BindView(R.id.tv_rb)
    lateinit var mTvRb: TextView

    @BindView(R.id.tv_zb)
    lateinit var mTvZb: TextView

    @BindView(R.id.rcy_rank)
    lateinit var mRcyRank: RecyclerView

    private var mRoomId: String? = null

    private var mRankType = 0   //榜单类型 0贡献榜 1魅力榜

    private var mDataType = 0   //榜单日期类型 0日榜 1周榜

    private val mRankListAdapter: RankListAdapter by lazy { RankListAdapter() }

    private val mRankListHeader: RankListHeader by lazy { RankListHeader(context) }

    private var mRanList: MutableList<UserRankResult> = ArrayList()

    companion object {
        fun newInstance(roomId: String?): RoomListFragment {
            val roomListFragment = RoomListFragment()
            val args = Bundle()
            args.putString(Constant.ROOM_ID, roomId)
            roomListFragment.arguments = args
            return roomListFragment
        }
    }

    override fun setLayoutId(): Int = R.layout.fragment_room_list

    override fun createPresenter(): RoomListPresenter {
        return RoomListPresenter()
    }

    override fun initView(savedInstanceState: Bundle?) {

        mPresenter.attachView(this)
        mRoomId = arguments?.getString(Constant.ROOM_ID)

        mRankListAdapter.setHeaderView(mRankListHeader)

        mRcyRank.layoutManager = LinearLayoutManager(context)
        mRcyRank.adapter = mRankListAdapter

        mPresenter.getUserRank(mRoomId, selectType(mRankType, mDataType))

    }

    @OnClick(R.id.tv_gxb, R.id.tv_mlb, R.id.tv_rb, R.id.tv_zb)
    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_gxb -> {
                mRankType = 0
                mTvGxb.setBackgroundResource(R.drawable.shape_rounded_white_r13)
                mTvGxb.setTextColor(ContextCompat.getColor(context!!, R.color.color_b16dfe))
                mTvMlb.background = null
                mTvMlb.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mPresenter.getUserRank(mRoomId, selectType(mRankType, mDataType))
            }

            R.id.tv_mlb -> {
                mRankType = 1
                mTvMlb.setBackgroundResource(R.drawable.shape_rounded_white_r13)
                mTvMlb.setTextColor(ContextCompat.getColor(context!!, R.color.color_b16dfe))
                mTvGxb.background = null
                mTvGxb.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mPresenter.getUserRank(mRoomId, selectType(mRankType, mDataType))
            }

            R.id.tv_rb -> {
                mDataType = 0
                mTvRb.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvZb.setTextColor(ContextCompat.getColor(context!!, R.color.color_80ffffff))
                mPresenter.getUserRank(mRoomId, selectType(mRankType, mDataType))
            }

            R.id.tv_zb -> {
                mDataType = 1
                mTvZb.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                mTvRb.setTextColor(ContextCompat.getColor(context!!, R.color.color_80ffffff))
                mPresenter.getUserRank(mRoomId, selectType(mRankType, mDataType))

            }
        }
    }

    private fun selectType(mRankType: Int, mDataType: Int): Int {

        var selectType = 0

        if (mRankType == 0) { //贡献榜

            if (mDataType == 0) {   //贡献榜日榜
                selectType = 1
            } else if (mDataType == 1) {    //贡献榜周榜
                selectType = 2
            }

        } else if (mRankType == 1) {    //魅力榜

            if (mDataType == 0) {   //魅力榜日榜
                selectType = 3
            } else if (mDataType == 1) {    //魅力榜周榜
                selectType = 4
            }
        }

        return selectType

    }

    override fun userRankSuccess(userRankList: MutableList<UserRankResult>?) {

        mRankListHeader.setRank(userRankList)

//        mRanList = userRankList!!

        mRanList = getRankList(userRankList)

        mRankListAdapter.data = mRanList
        mRankListAdapter.notifyDataSetChanged()

    }


    override fun onError(msg: String?) {

    }

    /**
     * 获取三名之后数据
     */
    fun getRankList(ranks: MutableList<UserRankResult>?): MutableList<UserRankResult> {

        var rankList: MutableList<UserRankResult> = ArrayList()

        if (ranks == null || ranks.size <= 3) {
            return rankList
        } else {
            for (i in ranks.indices) {
                if (i > 2) {
                    rankList.add(ranks[i])
                }
            }
            return rankList
        }

    }
}