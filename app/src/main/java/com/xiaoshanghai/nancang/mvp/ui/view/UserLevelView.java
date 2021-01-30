package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.utils.ScreenUtils;

public class UserLevelView extends RelativeLayout {

    private ImageView mIvUserLevel;

    private TextView mTvLevelNum;

    private View mView;

    private Context mContext;


    public UserLevelView(Context context) {
        super(context);
        init(context);
    }

    public UserLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UserLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.view_user_level, this, true);
        mIvUserLevel = mView.findViewById(R.id.iv_user_level);
        mTvLevelNum = mView.findViewById(R.id.tv_level_num);
    }

    public void setUserLevel(int level) {
        int bg = userLevel(level);
        mTvLevelNum.setText(String.valueOf(level));
        mIvUserLevel.setImageResource(bg);
    }

    private int userLevel(int level) {
        if (level <= 0) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u1;

        } else if (level <= 9) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u1;

        } else if (level <= 19) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u10;

        } else if (level <= 29) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u20;

        } else if (level <= 39) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u30;

        } else if (level <= 49) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u40;

        } else if (level <= 59) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u50;

        } else if (level <= 69) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u60;

        } else if (level <= 79) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u70;

        } else if (level <= 89) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u80;

        } else if (level <= 99) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,10),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u90;

        } else if (level <= 119) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u100;

        } else if (level <= 139) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u120;

        } else if (level <= 159) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u140;

        } else if (level <= 179) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u160;

        } else if (level <= 199) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u180;

        } else if (level <= 219) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,4),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u200;

        } else if (level <= 239) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,4),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u220;

        } else if (level <= 259) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,4),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u240;

        } else if (level <= 279) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,4),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u260;

        } else if (level <= 299) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,4),0);
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.white));
            return R.mipmap.icon_u280;

        } else if (level <= 309) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),ScreenUtils.dp2px(mContext,3));
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.color_fce5bd));
            return R.mipmap.icon_u300;

        } else if (level <= 319) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),ScreenUtils.dp2px(mContext,3));
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.color_fce5bd));
            return R.mipmap.icon_u310;

        } else if (level <= 329) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,5),ScreenUtils.dp2px(mContext,3));
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.color_fce5bd));
            return R.mipmap.icon_u320;

        } else if (level <= 339) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,4),ScreenUtils.dp2px(mContext,3));
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.color_fdfaa3));
            return R.mipmap.icon_u330;

        } else if (level <= 349) {
            mTvLevelNum.setPadding(0,0,ScreenUtils.dp2px(mContext,4),ScreenUtils.dp2px(mContext,3));
            mTvLevelNum.setTextColor(mContext.getResources().getColor(R.color.color_fdfaa3));
            return R.mipmap.icon_u340;

        } else if (level == 350) {
            setTextViewStyles(mTvLevelNum);
            return R.mipmap.icon_u350;

        } else {
            setTextViewStyles(mTvLevelNum);
            return R.mipmap.icon_u350;
        }
    }


    private void setTextViewStyles(TextView textView) {
        textView.setPadding(0,0,ScreenUtils.dp2px(mContext,4), ScreenUtils.dp2px(mContext,2));
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, textView.getPaint().getTextSize(), Color.parseColor("#FEF8E1"), Color.parseColor("#FFD10C"), Shader.TileMode.CLAMP);
        textView.getPaint().setShader(mLinearGradient);
        textView.invalidate();
    }


}
