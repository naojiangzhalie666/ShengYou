package com.xiaoshanghai.nancang.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;

@GlideModule
public class AppModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //        设置缓存大小为20mb
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        //        设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        //        根据SD卡是否可用选择是在内部缓存还是SD卡缓存
        if(isSDCardEnable()){
            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, "HYManagerImages", memoryCacheSizeBytes));
        }else {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "HYManagerImages", memoryCacheSizeBytes));
        }
    }

    /**
     * 判断SD卡是否可用
     * @return
     * ture：可用；false：不可用
     */
    public boolean isSDCardEnable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
