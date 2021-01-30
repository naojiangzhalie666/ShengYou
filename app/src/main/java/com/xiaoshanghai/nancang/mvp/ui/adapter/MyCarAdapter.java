package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;

public class MyCarAdapter extends BaseQuickAdapter<Decks, MyCarAdapter.Holder> {

    public MyCarAdapter() {
        super(R.layout.adapter_my_car);
    }

    @Override
    protected void convert(@NotNull Holder holder, Decks decks) {
        GlideAppUtil.loadImage(getContext(), decks.getDeckStaticUrl(), holder.mIvCar);
        holder.mTvCarName.setText(decks.getDeckName());

        boolean isExpired = decks.getValidity() == 0;      //是否过期
        boolean isUse = decks.getUsed() == 1;          //是否使用

        if (isExpired) {
            holder.mIvFlag.setImageResource(R.drawable.img_my_car_expire);
        } else {
            if (isUse) {
                holder.mIvFlag.setImageResource(R.drawable.img_syz);
            } else {
                holder.mIvFlag.setImageResource(R.drawable.img_sy);
            }
        }

        holder.mTvStint.setVisibility(decks.getCostType() == 0 ? View.VISIBLE : View.GONE);

    }

    static class Holder extends BaseViewHolder {

        private ImageView mIvCar;
        private ImageView mIvFlag;
        private TextView mTvStint;
        private TextView mTvCarName;

        public Holder(@NotNull View view) {
            super(view);

            mIvCar = view.findViewById(R.id.iv_car);
            mIvFlag = view.findViewById(R.id.iv_flag);
            mTvStint = view.findViewById(R.id.tv_stint);
            mTvCarName = view.findViewById(R.id.tv_car_name);
        }
    }
}
