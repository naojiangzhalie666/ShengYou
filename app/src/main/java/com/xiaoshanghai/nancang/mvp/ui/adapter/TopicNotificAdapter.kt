package com.xiaoshanghai.nancang.mvp.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.net.bean.TopicMsg
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.view.CircleImageView

class TopicNotificAdapter : BaseQuickAdapter<TopicMsg, TopicNotificAdapter.Holder>(R.layout.adapter_item_topic_notific) {


    override fun convert(holder: Holder, item: TopicMsg) {

        GlideAppUtil.loadImage(context, item.userPicture, holder.mUserPicture, R.mipmap.icon_default_avatar)
        holder.mTvUserName?.text = item.userName

        if (item.userSex == 0) {
            holder.mSexView?.setImageResource(R.mipmap.icon_gender_female)
        } else if (item.userSex == 1) {
            holder.mSexView?.setImageResource(R.mipmap.icon_gender_male)
        }

        if (item.commentType == 0) {        //评论
            holder.mTvTopic?.text = "评论了你的动态"
        } else if (item.commentType == 1) {//点赞
            holder.mTvTopic?.text = "赞了你的动态"
        }

        holder.mTime?.text = item.createTime

        if (!TextUtils.isEmpty(item.topicpicture)&&!TextUtils.isEmpty(item.topicContent)) {
            GlideAppUtil.loadImage(context,item.topicpicture,holder.mTopicPicture)
            holder.mTopicPicture?.visibility = View.VISIBLE
            holder.mTvTopicContent?.visibility = View.GONE
        }  else if (!TextUtils.isEmpty(item.topicpicture)){
            GlideAppUtil.loadImage(context,item.topicpicture,holder.mTopicPicture)
            holder.mTopicPicture?.visibility = View.VISIBLE
            holder.mTvTopicContent?.visibility = View.GONE
        }else if (!TextUtils.isEmpty(item.topicContent)){
            holder.mTopicPicture?.visibility = View.GONE
            holder.mTvTopicContent?.visibility = View.VISIBLE
            holder.mTvTopicContent?.text = item.topicContent
        } else {
            holder.mTopicPicture?.visibility = View.GONE
            holder.mTvTopicContent?.visibility = View.GONE
        }

        if (item.isRead == 0) {
            holder.mTvFlag?.visibility = View.VISIBLE
        } else if (item.isRead == 1) {
            holder.mTvFlag?.visibility = View.GONE
        }


    }


    inner class Holder : BaseViewHolder {

        var mUserPicture: CircleImageView? = null
        var mTvUserName: TextView? = null
        var mSexView: ImageView? = null
        var mTvTopic: TextView? = null
        var mTime: TextView? = null
        var mTopicPicture: CircleImageView? = null
        var mTvTopicContent: TextView? = null
        var mTvFlag: TextView? = null

        constructor(view: View) : super(view) {
            mUserPicture = view.findViewById(R.id.iv_user_picture)
            mTvUserName = view.findViewById(R.id.tv_user_name)
            mSexView = view.findViewById(R.id.sex_view)
            mTvTopic = view.findViewById(R.id.tv_topic)
            mTime = view.findViewById(R.id.tv_time)
            mTopicPicture = view.findViewById(R.id.iv_topic_picture)
            mTvTopicContent = view.findViewById(R.id.tv_topic_content)
            mTvFlag = view.findViewById(R.id.tv_read_flag)
        }
    }
}