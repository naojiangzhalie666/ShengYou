package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
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

public class FansSelectAdapter extends BaseQuickAdapter<MyFollowResult, FansSelectAdapter.Holder> {


    public FansSelectAdapter() {
        super(R.layout.adapter_fans_select);
        addChildClickViewIds(R.id.rl_follow);
    }

    @Override
    protected void convert(@NotNull Holder holder, MyFollowResult result) {
        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);
        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);
        holder.mTvNickName.setText(result.getUserName());
        holder.mUlv.setUserLevel(result.getUserLevel());
        holder.mClv.setCharmLevel(result.getCharmLevel());
        holder.mTvUserIntroduce.setText(result.getUserIntroduce());

//        if (result.getFollowEach().equals("1")) {
//            holder.mTvFind.setText("已关注");
//            holder.mIvFollow.setImageResource(R.mipmap.icon_face);
//            holder.mTvFind.setTextColor(getContext().getResources().getColor(R.color.color_777777));
//            holder.mRlFolow.setBackgroundResource(R.drawable.shape_unattention_bg);
//        } else {
//            holder.mTvFind.setText("关注");
//            holder.mIvFollow.setImageResource(R.mipmap.icon_friend_add);
//            holder.mTvFind.setTextColor(getContext().getResources().getColor(R.color.white));
//            holder.mRlFolow.setBackgroundResource(R.drawable.shape_fans_bg);
//        }
    }


    static class Holder extends BaseViewHolder {

        private CircleImageView mCivAvatar;
        private TextView mTvNickName;
        private UserLevelView mUlv;
        private CharmLevelView mClv;
        private TextView mTvUserIntroduce;
//        private RelativeLayout mRlFolow;
        private ImageView mIvFollow;
//        private TextView mTvFind;

        public Holder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mUlv = view.findViewById(R.id.ulv);
            mClv = view.findViewById(R.id.clv);
            mTvUserIntroduce = view.findViewById(R.id.tv_user_introduce);
//            mRlFolow = view.findViewById(R.id.rl_follow);
//            mIvFollow = view.findViewById(R.id.iv_follow);
//            mTvFind = view.findViewById(R.id.tv_find);
        }
    }

}
