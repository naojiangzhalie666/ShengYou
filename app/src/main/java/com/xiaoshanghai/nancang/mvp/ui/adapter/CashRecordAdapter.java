package com.xiaoshanghai.nancang.mvp.ui.adapter;


import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.CashEntity;
import com.xiaoshanghai.nancang.net.bean.CashMuListEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CashRecordAdapter extends BaseSectionQuickAdapter<CashMuListEntity, BaseViewHolder> {

    public CashRecordAdapter(@Nullable List<CashMuListEntity> data) {
        super(R.layout.adapter_income_header, data);
        setNormalLayout(R.layout.adapter_item_cash_record);
    }

    @Override
    protected void convertHeader(@NotNull BaseViewHolder holder, @NotNull CashMuListEntity result) {
        holder.setText(R.id.tv_date, result.getDate());
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, CashMuListEntity result) {

        CashEntity cashEntity = result.getResult();
        if (cashEntity != null) {

            if (cashEntity.getCashMode() == 1) {
                holder.setText(R.id.tv_tx, cashEntity.getBankType());
            } else if (cashEntity.getCashMode() == 2) {
                holder.setText(R.id.tv_tx, "支付宝");
            } else if (cashEntity.getCashMode() == 3){
                holder.setText(R.id.tv_tx, "微信");
            }

            holder.setText(R.id.tv_time, cashEntity.getTime());
            holder.setText(R.id.tv_money, cashEntity.getCashAccount() + "");
            holder.setText(R.id.tv_zs_num, "-" + cashEntity.getJewelNumber() + "");
            ConstraintLayout consBg = holder.getView(R.id.cons_bg);
            Integer status = cashEntity.getCashStatus();
            TextView mTvStatus = holder.getView(R.id.tv_status);
            TextView mTvLy = holder.getView(R.id.tv_ly);

            if (status == -1) {      //审核中
                consBg.setBackgroundResource(R.drawable.shape_chsh_bg_2);
                mTvStatus.setTextColor(getContext().getResources().getColor(R.color.color_ffcd07));
                mTvStatus.setText("审核中");
                mTvLy.setVisibility(View.GONE);
            } else if (status == 1) {   //成功
                consBg.setBackgroundResource(R.drawable.shape_chsh_bg_1);
                mTvStatus.setTextColor(getContext().getResources().getColor(R.color.color_11ea00));
                mTvStatus.setText("提现成功");
                mTvLy.setVisibility(View.GONE);
            } else if (status == 2) {    //失败
                consBg.setBackgroundResource(R.drawable.shape_chsh_bg_3);
                mTvStatus.setTextColor(getContext().getResources().getColor(R.color.color_ea2c00));
                mTvStatus.setText("提现失败(钻石已退回)");
                if (!TextUtils.isEmpty(result.getResult().getReason())) {
                    mTvLy.setText(result.getResult().getReason());
                    mTvLy.setVisibility(View.VISIBLE);
                } else {
                    mTvLy.setVisibility(View.GONE);
                }
            }
        }
    }
}