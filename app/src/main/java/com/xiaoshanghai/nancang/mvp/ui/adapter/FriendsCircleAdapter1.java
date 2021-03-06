package com.xiaoshanghai.nancang.mvp.ui.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lzy.ninegrid.PhotoCallback;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.FriendFabulousCallback;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.utils.BirthdayToAgeUtil;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.xiaoshanghai.nancang.view.ExpandableTextView;

import org.jetbrains.annotations.NotNull;


public class FriendsCircleAdapter1 extends BaseQuickAdapter<FriendsCircleResult, FriendsCircleAdapter1.FriendHolder> {

    private Context mContext;
    private final SparseBooleanArray mCollapsedStatus;
    private PhotoCallback mCallback;
    private FriendFabulousCallback mFablousCallback;

    public FriendsCircleAdapter1(Context context, PhotoCallback callback) {
        super(R.layout.adapter_friend_circle1);
        this.mContext = context;
        mCollapsedStatus = new SparseBooleanArray();
        this.mCallback = callback;
    }

    public void setFabulousCallback(FriendFabulousCallback fablousCallback) {
        this.mFablousCallback = fablousCallback;
    }

    @Override
    protected void convert(@NotNull FriendHolder holder, FriendsCircleResult result) {
        if(result.getPictureList().size()>0) {
            GlideAppUtil.loadImage(getContext(), result.getPictureList().get(0), holder.mNineGrid);
        }
        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);
        holder.mTvNickName.setText(result.getUserName());
        showYear(result.getShowYear(), result.getUserSex(), holder.mIvSex, holder.mTvAge, result.getUserBirthday());
        if(TextUtils.isEmpty(result.getCity())){
            holder.mTvDate.setText(result.getShowDate());
        }else {
            holder.mTvDate.setText(result.getShowDate()+"??"+result.getCity());
        }
        int position = getItemPosition(result);
        if(TextUtils.isEmpty(result.getTopicContent())) {
            holder.mExtView.setText("");
        }else {
            holder.mExtView.setText(result.getTopicContent());
        }
        holder.mCivAvatar.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickAvater(result, position);
            }
        });
        holder.mCivAvatar.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickAvater(result, position);
            }
        });
        holder.rl_contact.setOnClickListener(v -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickChat(result, position);
            }
        });
        holder.ivVideo.setOnClickListener(v -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickVideo(result, position);
            }
        });
    }
    /**
     * ????????????
     *
     * @param fabulous   ????????????
     * @param hasLike    ???????????? 1 ??? 0 ???
     * @param likeNumber ?????????
     */
    public void setFabulous(TextView fabulous, int hasLike, int likeNumber) {
        fabulous.setEnabled(hasLike == 0 ? false : true);
        fabulous.setText(String.valueOf(likeNumber));
    }

    /**
     * @param isShowYear   ?????????????????? 1 ??????  0 ??????
     * @param userSex      ?????? "1" ???  "0" ???
     * @param sex          ??????????????????
     * @param age          ??????????????????
     * @param userBirthday ??????
     */
    private void showYear(int isShowYear, String userSex, ImageView sex, TextView age, String userBirthday) {
        if (1 == isShowYear) {      //?????????????????????

            if ("0".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_sex_female);
            } else if ("1".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_sex_male);
            }

            age.setText(BirthdayToAgeUtil.BirthdayToAge(userBirthday));
        } else if (0 == isShowYear) {        //????????????????????????
            if ("0".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_gender_female);
            } else if ("1".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_gender_male);
            }
            age.setVisibility(View.GONE);
        }
    }


    static class FriendHolder extends BaseViewHolder {
        ImageView mNineGrid;           //?????????
        CircleImageView mCivAvatar;     //??????
        TextView mTvNickName;           //??????
        ImageView mIvSex;               //??????
        TextView mTvAge;                //??????
        TextView mTvDate;               //?????????????????????
        TextView mExtView;     //??????
        TextView rl_contact;
        CircleImageView ivVideo;
        public FriendHolder(@NotNull View view) {
            super(view);
            mNineGrid = view.findViewById(R.id.nineGrid);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mIvSex = view.findViewById(R.id.iv_sex);
            mTvAge = view.findViewById(R.id.tv_age);
            mTvDate = view.findViewById(R.id.tv_date);
            mExtView = view.findViewById(R.id.expand_text_view);
            rl_contact=view.findViewById(R.id.rl_contact);
            ivVideo=view.findViewById(R.id.ivVideo);
        }
    }
}
