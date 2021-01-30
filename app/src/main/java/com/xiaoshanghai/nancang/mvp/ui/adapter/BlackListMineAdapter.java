package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.BlackListMineBean;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class BlackListMineAdapter extends BaseQuickAdapter<BlackListMineBean, BaseViewHolder> {

    public BlackListMineAdapter() {
        super(R.layout.item_blacklist_mine);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, BlackListMineBean item) {
        holder.setText(R.id.tv_name, item.getUserName());
        holder.setText(R.id.tv_id, "ID:" + item.getUserNumber());

        ImageView mIvSex = holder.getView(R.id.iv_sex);
        if (!TextUtils.isEmpty(item.getSex())) {
            if (item.getSex().equals("0")) {
                mIvSex.setImageResource(R.mipmap.icon_gender_female);
            } else if (item.getSex().equals("1")) {
                mIvSex.setImageResource(R.mipmap.icon_gender_male);
            }
        }

        CircleImageView mAvatar = holder.getView(R.id.iv_icon);
        GlideAppUtil.loadImage(getContext(), item.getUserPicture(), mAvatar, R.mipmap.icon_default_avatar);

    }
}
