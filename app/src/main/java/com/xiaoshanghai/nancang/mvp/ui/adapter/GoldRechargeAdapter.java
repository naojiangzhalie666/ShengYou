package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.GoldResult;

import org.jetbrains.annotations.NotNull;

public class GoldRechargeAdapter extends BaseQuickAdapter<GoldResult, GoldRechargeAdapter.Holder> {

    private int mStatus = -1;

    public GoldRechargeAdapter(int status) {
        super(R.layout.adapter_gold_recharge);
        mStatus = status;
    }

    @Override
    protected void convert(@NotNull Holder holder, GoldResult result) {

        if (mStatus == 0) {
            holder.mIvGold.setVisibility(View.VISIBLE);
            holder.mTvGoldType.setText("金币");
            holder.mIvFrist.setImageResource(R.mipmap.icon_gold_discount);
            holder.mTvGoldNum.setText(String.valueOf(result.getCoinNumber()));
        } else if (mStatus == 1) {
            holder.mIvGold.setVisibility(View.GONE);
            holder.mTvGoldType.setText("钻石");
            holder.mIvFrist.setImageResource(R.mipmap.icon_first_cash);
            holder.mTvGoldNum.setText(String.valueOf(result.getJewelNumber()));
        }

        if (result.isSelect()) {

            if (mStatus == 0) {
                holder.mRlGold.setBackgroundResource(R.drawable.shape_gold_recharge);
            } else if (mStatus == 1) {
                holder.mRlGold.setBackgroundResource(R.drawable.shape_cash_recharge);
            }

            holder.mIvGold.setImageResource(R.mipmap.icon_select_gold);
            holder.mTvGoldNum.setTextColor(getContext().getResources().getColor(R.color.color_black));
            holder.mTvGoldType.setTextColor(getContext().getResources().getColor(R.color.color_black));
            holder.mTvRmbNum.setTextColor(getContext().getResources().getColor(R.color.color_black));
            holder.mTvRmbType.setTextColor(getContext().getResources().getColor(R.color.color_black));

        } else {
            holder.mRlGold.setBackgroundResource(R.drawable.shape_gold_recharge_unselect);
            holder.mIvGold.setImageResource(R.mipmap.icon_my_gold_off);
            holder.mTvGoldType.setTextColor(getContext().getResources().getColor(R.color.color_b2b2b2));
            holder.mTvRmbNum.setTextColor(getContext().getResources().getColor(R.color.color_b2b2b2));
            holder.mTvRmbType.setTextColor(getContext().getResources().getColor(R.color.color_b2b2b2));
        }

        if (result.getIsFirst() == 1) {
            holder.mIvFrist.setVisibility(View.VISIBLE);
        } else {
            holder.mIvFrist.setVisibility(View.GONE);
        }




        holder.mTvRmbNum.setText(String.valueOf(result.getAmount()));


    }

    static class Holder extends BaseViewHolder {

        private RelativeLayout mRlGold;
        private ImageView mIvGold;
        private TextView mTvGoldNum;
        private TextView mTvGoldType;
        private TextView mTvRmbNum;
        private TextView mTvRmbType;
        private ImageView mIvFrist;

        public Holder(@NotNull View view) {
            super(view);

            mRlGold = view.findViewById(R.id.rl_gold);
            mIvGold = view.findViewById(R.id.iv_gold);
            mTvGoldNum = view.findViewById(R.id.tv_gold_num);
            mTvGoldType = view.findViewById(R.id.tv_gold_type);
            mTvRmbNum = view.findViewById(R.id.tv_rmb_num);
            mTvRmbType = view.findViewById(R.id.tv_rmb_type);
            mIvFrist = view.findViewById(R.id.iv_frist);

        }
    }

}
