package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.MyCharmLvConstract
import com.xiaoshanghai.nancang.mvp.model.MyCharmLvModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GradeResult

class MyCharmLvPresenter:BasePresenter<MyCharmLvConstract.View>(),MyCharmLvConstract.Presenter{

    private val mode by lazy { MyCharmLvModel() }

    override fun getCharmGradeUserGrade() {
        mode.getCharmGrade()
                .execOnThread(view.getActLifeRecycle(),object: HttpObserver<GradeResult>() {
                    override fun success(bean: GradeResult?, response: BaseResponse<GradeResult>?) {
                        view.onCharmGradeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                } )
    }

}