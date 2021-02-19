package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.callback.GuideCallback;
import com.xiaoshanghai.nancang.net.bean.GuidBean;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.youth.banner.adapter.BannerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GuideAdapter extends BannerAdapter<GuidBean, GuideAdapter.Holder> {

    private Context mContext;

    private GuideCallback mCallback;

    public GuideAdapter(Context context, List<GuidBean> datas, GuideCallback callback) {
        super(datas);
        this.mContext = context;
        this.mCallback = callback;
    }

    @Override
    public Holder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_item_guide, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindView(Holder holder, GuidBean data, int position, int size) {
        GlideAppUtil.loadImage(mContext, data.getGuidaImage(), holder.mIvBg);

        holder.mClickView.setOnClickListener(view -> {
           if (data.getIndex() == 3){
               if (mCallback!=null) {
                   mCallback.onEnterClick();
               }
           }
        });


    }

    static class Holder extends BaseViewHolder {
        public ImageView mIvBg;
        public View mClickView;

        public Holder(@NotNull View view) {
            super(view);
            mIvBg = view.findViewById(R.id.iv_bg);
            mClickView =  view.findViewById(R.id.view_click);
        }
    }
}
