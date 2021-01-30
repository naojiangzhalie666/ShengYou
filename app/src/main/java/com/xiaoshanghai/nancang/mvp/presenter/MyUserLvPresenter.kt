package com.xiaoshanghai.nancang.mvp.presenter

import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.MyUserLvConstract
import com.xiaoshanghai.nancang.mvp.model.MyUserLvModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GradeResult

class MyUserLvPresenter:BasePresenter<MyUserLvConstract.View>(),MyUserLvConstract.Presenter{

    private val model by lazy { MyUserLvModel() }

    override fun getUserGrade() {
        model.getUserGrade()
                .execOnThread(view.getActLifeRecycle(),object:HttpObserver<GradeResult>() {
                    override fun success(bean: GradeResult?, response: BaseResponse<GradeResult>?) {
                        view.onUserGradeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                } )
    }
}