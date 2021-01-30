package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyView extends RelativeLayout {

    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private Context mContext;

    private View mView;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, this, true);
        ButterKnife.bind(mView);
    }

    public void setEmptyImgAndEmptyText(int res,String empty){
        ivEmpty.setImageResource(res);
        if (!TextUtils.isEmpty(empty)){
            tvEmpty.setText(empty);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }



}
