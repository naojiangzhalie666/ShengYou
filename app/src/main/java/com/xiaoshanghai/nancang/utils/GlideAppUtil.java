package com.xiaoshanghai.nancang.utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.xiaoshanghai.nancang.callback.GlideCallBack;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.annotations.Nullable;

/**
 * 图片工具类
 */
public class GlideAppUtil {

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param url      图片地址
     * @param view     展示测容器
     */
    public static void loadImage(Context mContext, String url, View view) {
        loadImage(mContext, url, view, -1);
    }

    /**
     * 加载图片
     *
     * @param mContext    上下文
     * @param url         图片地址
     * @param view        展示测容器
     * @param placeHolder 占位图
     */
    public static void loadImage(Context mContext, String url, View view, int placeHolder) {
        GlideRequests with;
        if (mContext == null) return;
        if (mContext instanceof RxAppCompatActivity) {
            with = GlideApp.with((RxAppCompatActivity) mContext);
        } else if (mContext instanceof Activity) {
            with = GlideApp.with((Activity) mContext);
        } else {
            with = GlideApp.with(mContext);
        }
        with
                .load(url)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(placeHolder)
                .into((ImageView) view);
    }

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param url      图片地址
     * @param view     展示测容器
     * @param callBack 回调
     */
    public static void loadImage(Context mContext, String url, View view, final GlideCallBack callBack) {
        if (mContext == null) return;
        GlideRequests with;
        if (mContext instanceof RxAppCompatActivity) {
            with = GlideApp.with((RxAppCompatActivity) mContext);
        } else if (mContext instanceof Activity) {
            with = GlideApp.with((Activity) mContext);
        } else {
            with = GlideApp.with(mContext);
        }
        with
                .asBitmap()
                .load(url)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        callBack.failed();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        callBack.success();
                        return false;
                    }
                })
                .into((ImageView) view);
    }

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param resId    图片地址
     * @param view     展示测容器
     */
    public static void loadImage(Context mContext, int resId, View view) {
        if (mContext == null) return;
        GlideRequests with;
        if (mContext instanceof RxAppCompatActivity) {
            with = GlideApp.with((RxAppCompatActivity) mContext);
        } else if (mContext instanceof Activity) {
            with = GlideApp.with((Activity) mContext);
        } else {
            with = GlideApp.with(mContext);
        }
        with
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) view);
    }

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param url      图片地址
     * @param view     展示测容器
     * @param corner   圆角
     */
    public static void loadCornerImage(Context mContext, String url, View view, int corner) {
        loadCornerImage(mContext, url, view, corner, -1);
    }

    /**
     * 加载图片
     *
     * @param mContext    上下文
     * @param url         图片地址
     * @param view        展示测容器
     * @param corner      圆角
     * @param placeHolder 占位图
     */
    public static void loadCornerImage(Context mContext, String url, View view, int corner, int placeHolder) {
        if (mContext == null) return;
        GlideRequests with;
        if (mContext instanceof RxAppCompatActivity) {
            with = GlideApp.with((RxAppCompatActivity) mContext);
        } else if (mContext instanceof Activity) {
            with = GlideApp.with((Activity) mContext);
        } else {
            with = GlideApp.with(mContext);
        }
        with
                .load(url)
                .placeholder(placeHolder)
                .error(placeHolder)
                .transform(new RoundedCorners(ScreenUtils.dp2px(mContext, corner)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) view);
    }

}
