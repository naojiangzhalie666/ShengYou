package com.xiaoshanghai.nancang.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.IncomeHeaderBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IncomeAdapter extends BaseSectionQuickAdapter<IncomeHeaderBean, BaseViewHolder> {

    public IncomeAdapter(@Nullable List<IncomeHeaderBean> data) {
        super(R.layout.adapter_income_header, data);
        setNormalLayout(R.layout.adapter_income);

    }

    @Override
    protected void convertHeader(@NotNull BaseViewHolder holder, @NotNull IncomeHeaderBean result) {
        holder.setText(R.id.tv_date, result.date);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, IncomeHeaderBean result) {
        int type = result.result.getType();
        if (type == 1) {
            if (result.result.getGift() != null) {
                holder.setText(R.id.tv_gift_name, result.result.getGift().getGiftName());
            } else {
                holder.setText(R.id.tv_gift_name, "");
            }
            holder.setText(R.id.tv_gift_type, "收礼进账");
        } else if (type == 2) {
            holder.setText(R.id.tv_gift_name, "佣金进账");
            holder.setText(R.id.tv_gift_type, result.result.getDate());
        }else if (type == 5){
            holder.setText(R.id.tv_gift_name, "提现失败退回");
            holder.setText(R.id.tv_gift_type,  result.result.getDate());
        } else if (type == 9) {
            holder.setText(R.id.tv_gift_name, "运营转入");
            holder.setText(R.id.tv_gift_type,  result.result.getDate());
        }
        holder.setText(R.id.tv_income_num, "+" + result.result.getNumber());

    }
}
