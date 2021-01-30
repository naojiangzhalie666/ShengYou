package com.xiaoshanghai.nancang.mvp.contract

import com.xiaoshanghai.nancang.base.BaseView
import com.xiaoshanghai.nancang.net.HttpObservable
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeSortResult

interface RoomTypeContract {

    interface View : BaseView {

        fun onSortSuccess(sortList: MutableList<HomeSortResult>?)

        fun onTypeSuccess(status: Int, type: HomeSortResult)

        fun onError(msg: String?)

    }

    interface Presenter {


        /**
         * 获取首页房间分类
         * @param indicator
         */
        fun getSort()

        fun setRoomType(roomId: String, roomTypeId: String, type: HomeSortResult)
    }

    interface Model {

        /**
         * 首页分类
         * @return
         */
        fun getHomeSort(): HttpObservable<BaseResponse<MutableList<HomeSortResult>>>

        /**
         * 修改房间属性
         */
        fun modifyRoomType(roomId: String?, typeId: String?): HttpObservable<BaseResponse<Int>>

    }
}