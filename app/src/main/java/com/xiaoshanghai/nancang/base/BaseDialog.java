package com.xiaoshanghai.nancang.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.utils.ScreenUtils;


public class BaseDialog extends Dialog {
    View mView;
    boolean cancel = true;
    boolean touchOutsideCancel = false;

    protected View getRootView() {
        return mView;
    }

    public BaseDialog(Context context) {
        super(context, R.style.dialog);
    }

    public BaseDialog(Context context, boolean fromButton) {
        super(context, R.style.Dialog_NoTitle);
        if (fromButton) {
            fromBottom();
        }
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(@LayoutRes int resource) {
        mView = LayoutInflater.from(getContext()).inflate(resource, null);
        //设置SelectPicPopupWindow的View
        setContentView(mView);
        //设置全屏
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (touchOutsideCancel)
                    dismiss();
            }
        });
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        cancel = flag;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        touchOutsideCancel = cancel;
    }

    private void fromBottom() {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.width = ScreenUtils.getScreenWidth();
        localLayoutParams.y = 0;
        localLayoutParams.x = 0;
        onWindowAttributesChanged(localLayoutParams);
    }
}
