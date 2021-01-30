package com.xiaoshanghai.nancang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaoshanghai.nancang.R;

public class DialogRoomLockView extends RelativeLayout {

    private Context mContext;

    private View mView;

    public DialogRoomLockView(Context context) {
        this(context,null);
    }

    public DialogRoomLockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DialogRoomLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.dialog_room_lock, this, true);
    }

    public <T extends View> T getView(int viewId) {

        return mView.findViewById(viewId);
    }
}
