package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterRoomView extends RelativeLayout {


    @BindView(R.id.iv_noble_bg)
    ImageView ivNobleBg;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    private Context mContext;

    private View mView;

    public EnterRoomView(Context context) {
        this(context, null);
    }

    public EnterRoomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnterRoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_enter_room, this, true);
        ButterKnife.bind(mView);
    }

    public void setUserEnterRoomBg(VoiceRoomSeatEntity.UserInfo userInfo) {
        if (userInfo == null) return;
        int bg = getBg(Integer.valueOf(userInfo.noble));
        ivNobleBg.setImageResource(bg);
        tvUserName.setText("“"+userInfo.userName+"”");
    }

    private int getBg(int noble) {
        int bgResult = 0;
        switch(noble) {
            case 1:
                bgResult = R.mipmap.img_noble_1;
                break;
            case 2:
                bgResult = R.mipmap.img_noble_2;
                break;
            case 3:
                bgResult = R.mipmap.img_noble_3;
                break;
            case 4:
                bgResult = R.mipmap.img_noble_4;
                break;
            case 5:
                bgResult = R.mipmap.img_noble_5;
                break;
            case 6:
                bgResult = R.mipmap.img_noble_6;
                break;
            case 7:
                bgResult = R.mipmap.img_noble_7;
                break;
            default:
                bgResult = 0;
                break;
        }

        return bgResult;
    }


}
