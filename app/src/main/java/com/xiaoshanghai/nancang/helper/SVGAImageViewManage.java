package com.xiaoshanghai.nancang.helper;

import android.content.Context;
import android.text.TextUtils;

import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SVGAImageViewManage implements SVGAParser.ParseCompletion, SVGACallback {

    private Context mContext;

    private boolean mRoomSpecial = true;

    private boolean isPlay = false; //当前动画是否在播放

    private ArrayList<String> mSVGAUrl = new ArrayList<>();//需要播放的动画列表

//    private SVGAParser svgaParser = SVGAParser.Companion.shareParser();//播放工具

    private SVGAImageView mAnimationView;

    public SVGAImageViewManage(Context context, boolean isSpecial, SVGAImageView animationView) {
        this.mAnimationView = animationView;
        this.mContext = context;
        this.mRoomSpecial = isSpecial;
        mAnimationView.setCallback(this);
//        mAnimationView.setLoops(1);
//        svgaParser.init(mContext);
//        SVGAParser.Companion.shareParser().init(mContext);
    }

    public void setRoomsPecial(boolean isSpecial) {
        this.mRoomSpecial = isSpecial;
    }

    public void addAnimation(String animationUrl) {
        if (TextUtils.isEmpty(animationUrl)) return;
        if (mRoomSpecial) {
            if (mSVGAUrl.size() < 100) {
                mSVGAUrl.add(animationUrl);
            }
            if (!isPlay) {
                if (mSVGAUrl.size() > 0) {
                    startAnimation(mSVGAUrl.get(0));
                }
            }
        }
    }

    private void startAnimation(String url) {

        if (mRoomSpecial) {
            try {
                isPlay = true;
                SVGAParser svgaParser = SVGAParser.Companion.shareParser();
                svgaParser.init(mContext);
                svgaParser.decodeFromURL(new URL(url), this);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                mSVGAUrl.remove(0);
                if (mSVGAUrl.size() > 0) {
                    startAnimation(mSVGAUrl.get(0));
                }
            }
        } else {
            if (mSVGAUrl.size() > 0) {
                mSVGAUrl.clear();
            }
        }
    }


    @Override
    public void onComplete(@NotNull SVGAVideoEntity videoItem) {
        mAnimationView.setVideoItem(videoItem);
        mAnimationView.startAnimation();
        if (mSVGAUrl.size() > 0) {
            mSVGAUrl.remove(0);
        }

    }

    @Override
    public void onError() {

        if (mSVGAUrl.size()>0) {
            mSVGAUrl.remove(0);
        }

        isPlay = false;
        if (mSVGAUrl.size() > 0) {
            startAnimation(mSVGAUrl.get(0));
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onFinished() {
        isPlay = false;
        if (mSVGAUrl.size() > 0) {
            startAnimation(mSVGAUrl.get(0));
        }

    }

    @Override
    public void onRepeat() {

    }

    @Override
    public void onStep(int frame, double percentage) {

    }
}
