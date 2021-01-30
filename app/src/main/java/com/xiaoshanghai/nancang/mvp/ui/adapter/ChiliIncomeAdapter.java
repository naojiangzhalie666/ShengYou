package com.xiaoshanghai.nancang.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.ChiliIncomeHeaderBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChiliIncomeAdapter extends BaseSectionQuickAdapter<ChiliIncomeHeaderBean, BaseViewHolder> {

    public ChiliIncomeAdapter(@Nullable List<ChiliIncomeHeaderBean> data) {
        super(R.layout.adapter_income_header, data);
        setNormalLayout(R.layout.adapter_chili_income);

    }

    @Override
    protected void convertHeader(@NotNull BaseViewHolder holder, @NotNull ChiliIncomeHeaderBean result) {
        holder.setText(R.id.tv_date, result.date);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ChiliIncomeHeaderBean result) {
        if (result.result.getType() == 1) {
            holder.setText(R.id.tv_gift_name, "签到进账");
        } else if (result.result.getType() == 2) {
            holder.setText(R.id.tv_gift_name, "抽奖进账");
        }

        holder.setText(R.id.tv_gift_type, result.result.getDate());
        holder.setText(R.id.tv_income_num, "+" + result.result.getNumber());


    }
}
