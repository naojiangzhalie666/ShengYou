package com.xiaoshanghai.nancang.utils;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class RecyclerViewAnimUtil {
    private static final RecyclerViewAnimUtil ourInstance = new RecyclerViewAnimUtil();

    public static RecyclerViewAnimUtil getInstance() {
        return ourInstance;
    }

    private RecyclerViewAnimUtil() {
    }
    /**
     * 关闭默认局部刷新动画
     */
    public void closeDefaultAnimator(RecyclerView mRvCustomer) {
        if(null==mRvCustomer)return;
        mRvCustomer.getItemAnimator().setAddDuration(0);
        mRvCustomer.getItemAnimator().setChangeDuration(0);
        mRvCustomer.getItemAnimator().setMoveDuration(0);
        mRvCustomer.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);


//        mRvCustomer.getItemAnimator().setAddDuration(0);
//        mRvCustomer.getItemAnimator().setChangeDuration(0);
//        mRvCustomer.getItemAnimator().setMoveDuration(0);
//        mRvCustomer.getItemAnimator().setRemoveDuration(0);
//      ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);


    }
}