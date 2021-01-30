package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;


public class MyCarRecordAdapter extends BaseQuickAdapter<Decks, BaseViewHolder> {
    public MyCarRecordAdapter() {
        super(R.layout.adapter_car_record);
//        super(R.layout.adapter_gift_heard);
//        setNormalLayout(R.layout.adapter_car_record);
    }

//    @Override
//    protected void convertHeader(@NotNull BaseViewHolder holder, @NotNull MyCarTypeResult myCarTypeResult) {
//        holder.setText(R.id.tv_title, myCarTypeResult.typeName);
//        holder.setText(R.id.tv_gift_num, String.valueOf(myCarTypeResult.deckTotal));
//    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, Decks decks) {
        ImageView mIvCar = holder.getView(R.id.iv_car);
        ImageView mIvFlag = holder.getView(R.id.iv_flag);
//        Decks decks = myCarTypeResult.decks;
        GlideAppUtil.loadImage(getContext(), decks.getDeckStaticUrl(), mIvCar);
//        holder.setText(R.id.tv_car_name,decks.getDeckName());
        if (decks.getValidity() == 0) {
            mIvFlag.setVisibility(View.VISIBLE);
        } else if (decks.getValidity() == 1){
            mIvFlag.setVisibility(View.GONE);
        }
    }
}
