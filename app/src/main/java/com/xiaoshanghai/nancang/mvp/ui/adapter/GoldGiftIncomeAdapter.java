package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.net.bean.GoldGiftHeaderResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldGiftIncomeAdapter extends BaseSectionQuickAdapter<GoldGiftHeaderResult, GoldGiftIncomeAdapter.Holder> {

    private int mChannel;

    public GoldGiftIncomeAdapter(@Nullable List<GoldGiftHeaderResult> data, int channel) {
        super(R.layout.adapter_income_header, data);
        setNormalLayout(R.layout.adapter_gold_gift_income);
        mChannel = channel;

    }

    @Override
    protected void convertHeader(@NotNull Holder holder, @NotNull GoldGiftHeaderResult result) {
        holder.mTvDate.setText(result.date);
    }

    @Override
    protected void convert(@NotNull Holder holder, GoldGiftHeaderResult result) {

        GlideAppUtil.loadImage(getContext(), result.result.getGiftStaticUrl(), holder.mIvGift);
        holder.mTvGiftName.setText(result.result.getGiftName());
        holder.mTvTime.setText(result.result.getTime());
        holder.mTvName.setText(result.result.getUserName());


        switch (mChannel) {
            case Constant.MY_GIFT_GOLD_INCOME:
                holder.mTvGiftNum.setVisibility(View.VISIBLE);
                holder.mTvGiftNum.setText("x"+result.result.getNumber());
                holder.mTvGiftValue.setTextColor(getContext().getResources().getColor(R.color.color_00aeff));
                holder.mTvGiftValue.setText("+"+result.result.getJewelNumber());
                holder.mTvGiftType.setVisibility(View.VISIBLE);
                holder.mTvGiftType.setText("钻石");
                holder.mTvGiftChannel.setText("送礼人：");
                break;
            case Constant.MY_GIFT_CHILI_INCOME:
                holder.mTvGiftNum.setVisibility(View.GONE);
                holder.mTvGiftValue.setTextColor(getContext().getResources().getColor(R.color.color_ff5f85));
                holder.mTvGiftValue.setText("x"+result.result.getNumber());
                holder.mTvGiftType.setVisibility(View.GONE);
                holder.mTvGiftChannel.setText("送礼人：");
                break;
            case Constant.MY_GIFT_GOLD_OUTLAY:
                holder.mTvGiftNum.setVisibility(View.VISIBLE);
                holder.mTvGiftNum.setText("x"+result.result.getNumber());
                holder.mTvGiftValue.setTextColor(getContext().getResources().getColor(R.color.red));
                holder.mTvGiftValue.setText("-"+result.result.getPrice());
                holder.mTvGiftType.setVisibility(View.VISIBLE);
                holder.mTvGiftType.setText("金币");
                holder.mTvGiftChannel.setText("收礼人：");
                break;
            case Constant.MY_GIFT_CHILI_OUTLAY:
                holder.mTvGiftNum.setVisibility(View.VISIBLE);
                holder.mTvGiftNum.setText("x"+result.result.getNumber());
                holder.mTvGiftValue.setTextColor(getContext().getResources().getColor(R.color.color_ff5f85));
                holder.mTvGiftValue.setText("-"+result.result.getPrice());
                holder.mTvGiftType.setVisibility(View.VISIBLE);
                holder.mTvGiftType.setText("辣椒");
                holder.mTvGiftChannel.setText("收礼人：");
                break;
        }
    }

    static class Holder extends BaseViewHolder {
        TextView mTvDate;
        ImageView mIvGift;
        TextView mTvGiftName;
        TextView mTvTime;
        TextView mTvGiftNum;
        TextView mTvGiftChannel;
        TextView mTvName;
        TextView mTvGiftValue;
        TextView mTvGiftType;

        public Holder(@NotNull View view) {
            super(view);
            mTvDate = view.findViewById(R.id.tv_date);
            mIvGift = view.findViewById(R.id.iv_gift);
            mTvGiftName = view.findViewById(R.id.tv_gift_name);
            mTvTime = view.findViewById(R.id.tv_time);
            mTvGiftNum = view.findViewById(R.id.tv_gift_num);
            mTvGiftChannel = view.findViewById(R.id.tv_gift_channel);
            mTvName = view.findViewById(R.id.tv_name);
            mTvGiftValue = view.findViewById(R.id.tv_gift_value);
            mTvGiftType = view.findViewById(R.id.tv_gift_type);


        }
    }

}
