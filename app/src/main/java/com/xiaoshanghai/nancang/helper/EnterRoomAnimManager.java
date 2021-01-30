package com.xiaoshanghai.nancang.helper;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.utils.ScreenUtils;

import java.util.ArrayList;


public class EnterRoomAnimManager implements AnimationListener.Stop {

    private boolean isPlay;

    private ArrayList<View> views = new ArrayList<>();

    private ConstraintLayout mCostraint;

    private Context mContext;

    private ConstraintSet constraintSet = new ConstraintSet();


    private EnterRoomAnimManager() {
    }

    @Override
    public void onStop() {
        mCostraint.removeView(views.get(0));
        views.remove(views.get(0));
        isPlay = false;
        if (views.size() > 0) {
            playAnimation(views.get(0));
        }
    }

    private static class SingletonInstance {
        private static final EnterRoomAnimManager INSTANCE = new EnterRoomAnimManager();
    }

    public static EnterRoomAnimManager getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void setInfo(Context context, ConstraintLayout costraint) {
        this.mContext = context;
        this.mCostraint = costraint;
    }

    public void addAnimation(View view) {
        views.add(view);
        if (!isPlay) {
            if (views.size() > 0) {
                playAnimation(views.get(0));
            }
        }
    }

    private void playAnimation(View view) {

        constraintSet.constrainWidth(view.getId(), ScreenUtils.dp2px(mContext, 390f));
        constraintSet.constrainHeight(view.getId(), ScreenUtils.dp2px(mContext, 130f));
        constraintSet.setDimensionRatio(view.getId(), "h,16:9");

        constraintSet.connect(view.getId(), ConstraintSet.TOP, R.id.ll_seat_face, ConstraintSet.BOTTOM);

        constraintSet.connect(view.getId(), ConstraintSet.END, R.id.ll_seat_face, ConstraintSet.START);

//        constraintSet.connect(view.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);


        mCostraint.addView(view);

        TransitionManager.beginDelayedTransition(mCostraint);
        constraintSet.applyTo(mCostraint);

        isPlay = true;
        ViewAnimator.animate(view)
                .translationX(0f,ScreenUtils.dp2px(mContext, 400f))
                .interpolator(new DecelerateInterpolator())
                .duration(1000)
                .thenAnimate(view)
                .scale(1f, 1f, 1f)
                .interpolator(new AccelerateInterpolator())
                .duration(1000)
                .thenAnimate(view)
                .alpha(1f, 0f)
                .interpolator(new DecelerateInterpolator())
                .duration(1000)
                .onStop(this)
                .start();


    }
}


