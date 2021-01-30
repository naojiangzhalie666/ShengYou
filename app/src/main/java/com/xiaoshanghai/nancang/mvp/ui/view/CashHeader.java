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


public class CashHeader extends RelativeLayout {

    @BindView(R.id.tv_cash)
    TextView mTvCash;

    private View mView;

    private Context mContext;

    public CashHeader(Context context) {
        this(context, null);
    }

    public CashHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CashHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_cash_header, this, true);
        ButterKnife.bind(mView);
    }


    public void setCash(Double cash) {
        mTvCash.setText(String.format("%.1f",cash));
    }


}
