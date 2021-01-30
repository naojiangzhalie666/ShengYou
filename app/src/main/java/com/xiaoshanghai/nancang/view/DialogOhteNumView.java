package com.xiaoshanghai.nancang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaoshanghai.nancang.R;

public class DialogOhteNumView extends RelativeLayout {

    private Context mContext;

    private View mView;

    public DialogOhteNumView(Context context) {
        this(context,null);
    }

    public DialogOhteNumView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DialogOhteNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.dialog_othe_num, this, true);
    }

    public <T extends View> T getView(int viewId) {

        return mView.findViewById(viewId);
    }
}
