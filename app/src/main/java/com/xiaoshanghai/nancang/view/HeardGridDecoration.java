package com.xiaoshanghai.nancang.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class HeardGridDecoration extends RecyclerView.ItemDecoration {

    int mHorizontal;
    int mVertical;
    int mLeftMargin;
    int mRightMargin;
    int mSpanCount;

    public HeardGridDecoration(int horizontal, int vertical, int leftMargin, int rightMargin, int spanCount) {
        this.mHorizontal = horizontal;
        this.mVertical = vertical;
        this.mLeftMargin = leftMargin;
        this.mRightMargin = rightMargin;
        this.mSpanCount = spanCount;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position != 0) {

            if (position == 1 || position == 2) {       //第一行

                if ((position) % mSpanCount == 1) {            //第一列

                    left = this.mLeftMargin;
                    top = this.mVertical;
                    right = this.mHorizontal / 2;
                    bottom = this.mVertical / 2;

                } else if ((position) % mSpanCount == 0) {       //最后一列

                    left = this.mHorizontal / 2;
                    top = this.mVertical;
                    right = this.mRightMargin;
                    bottom = this.mVertical / 2;

                } else {                                        //其他咧

                    left = this.mHorizontal / 2;
                    top = this.mVertical;
                    right = this.mHorizontal / 2;
                    bottom = this.mVertical / 2;

                }

            } else {

                if ((position) % mSpanCount == 1) {            //第一列

                    left = this.mLeftMargin;
                    top = this.mVertical / 2;
                    right = this.mHorizontal / 2;
                    bottom = this.mVertical / 2;

                } else if ((position % mSpanCount) == 0) {       //最后一列

                    left = this.mHorizontal / 2;
                    top = this.mVertical / 2;
                    right = this.mRightMargin;
                    bottom = this.mVertical / 2;

                } else {                                        //其他咧

                    left = this.mHorizontal / 2;
                    top = this.mVertical / 2;
                    right = this.mHorizontal / 2;
                    bottom = this.mVertical / 2;

                }
            }
        }

        outRect.set(left, top, right, bottom);

    }
}
