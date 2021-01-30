package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.BannerResult;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class HomeBannerAdapter extends BannerAdapter<BannerResult, HomeBannerAdapter.Holder> {

    private Context mContext;

    public HomeBannerAdapter(Context context, List<BannerResult> datas) {
        super(datas);
        this.mContext = context;
    }

    @Override
    public Holder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_home_banner, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindView(Holder holder, BannerResult data, int position, int size) {
        GlideAppUtil.loadImage(mContext, data.getPictureUrl(), holder.mIvBanner);

    }

    static class Holder extends BaseViewHolder {

        private ImageView mIvBanner;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mIvBanner = itemView.findViewById(R.id.iv_banner);
        }
    }
}
