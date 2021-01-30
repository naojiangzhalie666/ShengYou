package com.xiaoshanghai.nancang.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.IncomeHeaderBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OutComeAdapter extends BaseSectionQuickAdapter<IncomeHeaderBean, BaseViewHolder> {

    public OutComeAdapter(@Nullable List<IncomeHeaderBean> data) {
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
        if (type == 3) {
            holder.setText(R.id.tv_gift_name, "金币出账");
            holder.setText(R.id.tv_gift_type, result.result.getDate());
        } else if (type == 4) {
            holder.setText(R.id.tv_gift_name, "提现出账");
            holder.setText(R.id.tv_gift_type, result.result.getDate());
        } else if (type == 10) {
            holder.setText(R.id.tv_gift_name, "系统删除");
            holder.setText(R.id.tv_gift_type, result.result.getDate());
        }
        holder.setText(R.id.tv_income_num, "-" + result.result.getNumber());
    }
}
