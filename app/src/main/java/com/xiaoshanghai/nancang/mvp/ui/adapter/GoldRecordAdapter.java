package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.CashEntity;
import com.xiaoshanghai.nancang.net.bean.CashMuListEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldRecordAdapter extends BaseSectionQuickAdapter<CashMuListEntity, BaseViewHolder> {

    public GoldRecordAdapter(@Nullable List<CashMuListEntity> data) {
        super(R.layout.adapter_income_header, data);
        setNormalLayout(R.layout.adapter_item_gold_record);

    }

    @Override
    protected void convertHeader(@NotNull BaseViewHolder holder, @NotNull CashMuListEntity result) {
        holder.setText(R.id.tv_date, result.getDate());
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, CashMuListEntity result) {

        CashEntity cashEntity = result.getResult();
        if (cashEntity != null) {
            holder.setText(R.id.tv_time,cashEntity.getTime());
            holder.setText(R.id.tv_money_type,cashEntity.getCoinNumber()+"");
            holder.setText(R.id.tv_zs,"-"+cashEntity.getAmount()+"元");

            TextView mTvNum = holder.getView(R.id.tv_zs_num);
            if (cashEntity.getPayType() == 1) {
                mTvNum.setTextColor(getContext().getResources().getColor(R.color.color_1ea7da));
                mTvNum.setText("支付宝");
            } else if (cashEntity.getPayType() == 2){
                mTvNum.setTextColor(getContext().getResources().getColor(R.color.color_1eda38));
                mTvNum.setText("微信");
            }
        }

    }
}
