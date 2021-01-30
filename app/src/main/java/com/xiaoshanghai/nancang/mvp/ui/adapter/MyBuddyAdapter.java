package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.mvp.ui.view.CharmLevelView;
import com.xiaoshanghai.nancang.mvp.ui.view.UserLevelView;
import com.xiaoshanghai.nancang.net.bean.MyFollowResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class MyBuddyAdapter extends BaseQuickAdapter<MyFollowResult, MyBuddyAdapter.Holder> {

    private String mBuddyKey;

    public MyBuddyAdapter(String buddyKey) {
        super(R.layout.adapter_buddy_select);
        this.mBuddyKey = buddyKey;
        addChildClickViewIds(R.id.rl_give_away);
    }

    @Override
    protected void convert(@NotNull Holder holder, MyFollowResult result) {
        if (!TextUtils.isEmpty(mBuddyKey) && mBuddyKey.equals(Constant.TOPIC)) {
            holder.mTvButton.setText("分享");
        } else if (!TextUtils.isEmpty(mBuddyKey) && mBuddyKey.equals(Constant.CLAN)){
            holder.mTvButton.setText("分享");
        }else if (!TextUtils.isEmpty(mBuddyKey) && mBuddyKey.equals(Constant.PUBLIC_CHAT)){
            holder.mTvButton.setVisibility(View.GONE);
            holder.mRlGiveAway.setVisibility(View.GONE);
        }else if (!TextUtils.isEmpty(mBuddyKey) && mBuddyKey.equals(Constant.SHARE_ROOM)) {
            holder.mTvButton.setText("分享");
        }

        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);
        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);
        holder.mTvNickName.setText(result.getUserName());
        holder.mUlv.setUserLevel(result.getUserLevel());
        holder.mClv.setCharmLevel(result.getCharmLevel());
        holder.mTvUserIntroduce.setText(result.getUserIntroduce());
        int userSex = result.getUserSex();
        if (userSex == 0) {
            holder.mIvSex.setImageResource(R.mipmap.icon_gender_female);
        } else if (userSex == 1) {
            holder.mIvSex.setImageResource(R.mipmap.icon_gender_male);
        }

    }


    static class Holder extends BaseViewHolder {

        private CircleImageView mCivAvatar;
        private TextView mTvNickName;
        private UserLevelView mUlv;
        private CharmLevelView mClv;
        private TextView mTvUserIntroduce;
        private ImageView mIvSex;
        private TextView mTvButton;
        private RelativeLayout mRlGiveAway;

        public Holder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mUlv = view.findViewById(R.id.ulv);
            mClv = view.findViewById(R.id.clv);
            mTvUserIntroduce = view.findViewById(R.id.tv_user_introduce);
            mIvSex = view.findViewById(R.id.iv_sex);
            mTvButton = view.findViewById(R.id.tv_button);
            mRlGiveAway = view.findViewById(R.id.rl_give_away);
        }
    }
}
