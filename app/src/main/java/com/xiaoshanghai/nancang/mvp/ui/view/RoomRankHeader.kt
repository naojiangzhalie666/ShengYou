package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.RoomRankResult
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.NumberUtils
import com.xiaoshanghai.nancang.view.CircleImageView

class RoomRankHeader : ConstraintLayout {

    @BindView(R.id.iv_top_1_avatar)
    lateinit var mIvTopOneAvatar: CircleImageView  //第一名头像

    @BindView(R.id.tv_top1_name)
    lateinit var mTvOneName: TextView //第一名名字

    @BindView(R.id.group_1)
    lateinit var mGroupOne: Group //第一名需要隐藏的组

    @BindView(R.id.tv_top1_id)
    lateinit var mTvTopOneId: TextView     //第二名id

    @BindView(R.id.tv_juli1)
    lateinit var mTvOne: TextView //第一名标记

    @BindView(R.id.iv_top_2_avatar)
    lateinit var mIvTopTwoAvatar: CircleImageView  //第二名头像

    @BindView(R.id.tv_top2_name)
    lateinit var mTvTopTwoName: TextView //第二名名字

    @BindView(R.id.tv_top2_id)
    lateinit var mTvTopTwoId: TextView     //第二名id

    @BindView(R.id.tv_juli2_gold)
    lateinit var mTvTwoGold: TextView //第二名距离上一名差多少G

    @BindView(R.id.group_2)
    lateinit var mGroupTwo: Group    //第二名需要隐藏的组

    @BindView(R.id.iv_top_3_avatar)
    lateinit var mIvTopThreeAvatar: CircleImageView  //第三名头像

    @BindView(R.id.tv_top3_name)
    lateinit var mTvTopThreeName: TextView //第三名名字

    @BindView(R.id.tv_top3_id)
    lateinit var mTvTopThreeId: TextView     //第三名id

    @BindView(R.id.tv_juli3_gold)
    lateinit var mTvThreeGold: TextView //第三名距离上一名差多少G

    @BindView(R.id.group_3)
    lateinit var mGroupThree: Group    //第三名需要隐藏的组

    private var mContext: Context? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context?) {
        mContext = context
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_room_rank_header, this, true)
        ButterKnife.bind(view)
    }


    /**
     * 设置房间前三名排名
     */
    fun setRank(ranks: MutableList<RoomRankResult>?) {

        //排位列表为空或者是没有人时的处理
        if (ranks == null || ranks.size == 0) {
            mGroupOne.visibility = View.GONE
            mGroupTwo.visibility = View.GONE
            mGroupThree.visibility = View.GONE

            mIvTopOneAvatar.setImageResource(R.mipmap.icon_rank_empty_1)
            mIvTopTwoAvatar.setImageResource(R.mipmap.icon_rank_empty_2)
            mIvTopThreeAvatar.setImageResource(R.mipmap.icon_rank_empty_3)

            return
        }

        //只有一个人时的处理
        if (ranks.size == 1) {
            val oneRank = ranks[0]

            mTvOneName.text = oneRank.roomName

            mTvTopOneId.text = oneRank.roomNumber

            GlideAppUtil.loadImage(context!!, oneRank.roomPicture, mIvTopOneAvatar)

            mGroupOne.visibility = View.VISIBLE

            mGroupTwo.visibility = View.GONE
            mGroupThree.visibility = View.GONE

            mIvTopTwoAvatar.setImageResource(R.mipmap.icon_rank_empty_2)
            mIvTopThreeAvatar.setImageResource(R.mipmap.icon_rank_empty_3)

        } else if (ranks.size == 2) {
            val oneRank = ranks[0]
            val twoRank = ranks[1]

            mTvOneName.text = oneRank.roomName

            mTvTopOneId.text = oneRank.roomNumber

            GlideAppUtil.loadImage(context!!, oneRank.roomPicture, mIvTopOneAvatar)

            mGroupOne.visibility = View.VISIBLE


            mTvTopTwoName.text = twoRank.roomName

            mTvTopTwoId.text = twoRank.roomNumber

            val oneCount = oneRank.countPrice
            val twoCount = twoRank.countPrice

            val twoGlod = oneCount - twoCount

            if (twoGlod > 0) {

                mTvTwoGold.text = NumberUtils.strToNumber(twoGlod.toString())
            }

            GlideAppUtil.loadImage(context!!, twoRank.roomPicture, mIvTopTwoAvatar)

            mGroupTwo.visibility = View.VISIBLE


            mGroupThree.visibility = View.GONE

            mIvTopThreeAvatar.setImageResource(R.mipmap.icon_rank_empty_3)

        } else if (ranks.size >= 3) {

            val oneRank = ranks[0]
            val twoRank = ranks[1]
            val threeRank = ranks[2]

            mTvOneName.text = oneRank.roomName

            mTvTopOneId.text = oneRank.roomNumber

            GlideAppUtil.loadImage(context!!, oneRank.roomPicture, mIvTopOneAvatar)

            mGroupOne.visibility = View.VISIBLE


            mTvTopTwoName.text = twoRank.roomName

            mTvTopTwoId.text = twoRank.roomNumber

            val oneCount = oneRank.countPrice
            val twoCount = twoRank.countPrice

            val twoGlod = oneCount - twoCount

            if (twoGlod > 0) {
                mTvTwoGold.text = NumberUtils.strToNumber(twoGlod.toString())
            }

            GlideAppUtil.loadImage(context!!, twoRank.roomPicture, mIvTopTwoAvatar)

            mGroupTwo.visibility = View.VISIBLE


            mTvTopThreeName.text = threeRank.roomName

            mTvTopThreeId.text = threeRank.roomNumber

            val threeCount = threeRank.countPrice

            val threeGold = twoCount - threeCount

            if (threeGold > 0) {
                mTvThreeGold.text = NumberUtils.strToNumber(threeGold.toString())
            }

            GlideAppUtil.loadImage(context!!, threeRank.roomPicture, mIvTopThreeAvatar)

            mGroupThree.visibility = View.VISIBLE

        }

    }


}