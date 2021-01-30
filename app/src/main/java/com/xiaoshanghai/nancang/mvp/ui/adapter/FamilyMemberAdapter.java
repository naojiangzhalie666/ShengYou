package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.mvp.ui.view.CharmLevelView;
import com.xiaoshanghai.nancang.mvp.ui.view.UserLevelView;
import com.xiaoshanghai.nancang.net.bean.ClanResult;
import com.xiaoshanghai.nancang.net.bean.FamilyMemberResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;

import org.jetbrains.annotations.NotNull;

public class FamilyMemberAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, FamilyMemberAdapter.Holder> {

    public FamilyMemberAdapter() {
        addItemType(0, R.layout.adapter_family_member_patriarch);
        addItemType(1, R.layout.adapter_family_members);
    }

    @Override
    protected void convert(@NotNull Holder holder, MultiItemEntity result) {
        switch (holder.getItemViewType()) {
            case 1: //非家族成员
                setMember((FamilyMemberResult) result, holder);
                break;
            case 0: //家族成员
                String clanName = ((ClanResult) result).getClanName();
                GlideAppUtil.loadImage(getContext(), ((ClanResult) result).getUserPicture(), holder.getView(R.id.civ_user_avater));
                holder.setText(R.id.tv_nick_name, clanName);
                break;
        }

    }

    private void setMember(FamilyMemberResult result, Holder holder) {
        GlideAppUtil.loadImage(getContext(), result.getUserPicture(), holder.getView(R.id.civ_avatar));
        TextView tvPatriarchTag = holder.getView(R.id.tv_patriarch_tag);
        if (!TextUtils.isEmpty(result.getIsPatriarch())) {
            if (result.getIsPatriarch().equals("1")) {
                tvPatriarchTag.setVisibility(View.VISIBLE);
            }
        } else {
            tvPatriarchTag.setVisibility(View.GONE);
        }

        holder.setText(R.id.tv_nick_name, result.getUserName());
        UserLevelView userLevel = holder.getView(R.id.ul_user_lv);
        userLevel.setUserLevel(result.getUserLevel());
        CharmLevelView charmLevel = holder.getView(R.id.clv_charm_lv);
        charmLevel.setCharmLevel(result.getCharmLevel());
        holder.setText(R.id.tv_user_id, result.getUserNumber() + "");
    }

    static class Holder extends BaseViewHolder {

        public Holder(@NotNull View view) {
            super(view);
        }
    }
}
