package com.xiaoshanghai.nancang.mvp.presenter

import android.text.TextUtils
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.callback.FollowCallback
import com.xiaoshanghai.nancang.callback.RoomUserCallback
import com.xiaoshanghai.nancang.mvp.contract.RoomOnLineContract
import com.xiaoshanghai.nancang.mvp.model.RoomOnLineModel
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.net.bean.OnLineUserResult
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

class RoomOnLinePresenter : BasePresenter<RoomOnLineContract.View>(), RoomOnLineContract.Presenter {

    var initPage = 1
    var mPage = initPage
    private var size = 10

    private val model by lazy { RoomOnLineModel() }

    override fun loadOnLineUser(refresh: RefreshLayout?, roomId: String?) {
        model?.getOnLineUser(mPage.toString() + "", size.toString() + "", roomId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<HomeRoomResult<MutableList<OnLineUserResult>>>() {
                    override fun success(bean: HomeRoomResult<MutableList<OnLineUserResult>>?, response: BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>?) {
                        val records = bean?.records
                        if (records != null) {
                            if (mPage == initPage) {
                                view.refreshSuccess(refresh, records)
                            } else {
                                view.loadMoreSuccess(refresh, records)
                            }
                            if (records.size > 0) mPage++
                        }
                    }

                    override fun error(msg: String?) {
                        if (refresh != null) {
                            refresh.finishRefresh()
                            refresh.finishLoadMore()
                        }
                        view.onError(msg)
                    }

                })
    }

    override fun getUser(userId: String?, roomId: String?, mRoomUser: RoomUserCallback?) {
        model.getRoomUser(userId, roomId!!)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<VoiceRoomSeatEntity.UserInfo>() {

                    override fun success(bean: VoiceRoomSeatEntity.UserInfo?, response: BaseResponse<VoiceRoomSeatEntity.UserInfo>?) {
                        mRoomUser?.onRoomUser(bean)

                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }


                })
    }

    override fun queryFollow(userId: String, callback: FollowCallback?) {
        model.queryFollow(userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        callback?.onFollow(bean!!)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun follow(userId: String?, status: String?) {
        model.follow(userId, status)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        if (TextUtils.isEmpty(bean)) {
                            view.onError("请求失败")
                        } else {
                            if (status == "1") {
                                view.onFollow(bean)
                            } else if (status == "2") {
                                view.onUnFollow(bean)
                            }
                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun addManager(roomId: String?, userId: String?) {
        model.addManager(roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddManager(bean, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })

    }

    override fun removeManager(roomId: String?, userId: String?) {
        model.removeManager(roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onRemoveManager(bean, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


    override fun addBlackList(status: String?, roomId: String?, userId: String?) {
        model.addBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("1", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }


    override fun removeBlackList(status: String?, roomId: String?, userId: String?) {
        model.removeBlackList(status, roomId, userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onAddAndRemoveBlackSuccess("2", bean, roomId, userId)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}