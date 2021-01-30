package com.xiaoshanghai.nancang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.xiaoshanghai.nancang.R;


/**

 */
public class KeyboardView extends FrameLayout implements View.OnClickListener {


    private Listener listener;

    public KeyboardView(Context context) {
        super(context);
        init();
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_pwd_keyboard, null);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(view);
        view.findViewById(R.id.keyboard_0).setOnClickListener(this);
        view.findViewById(R.id.keyboard_1).setOnClickListener(this);
        view.findViewById(R.id.keyboard_2).setOnClickListener(this);
        view.findViewById(R.id.keyboard_3).setOnClickListener(this);
        view.findViewById(R.id.keyboard_4).setOnClickListener(this);
        view.findViewById(R.id.keyboard_5).setOnClickListener(this);
        view.findViewById(R.id.keyboard_6).setOnClickListener(this);
        view.findViewById(R.id.keyboard_7).setOnClickListener(this);
        view.findViewById(R.id.keyboard_8).setOnClickListener(this);
        view.findViewById(R.id.keyboard_9).setOnClickListener(this);
        view.findViewById(R.id.keyboard_hide).setOnClickListener(this);
        view.findViewById(R.id.keyboard_delete).setOnClickListener(this);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        final String tag = (String) v.getTag();
        if (tag != null) {
            switch (tag) {
                case "hide":
//                    hide();
                    break;
                case "commit":
                    if (listener != null) {
                        listener.onCommit(tag);
                    }
                    break;
                case "delete":

                    if (listener != null) {
                        listener.onDelete();
                    }
                    break;
                default:
                    if (listener != null) {
                        listener.onInput(tag);
                    }
                    break;
            }
        }
    }

    public interface Listener {

        void onInput(String s);

        void onDelete();

        void onCommit(String s);

    }

}
