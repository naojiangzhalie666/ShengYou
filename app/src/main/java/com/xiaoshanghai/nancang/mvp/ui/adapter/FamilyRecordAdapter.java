package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.RecordResult;
import com.xiaoshanghai.nancang.utils.SPUtils;

import org.jetbrains.annotations.NotNull;

public class FamilyRecordAdapter extends BaseQuickAdapter<RecordResult, FamilyRecordAdapter.Holder> {

    public FamilyRecordAdapter() {
        super(R.layout.adapter_family_recofd);
    }

    @Override
    protected void convert(@NotNull Holder holder, RecordResult result) {
        holder.mTvSubmitTime.setText(result.getApplyTime());
        holder.mTvAuditTime.setText(result.getAuditTime());
        holder.mTvApplicant.setText(SPUtils.getInstance().getUserInfo().getUserName());
        int applyStatus = result.getApplyStatus();
        if (applyStatus == 0) {
            holder.mIvStatus.setVisibility(View.INVISIBLE);
            holder.mTvStatus.setTextColor(getContext().getResources().getColor(R.color.color_black));
            holder.mTvStatus.setText(getContext().getResources().getString(R.string.family_under_review));
        } else if (applyStatus == 1) {
            holder.mIvStatus.setImageResource(R.mipmap.icon_family_found_record_success);
            holder.mTvStatus.setTextColor(getContext().getResources().getColor(R.color.color_5ebe48));
            holder.mTvStatus.setText(getContext().getResources().getString(R.string.apply_success));
        } else if (applyStatus == 2) {
            holder.mIvStatus.setImageResource(R.mipmap.icon_family_found_record_fail);
            holder.mTvStatus.setTextColor(getContext().getResources().getColor(R.color.red));
            holder.mTvStatus.setText(getContext().getResources().getString(R.string.apply_fail));
        }

        holder.mTvApplyPrompt.setText(TextUtils.isEmpty(result.getAuditReason()) ? "" : result.getAuditReason());

    }

    static class Holder extends BaseViewHolder {

        private TextView mTvSubmitTime;
        private TextView mTvAuditTime;
        private TextView mTvApplicant;
        private ImageView mIvStatus;
        private TextView mTvStatus;
        private TextView mTvApplyPrompt;

        public Holder(@NotNull View view) {
            super(view);
            mTvSubmitTime = view.findViewById(R.id.tv_submit_time);
            mTvAuditTime = view.findViewById(R.id.tv_audit_time);
            mTvApplicant = view.findViewById(R.id.tv_applicant);
            mIvStatus = view.findViewById(R.id.iv_status);
            mTvStatus = view.findViewById(R.id.tv_status);
            mTvApplyPrompt = view.findViewById(R.id.tv_apply_prompt);
        }
    }
}
