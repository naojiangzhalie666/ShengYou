package com.xiaoshanghai.nancang.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
class DrawLineTextView extends TextView {
    public DrawLineTextView(Context context) {
        this(context, null);
    }

    public DrawLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}