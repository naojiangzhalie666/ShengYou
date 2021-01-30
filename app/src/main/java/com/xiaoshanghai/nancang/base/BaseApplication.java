package com.xiaoshanghai.nancang.base;

import android.app.Application;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.text.TextUtils;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.xiaoshanghai.nancang.BuildConfig;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.constant.Constant;
import com.xiaoshanghai.nancang.constant.SpConstant;
import com.xiaoshanghai.nancang.helper.ConfigHelper;
import com.xiaoshanghai.nancang.helper.RoomFaceManager;
import com.xiaoshanghai.nancang.net.bean.UserBean;
import com.xiaoshanghai.nancang.utils.GlideImageLoader;
import com.xiaoshanghai.nancang.utils.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzy.ninegrid.NineGridView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qcloud.tim.uikit.TUIKit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BaseApplication extends Application {

    public static String HTTP_BASE_URL = BuildConfig.APP_BASE_URL;

    private static Context application;

    private static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initSVAG();
        initWeChat();
        createNormalRefreshHeader();
        NineGridView.setImageLoader(new GlideImageLoader());
        EasyFloat.init(this, false);
        initRoomFace();
        setFaceConfig();
    }

    private void initSVAG() {
        File cacheDir = new File(getApplicationContext().getCacheDir(), "http");
        try {
            HttpResponseCache.install(cacheDir, 1920 * 1200 * 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRoomFace() {
        RoomFaceManager.Companion.loadFaceFiles();
    }

    private void initWeChat() {
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);

        /**
         * TUIKit的初始化函数
         *
         * @param context  应用的上下文，一般为对应应用的ApplicationContext
         * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
         * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
         */
        TUIKit.init(this, Constant.IM_APP_KEY, new ConfigHelper().getConfigs());

        String sig = SPUtils.getInstance().getString(SpConstant.APP_SIG);

        if (!TextUtils.isEmpty(sig) && SPUtils.getInstance().getUserInfo() != null) {
            TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(this);
            UserBean userInfo = SPUtils.getInstance().getUserInfo();
            trtcVoiceRoom.login(Constant.IM_APP_KEY, userInfo.getId(), sig, null);
        }

    }

    private void createNormalRefreshHeader() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent, R.color.color_5e5e5e);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static Context getApplication() {
        return application;
    }

    public static IWXAPI getApi() {
        return api;
    }
    /**
     * 为了android和ios 区分授权，appId=appname_face_android ,其中appname为申请sdk时的应用名
     * 申请License取得的APPID
     * assets目录下License文件名
     * 百度人脸识别初始化
     */
    private void setFaceConfig() {
        FaceSDKManager.getInstance().initialize(this, "xiaoshanghai-face-android", "idl-license.face-android");
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // SDK初始化已经设置完默认参数（推荐参数），您也根据实际需求进行数值调整
        List<LivenessTypeEnum> livenessList = new ArrayList<LivenessTypeEnum>();
        // 根据需求添加活体动作
        livenessList.add(LivenessTypeEnum.Eye);
       /* livenessList.add(LivenessTypeEnum.Mouth);
        livenessList.add(LivenessTypeEnum.HeadUp);
        livenessList.add(LivenessTypeEnum.HeadDown);
        livenessList.add(LivenessTypeEnum.HeadLeft);
        livenessList.add(LivenessTypeEnum.HeadRight);
        livenessList.add(LivenessTypeEnum.HeadLeftOrRight);*/
        config.setLivenessTypeList(livenessList);
        config.setBlurnessValue(FaceEnvironment.VALUE_BLURNESS);
        config.setBrightnessValue(FaceEnvironment.VALUE_BRIGHTNESS);
        config.setCropFaceValue(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        config.setHeadPitchValue(FaceEnvironment.VALUE_HEAD_PITCH);
        config.setHeadRollValue(FaceEnvironment.VALUE_HEAD_ROLL);
        config.setHeadYawValue(FaceEnvironment.VALUE_HEAD_YAW);
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        config.setOcclusionValue(FaceEnvironment.VALUE_OCCLUSION);
        config.setCheckFaceQuality(true);
        config.setFaceDecodeNumberOfThreads(2);
        FaceSDKManager.getInstance().setFaceConfig(config);
    }


}
