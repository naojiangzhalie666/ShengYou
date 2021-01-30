package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.NewGoldResult;

import org.jetbrains.annotations.NotNull;

public class NewGoldRechargeAdapter extends BaseQuickAdapter<NewGoldResult, NewGoldRechargeAdapter.Holder> {

    public NewGoldRechargeAdapter() {
        super(R.layout.adapter_new_gold_recharge);
    }

    @Override
    protected void convert(@NotNull Holder holder, NewGoldResult result) {



        if (result.isSelect()) {
            holder.mRlGold.setBackgroundResource(R.drawable.shape_cash_recharge);
            holder.mTvGoldNum.setTextColor(getContext().getResources().getColor(R.color.color_black));
            holder.mTvGoldType.setTextColor(getContext().getResources().getColor(R.color.color_black));
            holder.mTvRmbNum.setTextColor(getContext().getResources().getColor(R.color.color_black));
            holder.mTvRmbType.setTextColor(getContext().getResources().getColor(R.color.color_black));

        } else {
            holder.mRlGold.setBackgroundResource(R.drawable.shape_gold_recharge_unselect);
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
        holder.mTvGoldType.setText("钻石");
        holder.mIvFrist.setImageResource(R.mipmap.icon_first_cash);
        holder.mTvGoldNum.setText(String.valueOf(result.getJewelNumber()));

    }

    static class Holder extends BaseViewHolder {

        private RelativeLayout mRlGold;
        private TextView mTvGoldNum;
        private TextView mTvGoldType;
        private TextView mTvRmbNum;
        private TextView mTvRmbType;
        private ImageView mIvFrist;

        public Holder(@NotNull View view) {
            super(view);

            mRlGold = view.findViewById(R.id.rl_gold);
            mTvGoldNum = view.findViewById(R.id.tv_gold_num);
            mTvGoldType = view.findViewById(R.id.tv_gold_type);
            mTvRmbNum = view.findViewById(R.id.tv_rmb_num);
            mTvRmbType = view.findViewById(R.id.tv_rmb_type);
            mIvFrist = view.findViewById(R.id.iv_frist);

        }
    }

}
