package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.OnItemClickCallback;
import com.xiaoshanghai.nancang.net.bean.StartRecommendResult;
import com.xiaoshanghai.nancang.mvp.ui.adapter.StartCommendAdapter.Holder;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;


import org.jetbrains.annotations.NotNull;


public class StartCommendAdapter extends BaseQuickAdapter<StartRecommendResult, Holder> {

    private OnItemClickCallback mCallback;

    public StartCommendAdapter() {
        super(R.layout.adapter_start_commend);
    }

    public void setOnClickItem(OnItemClickCallback callback) {
        this.mCallback = callback;
    }

    @Override
    protected void convert(@NotNull Holder holder, StartRecommendResult result) {
        GlideAppUtil.loadImage(getContext(),result.getClanPicture(),holder.mIvFamilyLogo);
        holder.mTvFamilyName.setText(result.getClanName());
        holder.mTvFailyId.setText(result.getClanNumber()+"");
        holder.mTvFamilyNum.setText(result.getMember()+"");
    }

    static class Holder extends BaseViewHolder {

        CircleImageView mIvFamilyLogo;
        TextView mTvFamilyName;
        TextView mTvFailyId;
        TextView mTvFamilyNum;

        public Holder(@NotNull View view) {
            super(view);
            mIvFamilyLogo = view.findViewById(R.id.iv_family_logo);
            mTvFamilyName = view.findViewById(R.id.tv_family_name);
            mTvFailyId = view.findViewById(R.id.tv_family_id);
            mTvFamilyNum = view.findViewById(R.id.tv_family_num);
        }
    }
}
