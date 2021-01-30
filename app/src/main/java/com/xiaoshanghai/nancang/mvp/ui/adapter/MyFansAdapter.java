package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.mvp.ui.view.CharmLevelView;
import com.xiaoshanghai.nancang.mvp.ui.view.UserLevelView;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class MyFansAdapter extends BaseQuickAdapter<MyFollowResult, MyFansAdapter.Holder> {


    public MyFansAdapter() {
        super(R.layout.adapter_my_fans);
        addChildClickViewIds(R.id.tv_find);
    }

    @Override
    protected void convert(@NotNull Holder holder, MyFollowResult result) {
        GlideAppUtil.loadImage(getContext(),result.getUserPictureUrl(),holder.mCivAvatar);
        GlideAppUtil.loadImage(getContext(),result.getUserPictureUrl(),holder.mCivAvatar);
        holder.mTvNickName.setText(result.getUserName());
        holder.mUlv.setUserLevel(result.getUserLevel());
        holder.mClv.setCharmLevel(result.getCharmLevel());
        holder.mTvUserIntroduce.setText(result.getUserIntroduce());

        if (result.getFollowEach().equals("1")) {
            holder.mTvFind.setText("相互关注");
            holder.mTvFind.setBackgroundResource(R.drawable.shape_unattention_bg);
            holder.mTvFind.setTextColor(getContext().getResources().getColor(R.color.color_777777));
        } else {
            holder.mTvFind.setText("关注");
            holder.mTvFind.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
            holder.mTvFind.setBackgroundResource(R.drawable.shape_attention_bg);
        }
    }


    static class Holder extends BaseViewHolder {

        private CircleImageView mCivAvatar;
        private TextView mTvNickName;
        private UserLevelView mUlv;
        private CharmLevelView mClv;
        private TextView mTvUserIntroduce;
        private TextView mTvFind;

        public Holder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mUlv = view.findViewById(R.id.ulv);
            mClv = view.findViewById(R.id.clv);
            mTvUserIntroduce = view.findViewById(R.id.tv_user_introduce);
            mTvFind = view.findViewById(R.id.tv_find);
        }
    }

}
