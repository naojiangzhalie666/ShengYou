package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;


public class MikeSeatView extends RelativeLayout {


    private AvatarView mAvatar;

    private ImageView mIvOff;

    private LinearLayout mLlGiftValue;

    private TextView mTvGiftValueNum;

    private TextView mTvSeatNum;

    private TextView mTvName;

    private View mView;


//    private Context mContext;

    private VoiceRoomSeatEntity mTheme;


    public MikeSeatView(Context context) {
        super(context);
        init(context);
    }

    public MikeSeatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MikeSeatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mView = LayoutInflater.from(context).inflate(R.layout.view_mike_seat, this, true);


        initView();

    }

    private void initView() {

        mAvatar = mView.findViewById(R.id.iv_Head);

        mIvOff = mView.findViewById(R.id.iv_off);

        mLlGiftValue = mView.findViewById(R.id.ll_gift_value);

        mTvGiftValueNum = mView.findViewById(R.id.tv_gift_value_num);

        mTvSeatNum = mView.findViewById(R.id.tv_seat_num);

        mTvName = mView.findViewById(R.id.tv_name);


    }


    public void setSeatResult(VoiceRoomSeatEntity theme, boolean giftValue, int position) {
        mTheme = theme;

//        if (giftValue) {
//            mLlGiftValue.setVisibility(View.VISIBLE);
//        } else {
//            mLlGiftValue.setVisibility(View.INVISIBLE);
//        }



        //麦位没被锁并无人使用
        if (!mTheme.isUsed) {

            //麦位被锁
            if (mTheme.isClose) {

                mAvatar.setDefAvatarAndHeadear(R.mipmap.icon_live_default_avatar, 0);
                mTvName.setText("号麦位");
                mTvSeatNum.setText((position + 1) + "");
                mTvGiftValueNum.setText("0");
                mTvSeatNum.setBackgroundResource(R.drawable.shape_mike_seak_num);
            } else {
                if (position == 7) {
                    mAvatar.setDefAvatarAndHeadear(R.drawable.icon_empty_seat, 0);
                } else {
                    mAvatar.setDefAvatarAndHeadear(R.mipmap.icon_live_tme, 0);
                }
                mTvName.setText("号麦位");
                mTvSeatNum.setText((position + 1) + "");
                mTvGiftValueNum.setText("0");
                mTvSeatNum.setBackgroundResource(R.drawable.shape_mike_seak_num);
            }

        } else {


            //麦位没被锁但是有人使用了
            mAvatar.setAvatarAndHeadear(mTheme.userAvatar, !TextUtils.isEmpty(mTheme.userInfo.headwear) ? mTheme.userInfo.headwear : "");

            mTvName.setText(!TextUtils.isEmpty(mTheme.userName) ? mTheme.userName : "主播名还在查找");


            if (mTheme.userInfo.userSex == 0) {

                mTvSeatNum.setBackgroundResource(R.drawable.shape_mike_seak_female);

            } else if (mTheme.userInfo.userSex == 1) {
                mTvSeatNum.setBackgroundResource(R.drawable.shape_mike_seak_male);

            } else {
                mTvSeatNum.setBackgroundResource(R.drawable.shape_mike_seak_num);
            }

            mTvSeatNum.setText((position + 1) + "");

            mTvGiftValueNum.setText((mTheme.userInfo.giftCoinCount > 0) ? mTheme.userInfo.giftCoinCount + "" : "0");

        }

        if (mTheme.isMute) {

            mIvOff.setVisibility(View.VISIBLE);

        } else {
            mIvOff.setVisibility(View.GONE);

        }

    }
}