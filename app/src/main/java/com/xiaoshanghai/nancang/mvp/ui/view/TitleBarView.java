package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.TitleBarClickCallback;
import com.xiaoshanghai.nancang.constant.Constant;

/**
 * TitleBarView 简单封装公用titlebar
 * 写的很烦，想整理下
 */
public class TitleBarView extends RelativeLayout implements View.OnClickListener {

    private ImageView mIvBlack;
    private TextView mTvTitle;
    private TextView mRight1;
    private TextView mRight2;
    private RelativeLayout mTitle;

    private Context mContext;

    private View mView;
    private TitleBarClickCallback mCallback;

    public TitleBarView(Context context) {
        this(context, null);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnClickCallback(TitleBarClickCallback callback) {
        this.mCallback = callback;
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.view_title_bar, this, true);

        mIvBlack = mView.findViewById(R.id.iv_black);
        mTvTitle = mView.findViewById(R.id.tv_title_name);
        mRight1 = mView.findViewById(R.id.tv_right_1);
        mRight2 = mView.findViewById(R.id.tv_right_2);
        mTitle = mView.findViewById(R.id.title);

        mIvBlack.setOnClickListener(this);
        mRight1.setOnClickListener(this);
        mRight2.setOnClickListener(this);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBarView);

        int color = typedArray.getColor(R.styleable.TitleBarView_bgColor, 0);
        mTitle.setBackgroundColor(color);

        int titleColor = typedArray.getColor(R.styleable.TitleBarView_titleColor, 0);
        setTitleColor(titleColor);

        int color1 = typedArray.getColor(R.styleable.TitleBarView_right1Color, 0);
        int color2 = typedArray.getColor(R.styleable.TitleBarView_right2Color, 0);

        mRight1.setTextColor(color1);
        mRight2.setTextColor(color2);

        int blackResource = typedArray.getResourceId(R.styleable.TitleBarView_blackImg, R.mipmap.square_release_back);
        String titleString = typedArray.getString(R.styleable.TitleBarView_title);

        int right1Img = typedArray.getResourceId(R.styleable.TitleBarView_right1Img, 0);
        int right2Img = typedArray.getResourceId(R.styleable.TitleBarView_right2Img, 0);

        String right1Text = typedArray.getString(R.styleable.TitleBarView_right1Text);
        String right2Text = typedArray.getString(R.styleable.TitleBarView_right2Text);

        setStyleable(blackResource, titleString, right1Img, right1Text, right2Img, right2Text);


    }

    private void setTitleColor(int resources){
        mTvTitle.setTextColor(resources );
    }

    private void setStyleable(int blackResource, String titleString, int right1Img, String right1Text, int right2Img, String right2Text) {
        mIvBlack.setImageResource(blackResource);
        mTvTitle.setText(titleString);

        if (right1Img == 0 && TextUtils.isEmpty(right1Text)) {
            mRight1.setVisibility(GONE);
        } else {
            mRight1.setText(right1Text);
            settingDrawableTop(getContext(), mRight1, right1Img, right1Text);
        }

        if (right2Img == 0 && TextUtils.isEmpty(right2Text)) {
            mRight2.setVisibility(GONE);
        } else {
            mRight2.setText(right1Text);
            settingDrawableTop(getContext(), mRight2, right2Img, right2Text);
        }

    }

    public void setLeftImage(int resources) {
        mIvBlack.setImageResource(resources);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setR1Text(String r1Text) {
        settingDrawableTop(getContext(), mRight1, 0, r1Text);
    }

    public void setR1TextAndImg(int resources, String r1Text) {
        settingDrawableTop(getContext(), mRight1, resources, r1Text);
    }

    public void setR2Text(String r2Text) {
        settingDrawableTop(getContext(), mRight2, 0, r2Text);
    }

    public void setR2TextAndImg(int resources, String r2Text) {
        settingDrawableTop(getContext(), mRight2, resources, r2Text);
    }

    private void settingDrawableTop(Context context, TextView view, int resourcesDrawable, String resourcesString) {
        view.setVisibility(View.VISIBLE);
        if (0 != resourcesDrawable) {
            Drawable drawable = null;
            drawable = context.getResources().getDrawable(resourcesDrawable);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                view.setCompoundDrawables(drawable, null, null, null);//图在左边
                view.setText(resourcesString);
            }
        } else {
            view.setCompoundDrawables(null, null, null, null);//图在左边
            view.setText(resourcesString);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                if (mCallback != null) {
                    mCallback.titleLeftClick();
                }
                break;
            case R.id.tv_right_1:
                if (mCallback != null) {
                    mCallback.titleRightClick(Constant.RIGHT_CLICK_1);
                }
                break;
            case R.id.tv_right_2:
                if (mCallback != null) {
                    mCallback.titleRightClick(Constant.RIGHT_CLICK_2);
                }
                break;
        }
    }
}