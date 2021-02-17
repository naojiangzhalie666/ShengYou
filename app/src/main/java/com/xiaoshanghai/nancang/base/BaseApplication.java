package com.xiaoshanghai.nancang.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.xiaoshanghai.nancang.BuildConfig;
import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.constant.Const;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BaseApplication extends Application {
    public static String city="南昌市";
    public static String  latitude="30.49347";
    public static String longitude="114.410726";
    public static String HTTP_BASE_URL = BuildConfig.APP_BASE_URL;
    public static boolean sexStatus=false;
    private static Context application;

    private static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //打开第三方推送
        XGPushConfig.enableDebug(this,true);
        XGPushConfig.enablePullUpOtherApp(this,false);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761518945412");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "5211894513412");
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.e("aa", "-----注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.e("aa", "-------注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        initSVAG();
        initWeChat();
        createNormalRefreshHeader();
        NineGridView.setImageLoader(new GlideImageLoader());
        EasyFloat.init(this, false);
        initRoomFace();
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

    public static List<LivenessTypeEnum> livenessList = new ArrayList<>();
    // 活体随机开关
    public static boolean isLivenessRandom = true;
    // 语音播报开关
    public static boolean isOpenSound = true;
    // 活体检测开关
    public static boolean isActionLive = true;
    // 质量等级（0：正常、1：宽松、2：严格、3：自定义）
    public static int qualityLevel = Const.QUALITY_NORMAL;

    private static Map<String, Activity> destroyMap = new HashMap<>();
    /**
     * 添加到销毁队列
     * @param activity 要销毁的activity
     */
    public static void addDestroyActivity(Activity activity, String activityName) {
        destroyMap.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destroyActivity(String activityName) {
        Set<String> keySet = destroyMap.keySet();
        for (String key : keySet) {
            destroyMap.get(key).finish();
        }
    }
}
