package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.xiaoshanghai.nancang.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoldRechargeHeader extends RelativeLayout {

    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;
    private View mView;

    private Context mContext;

    public GoldRechargeHeader(Context context) {
        this(context, null);
    }

    public GoldRechargeHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoldRechargeHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_gold_recharge_header, this, true);
        ButterKnife.bind(mView);
        setUserInfo();
    }

    private void setUserInfo() {
        UserBean userInfo = SPUtils.getInstance().getUserInfo();
        GlideAppUtil.loadImage(mContext,userInfo.getUserPicture(),civAvatar);
        tvNickName.setText(userInfo.getUserName());
    }

    public void setMyGoldNum(double goldNum) {
        tvGoldNum.setText(String.format("%.1f",goldNum));
    }


}
