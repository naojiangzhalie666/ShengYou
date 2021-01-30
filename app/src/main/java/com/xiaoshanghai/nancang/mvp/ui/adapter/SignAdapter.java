package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.SignRewardEntity;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;

public class SignAdapter extends BaseQuickAdapter<SignRewardEntity, SignAdapter.Holder> {


    public SignAdapter() {
        super(R.layout.adapter_item_sign);
    }

    @Override
    protected void convert(@NotNull Holder holder, SignRewardEntity item) {

        holder.mTvSignDay.setText((getItemPosition(item)+1) + "天");
        if (item.isOpen()) {
            GlideAppUtil.loadImage(getContext(),item.getSignPicture(),holder.mIvSign,R.mipmap.icon_sign_close);
        } else {

            holder.mIvSign.setImageResource(R.mipmap.icon_sign_close);

        }
    }

    static class Holder extends BaseViewHolder {

        public TextView mTvSignDay;
        public ImageView mIvSign;

        public Holder(@NotNull View view) {
            super(view);
            mTvSignDay = view.findViewById(R.id.tv_sign_day);
            mIvSign = view.findViewById(R.id.iv_sign);
        }
    }

}
