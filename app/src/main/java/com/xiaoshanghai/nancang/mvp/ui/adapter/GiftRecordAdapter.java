package com.xiaoshanghai.nancang.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.MyGiftTypeResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class GiftRecordAdapter extends BaseSectionQuickAdapter<MyGiftTypeResult, BaseViewHolder> {

    public GiftRecordAdapter() {
        super(R.layout.adapter_gift_heard);
        setNormalLayout(R.layout.adapter_gift);
    }


    @Override
    protected void convertHeader(@NotNull BaseViewHolder holder, @NotNull MyGiftTypeResult myGiftRecordResult) {
        holder.setText(R.id.tv_title,myGiftRecordResult.typeName);
        holder.setText(R.id.tv_gift_num,String.valueOf(myGiftRecordResult.giftTotal));
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, MyGiftTypeResult myGiftRecordResult) {
        CircleImageView mIvFamilyPhto = holder.getView(R.id.iv_gift);
        GlideAppUtil.loadImage(getContext(),myGiftRecordResult.gift.getGiftStaticUrl(),mIvFamilyPhto);
        holder.setText(R.id.tv_gift_name,myGiftRecordResult.gift.getGiftName());
        holder.setText(R.id.tv_gift_num,"x"+myGiftRecordResult.gift.getGetTotal());
    }

}
