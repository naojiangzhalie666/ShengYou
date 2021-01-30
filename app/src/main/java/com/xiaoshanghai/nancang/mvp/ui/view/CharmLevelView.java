package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;


public class CharmLevelView extends RelativeLayout {

    private Context mContext;

    private View mView;
    private ImageView mIvCharm;
    private TextView mTvCharmNum;

    public CharmLevelView(Context context) {
        super(context);
        initView(context);
    }

    public CharmLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CharmLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.view_charm_level, this);
        mIvCharm = mView.findViewById(R.id.iv_charm);
        mTvCharmNum = mView.findViewById(R.id.tv_charm_num);
    }

    public void setCharmLevel(int level) {
        int bg = charmLevel(level);
        mTvCharmNum.setText(String.valueOf(level));
        mIvCharm.setImageResource(bg);
    }

    private int charmLevel(int level) {
        if (level <= 0) {
            return R.mipmap.icon_m1;

        } else if (level <= 9) {

            return R.mipmap.icon_m1;

        } else if (level <= 19) {

            return R.mipmap.icon_m10;

        } else if (level <= 29) {

            return R.mipmap.icon_m20;

        } else if (level <= 39) {

            return R.mipmap.icon_m30;

        } else if (level <= 49) {

            return R.mipmap.icon_m40;

        } else if (level <= 59) {

            return R.mipmap.icon_m50;

        } else if (level <= 69) {

            return R.mipmap.icon_m60;

        } else if (level == 70) {

            return R.mipmap.icon_m70;

        } else {
            return R.mipmap.icon_m70;
        }
    }

}
