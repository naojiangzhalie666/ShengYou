package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.FeedBackContract
import com.xiaoshanghai.nancang.mvp.model.FeedBackModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse

class FeedBackPresenter:BasePresenter<FeedBackContract.View>() ,FeedBackContract.Presenter{
    private val model by lazy { FeedBackModel() }

    override fun feedBack(message: String, type: String, num: String, userId: String) {
        view.showLoading()
        model.feedBack(message,type,num,userId)?.execOnThread(view.getActLifeRecycle(),object : HttpObserver<Int>() {
            override fun success(bean: Int?, response: BaseResponse<Int>?) {
                 view.hideLoading()
                if (bean!=null){
                    if (bean==1){
                        view.feedBackSuccess()
                    }else{
                        view.feedBackError("反馈提交失败")
                    }
                }
            }

            override fun error(msg: String?) {
                view.hideLoading()
                if (msg != null) {
                    view.feedBackError(msg)
                }
            }

        })
    }


}