package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.OnItemClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.net.bean.FriendsCircleResult;
import com.xiaoshanghai.nancang.utils.BirthdayToAgeUtil;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;
import com.xiaoshanghai.nancang.view.ExpandableTextView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.PhotoCallback;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;

public class MessageDetailsHeaderView extends RelativeLayout implements PhotoCallback {

    CircleImageView mCivAvatar;     //头像
    TextView mTvNickName;           //昵称
    ImageView mIvSex;               //性别
    TextView mTvAge;                //年龄
    TextView mTvDate;               //朋友圈发布时间
    ImageView mIvMore;               //更多按钮
    ExpandableTextView mExtView;     //内容
    NineGridView mNineGrid;           //九宫格
    TextView mTvCommentNum;           //评论数
    private FriendsCircleResult mResult;

    private SparseBooleanArray mCollapsedStatus;

    private OnItemClickCallback mCallback;

    public MessageDetailsHeaderView(@NonNull Context context) {
        super(context);
        init();
    }

    public MessageDetailsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageDetailsHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCallback(OnItemClickCallback callback) {
        this.mCallback = callback;
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_message_details_header, this, true);
        initView(view);
    }

    private void initView(View view) {

        mCivAvatar = view.findViewById(R.id.civ_avatar);
        mTvNickName = view.findViewById(R.id.tv_nick_name);
        mIvSex = view.findViewById(R.id.iv_sex);
        mTvAge = view.findViewById(R.id.tv_age);
        mTvDate = view.findViewById(R.id.tv_date);
        mIvMore = view.findViewById(R.id.iv_more);
        mExtView = view.findViewById(R.id.expand_text_view);
        mNineGrid = view.findViewById(R.id.nineGrid);
        mTvCommentNum = view.findViewById(R.id.tv_comment_num);
        mCollapsedStatus = new SparseBooleanArray();

        mIvMore.setOnClickListener(view1 -> {
            if (mCallback != null) {
                mCallback.onClickItem(mResult);
            }
        });
    }

    public void upData(FriendsCircleResult result) {
        mResult = result;

        GlideAppUtil.loadImage(getContext(), result.getUserPictureUrl(), mCivAvatar);

        mTvNickName.setText(result.getUserName());

        showYear(result.getShowYear(), result.getUserSex(), mIvSex, mTvAge, result.getUserBirthday());

        mTvDate.setText(result.getShowDate());

        mExtView.setText(result.getTopicContent(), mCollapsedStatus, 0);

        List<String> pictureList = result.getPictureList();
        ArrayList<ImageInfo> images = new ArrayList<>();
        for (String s : pictureList) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(s);
            info.setBigImageUrl(s);
            images.add(info);
        }

        NineGridViewClickAdapter nineGridViewClickAdapter = new NineGridViewClickAdapter(getContext(), images);
        nineGridViewClickAdapter.setPhotoCallback(this);
        mNineGrid.setAdapter(nineGridViewClickAdapter);


    }

    /**
     * @param isShowYear   是否显示年龄 1 显示  0 显示
     * @param userSex      性别 "1" 男  "0" 女
     * @param sex          性别图标控件
     * @param age          年龄文字控件
     * @param userBirthday 生日
     */
    private void showYear(int isShowYear, String userSex, ImageView sex, TextView age, String userBirthday) {
        if (1 == isShowYear) {      //显示发布者年龄

            if ("0".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_sex_female);
            } else if ("1".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_sex_male);
            }

            age.setText(BirthdayToAgeUtil.BirthdayToAge(userBirthday));


        } else if (0 == isShowYear) {        //不显示发布在年龄
            if ("0".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_gender_female);
            } else if ("1".equals(userSex)) {
                sex.setImageResource(R.mipmap.icon_gender_male);
            }
            age.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemPhotoClick(int index, List<ImageInfo> imageInfo) {
        ArrayList<String> imgs = new ArrayList<>();

        for (ImageInfo info : imageInfo) {
            imgs.add(info.getBigImageUrl());
        }
        photoPreviewWrapper(index, imgs);
    }

    private void photoPreviewWrapper(int index, ArrayList<String> imageInfo) {

        BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(getContext())
                .saveImgDir(new File(Constant.FILE_PATH)); // 保存图片的目录，如果传 null，则没有保存图片功能

        if (imageInfo.size() == 1) {
            // 预览单张图片
            photoPreviewIntentBuilder.previewPhoto(imageInfo.get(index));
        } else if (imageInfo.size() > 1) {
            // 预览多张图片
            photoPreviewIntentBuilder.previewPhotos(imageInfo)
                    .currentPosition(index); // 当前预览图片的索引
        }
        getContext().startActivity(photoPreviewIntentBuilder.build());
    }

    public void setCommentNum(String num) {
        mTvCommentNum.setText(num);
    }
}
