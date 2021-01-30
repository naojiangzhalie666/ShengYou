package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.SeachRoomEntty;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class EnterRoomRecordAdapter extends BaseQuickAdapter<SeachRoomEntty, EnterRoomRecordAdapter.Holder> {

    public EnterRoomRecordAdapter() {
        super(R.layout.adapter_item_enter_room_record);

    }

    @Override
    protected void convert(@NotNull Holder holder, SeachRoomEntty item) {
        GlideAppUtil.loadImage(getContext(),item.getUserPictureUrl(),holder.mCivAvatar,R.mipmap.icon_default_avatar);
        if (item.getRoomStatus().equals("1")) {
            holder.mIvFlag.setImageResource(R.mipmap.icon_search_live);
        } else {
            holder.mIvFlag.setImageResource(R.mipmap.icon_search_live_off);
        }

        holder.mTvName.setText(item.getUserName());


    }

    static class Holder extends BaseViewHolder {

        public CircleImageView mCivAvatar;
        public ImageView mIvFlag;
        public TextView mTvName;

        public Holder(@NotNull View view) {
            super(view);
            mCivAvatar = view.findViewById(R.id.civ_avatar);
            mIvFlag = view.findViewById(R.id.iv_flag);
            mTvName = view.findViewById(R.id.tv_name);

        }
    }
}
