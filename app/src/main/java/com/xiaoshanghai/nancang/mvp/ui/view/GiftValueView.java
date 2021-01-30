package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftValueView extends RelativeLayout {

    @BindView(R.id.tv_gift_value_num)
    TextView mTvGiftValueNum;


    private Context mContext;

    private View mView;

    public GiftValueView(Context context) {
        this(context, null);
    }

    public GiftValueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_gift_value, this, true);
        ButterKnife.bind(mView);
    }

    public void setGiftValue(VoiceRoomSeatEntity.UserInfo userInfo) {
        if (userInfo == null) {
            mTvGiftValueNum.setText("0");
        } else {
            mTvGiftValueNum.setText(userInfo.giftCoinCount + "");

        }
    }

    public void clearValue() {
        mTvGiftValueNum.setText("0");
    }


}
