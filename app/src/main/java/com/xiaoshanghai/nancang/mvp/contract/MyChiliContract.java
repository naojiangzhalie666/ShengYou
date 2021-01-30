package com.xiaoshanghai.nancang.mvp.contract;

import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.xiaoshanghai.nancang.base.BaseView;
import com.xiaoshanghai.nancang.net.HttpObservable;
import com.xiaoshanghai.nancang.net.bean.BaseResponse;

public interface MyChiliContract {

    interface View extends BaseView{

        /**
         * 获取辣椒总量
         * @param count
         */
        void onChiliSuccess(String count);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);
    }

    interface Presenter {
        /**
         * 获取我的辣椒总量
         */
        void getMyChili();

        void initFragments(ViewPager mViewPager, TextView one, TextView two);
    }

    interface Model {
        /**
         * 获取辣椒
         * @return
         */
        HttpObservable<BaseResponse<Double>> getMyChili();
    }

}
