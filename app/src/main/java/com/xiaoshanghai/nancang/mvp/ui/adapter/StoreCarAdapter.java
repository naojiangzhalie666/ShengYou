package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.OnCarTestDive;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.net.bean.Decks;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;

public class StoreCarAdapter extends BaseQuickAdapter<Decks, StoreCarAdapter.Holder> {

    private OnCarTestDive onCarTestDive;

    public StoreCarAdapter() {
        super(R.layout.adapter_store_car);
    }

    public void setOnCarTestDive(OnCarTestDive onCarTestDive) {
        this.onCarTestDive = onCarTestDive;
    }

    @Override
    protected void convert(@NotNull Holder holder, Decks decks) {

        if (decks.isSelect()) {
            holder.mRlCar.setBackgroundResource(R.drawable.shape_car_select_bg);
            holder.mTvCarName.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.mRlCar.setBackgroundResource(R.drawable.shape_car_bg);
            holder.mTvCarName.setTextColor(getContext().getResources().getColor(R.color.color_black));
        }

        GlideAppUtil.loadImage(getContext(), decks.getDeckStaticUrl(), holder.mIvCar);
        holder.mTvStint.setVisibility(decks.getCostType() == 0 ? View.VISIBLE : View.GONE);

        holder.mTvCarName.setText(decks.getDeckName());

        holder.mTvTestSrive.setVisibility(View.VISIBLE);

        holder.mTvTestSrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCarTestDive!=null) {
                    onCarTestDive.onCarTestClick(Constant.CAR_CLICK,decks);
                }
            }
        });


    }

    static class Holder extends BaseViewHolder {

        private RelativeLayout mRlCar;
        private ImageView mIvCar;
        private TextView mTvStint;
        private TextView mTvTestSrive;
        private TextView mTvCarName;

        public Holder(@NotNull View view) {
            super(view);
            mRlCar = view.findViewById(R.id.rl_car);
            mIvCar = view.findViewById(R.id.iv_car);
            mTvStint = view.findViewById(R.id.tv_stint);
            mTvTestSrive = view.findViewById(R.id.tv_test_drive);
            mTvCarName = view.findViewById(R.id.tv_car_name);
        }
    }
}
