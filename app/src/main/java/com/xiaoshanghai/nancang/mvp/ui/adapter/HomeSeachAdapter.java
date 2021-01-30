package com.xiaoshanghai.nancang.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;

import org.jetbrains.annotations.NotNull;

public class HomeSeachAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeSeachAdapter() {
        super(R.layout.adapter_item_home_seach);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, String s) {
        holder.setText(R.id.tv_seach_record,s);
    }
}
