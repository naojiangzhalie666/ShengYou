package com.xiaoshanghai.nancang.mvp.presenter

import android.R
import android.graphics.Color
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.xiaoshanghai.nancang.base.BasePresenter
import com.xiaoshanghai.nancang.mvp.contract.ChiliGiftOutlayContract
import com.xiaoshanghai.nancang.mvp.model.ChiliGiftOutlayModel
import com.xiaoshanghai.nancang.mvp.ui.fragment.mine.ChiliGiftOutlayFragment
import com.xiaoshanghai.nancang.net.HttpObserver
import com.xiaoshanghai.nancang.net.bean.BaseResponse
import com.xiaoshanghai.nancang.net.bean.GoldGiftResult
import com.xiaoshanghai.nancang.net.bean.HomeRoomResult
import com.xiaoshanghai.nancang.utils.DateUtil
import com.scwang.smartrefresh.layout.api.RefreshLayout
import java.util.*

class ChiliGiftOutlayPresenter : BasePresenter<ChiliGiftOutlayContract.View>(),ChiliGiftOutlayContract.Presenter {

    var initPage = 1
    var mPage = initPage
    var size = 10

    val model: ChiliGiftOutlayModel by lazy { ChiliGiftOutlayModel() }



    override fun selectTime(date: String?) {
        val calendar = Calendar.getInstance()
        calendar.time = DateUtil.stringToDate(date, DateUtil.DatePattern.ONLY_DAY)
        val pvTime = TimePickerBuilder((view as ChiliGiftOutlayFragment).context, OnTimeSelectListener { date, v -> //选中事件回调
            val dateToString = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY)
            view.setTime(dateToString)
        })
                .setType(booleanArrayOf(true, true, true, false, false, false)) // 默认全部显示
                .setCancelText("取消") //取消按钮文字
                .setSubmitText("确定") //确认按钮文字
                //                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20) //标题文字大小
                .setTitleText("日期选择") //标题文字
                .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true) //是否循环滚动
                .setTitleColor(Color.BLACK) //标题文字颜色
                .setSubmitColor(Color.parseColor("#000000")) //确定按钮文字颜色
                .setCancelColor(Color.parseColor("#000000")) //取消按钮文字颜色
                .setTitleBgColor(Color.WHITE) //标题背景颜色 Night mode
                .setBgColor(Color.WHITE) //滚轮背景颜色 Night mode
                .setDate(calendar) // 如果不设置的话，默认是系统时间*/
                .setLabel("年", "月", "日", "时", "分", "秒") //默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false) //是否显示为对话框样式
                .setDecorView((view as ChiliGiftOutlayFragment).activity!!.window.decorView.findViewById(R.id.content))
                .build()
        pvTime.show()
    }

    override fun getChiliGiftOutlayList(refresh: RefreshLayout?, date: String?) {
        val replace = date?.replace("-", "/")

        model!!.getChiliGiftOutlayList(mPage.toString() + "", size.toString() + "", replace)
                ?.execOnThread(view.getActLifeRecycle(), object : HttpObserver<HomeRoomResult<List<GoldGiftResult?>?>?>() {


                    override fun success(bean: HomeRoomResult<List<GoldGiftResult?>?>?, response: BaseResponse<HomeRoomResult<List<GoldGiftResult?>?>?>?) {
                        val records = bean?.records
                        //                        if (records != null) {
                        if (mPage == initPage) {
                            view.refreshSuccess(refresh, records)
                        } else {
                            view.loadMoreSuccess(refresh, records)
                        }
                        if (records?.size!! > 0) mPage++
                        //                        }
                    }

                    override fun error(msg: String) {
                        if (refresh != null) {
                            refresh.finishRefresh()
                            refresh.finishLoadMore()
                        }
                        view.onError(msg)
                    }


                })

    }

}