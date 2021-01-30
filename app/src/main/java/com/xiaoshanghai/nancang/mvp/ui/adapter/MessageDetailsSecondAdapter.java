package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.CommentResult;
import com.xiaoshanghai.nancang.mvp.ui.adapter.MessageDetailsSecondAdapter.Holder;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MessageDetailsSecondAdapter extends BaseQuickAdapter<CommentResult, Holder> {


    public MessageDetailsSecondAdapter(@Nullable List<CommentResult> data) {
        super(R.layout.adapter_second, data);
    }

    @Override
    protected void convert(@NotNull Holder holder, CommentResult result) {

        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);
        holder.mTvNickName.setText(result.getUserName());
        holder.mTvDate.setText(result.getShowDate());
        holder.mTvComment.setText(result.getCommentContent());

    }

    static class Holder extends BaseViewHolder {

        private CircleImageView mCivAvatar;     //评论者头像
        private TextView mTvNickName;           //评论者昵称
        private TextView mTvDate;               //评论日期
        private TextView mTvComment;            //评论

        public Holder(@NotNull View view) {
            super(view);

            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mTvDate = view.findViewById(R.id.tv_date);
            mTvComment = view.findViewById(R.id.tv_comment);

        }
    }

}
