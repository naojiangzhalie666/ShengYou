package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.HomeSeachEntity;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class SeachAdapter extends BaseQuickAdapter<HomeSeachEntity, SeachAdapter.Holder> {

    public SeachAdapter() {
        super(R.layout.adapter_item_seach);
    }

    @Override
    protected void convert(@NotNull Holder holder, HomeSeachEntity item) {
        GlideAppUtil.loadImage(getContext(),item.getUserPicture(),holder.mCivAvatar,R.mipmap.icon_default_avatar);
        holder.mTvName.setText(item.getUserName());
        if (item.getUserSex() == 0) {
            holder.mSexView.setImageResource(R.mipmap.icon_gender_female);
        } else if (item.getUserSex() == 1) {
            holder.mSexView.setImageResource(R.mipmap.icon_gender_male);
        }
        holder.mTvIdNum.setText(item.getUserNumber().toString());

    }

    static class Holder extends BaseViewHolder {

        public CircleImageView mCivAvatar;
        public TextView mTvName;
        public ImageView mSexView;
        public TextView mTvIdNum;

        public Holder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mTvName = view.findViewById(R.id.tv_name);
            mSexView = view.findViewById(R.id.sex_view);
            mTvIdNum = view.findViewById(R.id.tv_id_num);

        }
    }
}
