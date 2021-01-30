package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCarHeaderView extends RelativeLayout {

    @BindView(R.id.tv_gift_num)
    TextView tvGiftNum;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private Context mContext;
    private View mView;

    public MyCarHeaderView(Context context) {
        this(context, null);
    }

    public MyCarHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCarHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_gift_heard, this, true);
        ButterKnife.bind(mView);
        mTvTitle.setText("座驾");
    }

    public void setCarNum(int num) {
        tvGiftNum.setText(num + "");
    }
}
