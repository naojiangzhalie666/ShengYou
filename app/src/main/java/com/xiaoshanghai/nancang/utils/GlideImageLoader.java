package com.xiaoshanghai.nancang.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.xiaoshanghai.nancang.R;
import com.lzy.ninegrid.NineGridView;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideImageLoader implements NineGridView.ImageLoader {
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        RequestOptions options = bitmapTransform(new CenterCropRoundCornerTransform(ScreenUtils.dp2px(context, 10)));
        GlideApp.with(context)
                .load(url)
                .apply(options)
                .placeholder(R.drawable.shape_default_img_bg)
                .into(imageView);

////            .with(context)
//                .load(url)
//                .apply(options)
//                .placeholder(R.drawable.shape_default_img_bg)
//                .skipMemoryCache(false)
//                .thumbnail(0.5f)
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .dontAnimate()
//                .into(imageView);



    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
