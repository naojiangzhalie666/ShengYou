package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.OnSecondCallback;
import com.xiaoshanghai.nancang.net.bean.CommentResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class MessageDetailsAdapter extends BaseQuickAdapter<CommentResult, MessageDetailsAdapter.Holder> {

    private OnSecondCallback onSecondClick;

    public MessageDetailsAdapter(OnSecondCallback onSecondClick) {
        super(R.layout.adapter_message_details);
        this.onSecondClick = onSecondClick;
    }

    @Override
    protected void convert(@NotNull Holder holder, CommentResult result) {
        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);
        holder.mTvNickName.setText(result.getUserName());
        holder.mTvDate.setText(result.getShowDate());
        holder.mTvComment.setText(result.getCommentContent());

        MessageDetailsSecondAdapter adapter = new MessageDetailsSecondAdapter(result.getSonComments());

        holder.mRcComment.setLayoutManager(new LinearLayoutManager(getContext()));
        holder.mRcComment.setAdapter(adapter);

        int itemPosition = getItemPosition(result);

        holder.mLlOneComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSecondClick!=null) {
                    onSecondClick.onSecondClick(result,itemPosition);
                }

            }
        });


    }

    public static class Holder extends BaseViewHolder {
        private CircleImageView mCivAvatar;     //评论者头像
        private TextView mTvNickName;           //评论者昵称
        private TextView mTvDate;               //评论日期
        private TextView mTvComment;            //评论
        private RecyclerView mRcComment;        //评论回复列表
        private LinearLayout mLlOneComment;        //评论回复列表

        public Holder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mTvDate = view.findViewById(R.id.tv_date);
            mTvComment = view.findViewById(R.id.tv_comment);
            mRcComment = view.findViewById(R.id.rc_comment);
            mLlOneComment = view.findViewById(R.id.ll_one_comment);

        }
    }
}
