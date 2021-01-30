package com.xiaoshanghai.nancang.mvp.contract;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

import java.util.List;
import java.util.Map;

import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import okhttp3.RequestBody;

public interface GraphiceContract {
    interface View extends BaseView {

        void onSuccess();

        void onError(String msg);
    }

    interface Presenter {

        /**
         * 初始化
         *
         * @param mPhotosSnpl
         */
        void initData(BGASortableNinePhotoLayout mPhotosSnpl);

        /**
         * 发布朋友圈
         * @param content
         * @param files
         */
        void release(String content,List<String> files);


    }

    interface Model {
        HttpObservable<BaseResponse<String>> releaseFriends(String topicContent,Map<String, RequestBody> params);
    }
}
