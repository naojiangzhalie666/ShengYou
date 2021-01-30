package com.xiaoshanghai.nancang.view;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.base.BaseDialog;


public class LoadingDialog extends BaseDialog {
    private SpinKitView spin_kit;
    private TextView tvContent;
    private ImageView iv_rotate;
    public LoadingDialog(Context context) {
        super(context);
        initViews();
    }

    public LoadingDialog(Context context, boolean fromButton) {
        super(context, fromButton);
        initViews();
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        initViews();
    }

    public LoadingDialog showDialog(Context context, String message ) {
        LoadingDialog dialog = new LoadingDialog(context, R.style.dialog);
        dialog.setContent(message);
        dialog.show();
        return dialog;
    }

    private void initViews() {
        setContentView(R.layout.view_loading);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        tvContent = findViewById(R.id.tvContent);
        spin_kit = findViewById(R.id.spin_kit);
        iv_rotate=findViewById(R.id.iv_rotate);
    }

    public void startRotate(){
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate);
        if (rotate != null) {
            iv_rotate.startAnimation(rotate);
        }  else {
            iv_rotate.setAnimation(rotate);
            iv_rotate.startAnimation(rotate);
        }
    }

    public void stopRotate(){
        if(iv_rotate!=null)
        iv_rotate.clearAnimation();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        }
    }

    public void dismiss(Long delayMills) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },delayMills);
    }
}
