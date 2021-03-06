package com.xiaoshanghai.nancang.mvp.ui.adapter;


import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.FriendFabulousCallback;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.utils.BirthdayToAgeUtil;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.xiaoshanghai.nancang.view.ExpandableTextView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.PhotoCallback;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FriendsCircleAdapter extends BaseQuickAdapter<FriendsCircleResult, FriendsCircleAdapter.FriendHolder> {

    private Context mContext;
    private final SparseBooleanArray mCollapsedStatus;
    private PhotoCallback mCallback;
    private FriendFabulousCallback mFablousCallback;

    public FriendsCircleAdapter(Context context, PhotoCallback callback) {
        super(R.layout.adapter_friend_circle);
        this.mContext = context;
        mCollapsedStatus = new SparseBooleanArray();
        this.mCallback = callback;
    }

    public void setFabulousCallback(FriendFabulousCallback fablousCallback) {
        this.mFablousCallback = fablousCallback;
    }


    @Override
    protected void convert(@NotNull FriendHolder holder, FriendsCircleResult result) {

        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), holder.mCivAvatar);

        holder.mTvNickName.setText(result.getUserName());

        showYear(result.getShowYear(), result.getUserSex(), holder.mIvSex, holder.mTvAge, result.getUserBirthday());

        holder.mTvDate.setText(result.getShowDate());

        int position = getItemPosition(result);
        holder.mExtView.setText(result.getTopicContent(), mCollapsedStatus, position);

        List<String> pictureList = result.getPictureList();
        ArrayList<ImageInfo> images = new ArrayList<>();
        for (String s : pictureList) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(s);
            info.setBigImageUrl(s);
            images.add(info);
        }

        NineGridViewClickAdapter nineGridViewClickAdapter = new NineGridViewClickAdapter(mContext, images);
        nineGridViewClickAdapter.setPhotoCallback(mCallback);
        holder.mNineGrid.setAdapter(nineGridViewClickAdapter);

        setFabulous(holder.mTvFabulous, result.getHasLike(), result.getLikeNumber());

        holder.mTvComment.setText(String.valueOf(result.getCommentNumber()));

        holder.mRlFabulous.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickFabulous(result, position);
            }
        });

        holder.mCivAvatar.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickAvater(result, position);
            }
        });


        holder.mTvComment.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickComment(result, position);
            }
        });

        holder.mTvShare.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickShare(result, position);
            }
        });

        holder.mIvMore.setOnClickListener(view -> {
            if (mFablousCallback != null) {
                mFablousCallback.onClickMore(result, position);
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

        CircleImageView mCivAvatar;     //??????
        TextView mTvNickName;           //??????
        ImageView mIvSex;               //??????
        TextView mTvAge;                //??????
        TextView mTvDate;               //?????????????????????
        ImageView mIvMore;               //????????????
        ExpandableTextView mExtView;     //??????
        NineGridView mNineGrid;           //?????????
        RelativeLayout mRlFabulous;           //?????????????????????
        TextView mTvFabulous;           //?????????
        TextView mTvComment;           //?????????
        TextView mTvShare;           //????????????

        public FriendHolder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvNickName = view.findViewById(R.id.tv_nick_name);
            mIvSex = view.findViewById(R.id.iv_sex);
            mTvAge = view.findViewById(R.id.tv_age);
            mTvDate = view.findViewById(R.id.tv_date);
            mIvMore = view.findViewById(R.id.iv_more);
            mExtView = view.findViewById(R.id.expand_text_view);
            mNineGrid = view.findViewById(R.id.nineGrid);
            mRlFabulous = view.findViewById(R.id.rl_fabulous);
            mTvFabulous = view.findViewById(R.id.tv_fabulous);
            mTvComment = view.findViewById(R.id.tv_comment);
            mTvShare = view.findViewById(R.id.tv_share);
        }
    }
}
