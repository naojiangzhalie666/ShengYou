package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.FamilyRankResult;
import com.xiaoshanghai.nancang.mvp.ui.adapter.FamilyRankAdapter.Holder;
import com.xiaoshanghai.nancang.utils.GlideAppUtil;
import com.xiaoshanghai.nancang.utils.NumberUtils;
import com.xiaoshanghai.nancang.view.CircleImageView;

import org.jetbrains.annotations.NotNull;

public class FamilyRankAdapter extends BaseQuickAdapter<FamilyRankResult, Holder> {

    public FamilyRankAdapter() {
        super(R.layout.adapter_family_rank);
    }

    @Override
    protected void convert(@NotNull Holder holder, FamilyRankResult result) {

        int itemPosition = getItemPosition(result);
        GlideAppUtil.loadImage(getContext(), result.getClanPicture(), holder.mIvLogo);
        holder.mTvFamilyName.setText(result.getClanName());
        holder.mTvFamilyId.setText(result.getClanNumber());

        String coins = "";
        if (itemPosition != 0) {
            FamilyRankResult item = getItem(itemPosition - 1);
            double coins1 = item.getCoins();
            double coins2 = result.getCoins();
            coins = NumberUtils.NumberToStr((coins1 - coins2) + "");
        }


        setRank(itemPosition, holder.mIvNum, holder.mTvNum, holder.mTvUpFlag, holder.mTvCoins, coins);
    }


    /**
     * @param position  名次
     * @param imageView 名次头像 第 1 2 3 名显示名次头像
     * @param textView  名次文字 第 4 及以后名次显示名次文字
     * @param flag      显示文字 “距上一名” 第 2 名及以后显示
     * @param tvConins  显示距离上一名次金币数量 第 2 名及以后显示 第 1 名显示 NO.1
     * @param coins     金币
     */
    private void setRank(int position, ImageView imageView, TextView textView, TextView flag, TextView tvConins, String coins) {

        if (position == 0) {

            imageView.setImageResource(R.mipmap.icon_family_square_first);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            flag.setVisibility(View.GONE);
            tvConins.setText("No.1");

        } else if (position == 1) {

            imageView.setImageResource(R.mipmap.icon_family_square_second);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            flag.setVisibility(View.VISIBLE);
            tvConins.setText(coins);

        } else if (position == 2) {
            imageView.setImageResource(R.mipmap.icon_family_square_third);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            flag.setVisibility(View.VISIBLE);
            tvConins.setText(coins);
        } else {
            textView.setText(position + 1 + "");
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            flag.setVisibility(View.VISIBLE);
            tvConins.setText(coins);
        }
    }

    static class Holder extends BaseViewHolder {

        ImageView mIvNum;           // 第 1 2 3 名显示头像
        TextView mTvNum;            //第4名及以后 显示名次
        CircleImageView mIvLogo;    //家族logo
        TextView mTvFamilyName;     //家族名称
        TextView mTvFamilyId;       //家族ID
        TextView mTvUpFlag;         //显示文字 "距上一名" 2 名及以后显示
        TextView mTvCoins;          //显示距离上一名还差多少金币 第一名显示NO.1 第二名及以后显示距离上一名次还差多少金币

        public Holder(@NotNull View view) {
            super(view);
            mIvNum = view.findViewById(R.id.iv_num);
            mTvNum = view.findViewById(R.id.tv_num);
            mIvLogo = view.findViewById(R.id.iv_family_logo);
            mTvFamilyName = view.findViewById(R.id.tv_family_name);
            mTvFamilyId = view.findViewById(R.id.tv_family_id);
            mTvUpFlag = view.findViewById(R.id.tv_up_flag);
            mTvCoins = view.findViewById(R.id.tv_coins);
        }
    }
}
