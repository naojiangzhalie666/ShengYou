package com.xiaoshanghai.nancang.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.mvp.ui.adapter.SignAdapter;
import com.xiaoshanghai.nancang.net.bean.SignRewardEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChiliProgressbar extends LinearLayout {

    @BindView(R.id.pb_lv)
    ProgressBar pbLv;
    @BindView(R.id.iv_chili_0)
    ImageView ivChili0;
    @BindView(R.id.iv_chili_1)
    ImageView ivChili1;
    @BindView(R.id.iv_chili_2)
    ImageView ivChili2;
    @BindView(R.id.iv_chili_3)
    ImageView ivChili3;
    @BindView(R.id.iv_chili_4)
    ImageView ivChili4;
    @BindView(R.id.iv_chili_5)
    ImageView ivChili5;
    @BindView(R.id.iv_chili_6)
    ImageView ivChili6;
    @BindView(R.id.iv_chili_7)
    ImageView ivChili7;
    @BindView(R.id.rcy_sign)
    RecyclerView rcySign;

    private ArrayList<View> ids = new ArrayList<>();

    private List<SignRewardEntity> mSignResult = new ArrayList();

    private SignAdapter mAdapter = new SignAdapter();

    private Context mContext;
    private View mView;
    private int mIndex = -1;

    public ChiliProgressbar(Context context) {
        this(context, null);
    }

    public ChiliProgressbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChiliProgressbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.view_chili_progress_bar, this);
        ButterKnife.bind(mView);
        ids.add(ivChili0);
        ids.add(ivChili1);
        ids.add(ivChili2);
        ids.add(ivChili3);
        ids.add(ivChili4);
        ids.add(ivChili5);
        ids.add(ivChili6);
        ids.add(ivChili7);

        rcySign.setLayoutManager(new GridLayoutManager(mContext, 7));
        rcySign.setAdapter(mAdapter);


    }

    public void setProgress(boolean isSign, int signDay) {

        int sign = signDay % 7;

        if (isSign) {
            if (sign == 0) {
                pbLv.setProgress(7);
                mIndex = 7;
                showChili(7);
            } else {
                pbLv.setProgress(sign);
                mIndex = sign;
                showChili(sign);
            }
        } else {
            if (sign == 0) {
                pbLv.setProgress(0);
                mIndex = 0;
                showChili(0);
            } else {
                pbLv.setProgress(sign);
                mIndex = sign;
                showChili(sign);
            }
        }

        if (mSignResult.size() > 0 && mIndex > 0) {
            for (int i = 0; i < mSignResult.size(); i++) {
                SignRewardEntity signRewardEntity = mSignResult.get(i);
                if (i < mIndex) {
                    signRewardEntity.setOpen(true);
                } else {
                    signRewardEntity.setOpen(false);
                }
            }
            mAdapter.setList(mSignResult);
        }

    }

    private void showChili(int index) {
        if (index <= 0) {
            index = 0;
        } else if (index >= 7) {
            index = 7;
        }

        for (int i = 0; i < ids.size(); i++) {
            if (index == i) {
                ids.get(i).setVisibility(View.VISIBLE);
            } else {
                ids.get(i).setVisibility(View.GONE);
            }
        }

    }

    public void setSignResult(List<SignRewardEntity> signResult) {
        mSignResult.clear();
        mSignResult.addAll(signResult);

        if (mSignResult.size() > 0 && mIndex > 0) {
            for (int i = 0; i < mSignResult.size(); i++) {
                SignRewardEntity signRewardEntity = mSignResult.get(i);
                if (i < mIndex) {
                    signRewardEntity.setOpen(true);
                } else {
                    signRewardEntity.setOpen(false);
                }
            }
            if (mAdapter != null) {
                mAdapter.setList(mSignResult);
            }
        } else {
            mAdapter.setList(mSignResult);
        }


    }

}
