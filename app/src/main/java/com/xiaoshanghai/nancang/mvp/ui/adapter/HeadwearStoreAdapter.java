package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;

public class HeadwearStoreAdapter extends BaseQuickAdapter<Decks, HeadwearStoreAdapter.Holder> {


    public HeadwearStoreAdapter() {

        super(R.layout.adapter_headwear_store);
    }


    @Override
    protected void convert(@NotNull Holder holder, Decks decks) {

        if (decks.isSelect()) {
            holder.mRlHeadwear.setBackgroundResource(R.drawable.shape_car_select_bg);
            holder.mTvHeadwearName.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.mRlHeadwear.setBackgroundResource(R.drawable.shape_car_bg);
            holder.mTvHeadwearName.setTextColor(getContext().getResources().getColor(R.color.color_black));
        }

        GlideAppUtil.loadImage(getContext(),decks.getDeckUrl(),holder.mIvHeadwear);
        holder.mTvStint.setVisibility(decks.getCostType() == 0 ? View.VISIBLE : View.GONE);
        holder.mTvHeadwearName.setText(decks.getDeckName());

    }

    static class Holder extends BaseViewHolder {

        private RelativeLayout mRlHeadwear;
        private ImageView mIvHeadwear;
        private TextView mTvStint;
        private TextView mTvHeadwearName;

        public Holder(@NotNull View view) {
            super(view);
            mRlHeadwear = view.findViewById(R.id.rl_headwear);
            mIvHeadwear = view.findViewById(R.id.iv_headwear);
            mTvStint = view.findViewById(R.id.tv_stint);
            mTvHeadwearName = view.findViewById(R.id.tv_headwear_name);
        }
    }
}
