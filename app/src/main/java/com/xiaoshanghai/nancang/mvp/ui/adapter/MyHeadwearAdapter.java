package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;

public class MyHeadwearAdapter extends BaseQuickAdapter<Decks, MyHeadwearAdapter.Holder> {

    public MyHeadwearAdapter() {
        super(R.layout.adapter_my_headwear);
        addChildClickViewIds(R.id.tv_use);
    }

    @Override
    protected void convert(@NotNull Holder holder, Decks decks) {
        GlideAppUtil.loadImage(getContext(), decks.getDeckUrl(), holder.mIvHeadwear);
        if (decks.getCostType() == 0) {
            holder.mTvStint.setVisibility(View.VISIBLE);
        } else {
            holder.mTvStint.setVisibility(View.GONE);
        }

        holder.mTvHeadwearName.setText(decks.getDeckName());

        boolean isExpired = decks.getValidity() == 0 ;      //是否过期
        boolean isUse = decks.getUsed() == 1;          //是否使用


        if (!isExpired){         //未过期
            holder.mTvDate.setText("剩余" + decks.getValidityDays() + "天");

            if (isUse) {        //使用中
                holder.mTvUse.setText("使用中");
                holder.mTvUse.setBackgroundResource(R.drawable.shape_using_bg);

            } else {            //未使用
                holder.mTvUse.setText("使用");
                holder.mTvUse.setBackgroundResource(R.drawable.shape_use_bg);

            }

        } else {        //已过期
            holder.mTvDate.setText("已过期");
            holder.mTvUse.setText("缴费");
            holder.mTvUse.setBackgroundResource(R.drawable.shape_use_bg);

        }



    }

    static class Holder extends BaseViewHolder {
        private ImageView mIvHeadwear;
        private TextView mTvStint;
        private TextView mTvHeadwearName;
        private TextView mTvDate;
        private TextView mTvUse;

        public Holder(@NotNull View view) {
            super(view);
            mIvHeadwear = view.findViewById(R.id.iv_headwear);
            mTvStint = view.findViewById(R.id.tv_stint);
            mTvHeadwearName = view.findViewById(R.id.tv_headwear_name);
            mTvDate = view.findViewById(R.id.tv_date);
            mTvUse = view.findViewById(R.id.tv_use);
        }
    }
}
