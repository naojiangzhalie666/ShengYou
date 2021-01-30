package com.xiaoshanghai.nancang.mvp.ui.fragment.mine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseMvpFragment
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.constant.URLConstant
import com.xiaoshanghai.nancang.mvp.contract.MyUserLvConstract
import com.xiaoshanghai.nancang.mvp.presenter.MyUserLvPresenter
import com.xiaoshanghai.nancang.mvp.ui.activity.mine.WebActivity
import com.xiaoshanghai.nancang.net.bean.GradeResult
import com.xiaoshanghai.nancang.utils.ActStartUtils
import com.xiaoshanghai.nancang.utils.GlideAppUtil
import com.xiaoshanghai.nancang.utils.SPUtils
import com.xiaoshanghai.nancang.utils.ToastUtils

class MyUserLvFragment private constructor() : BaseMvpFragment<MyUserLvPresenter>(), MyUserLvConstract.View {

    @BindView(R.id.iv_avatar)
    lateinit var mIvAvatar: ImageView

    @BindView(R.id.tv_lv_num)
    lateinit var mTvLvNum: TextView

    @BindView(R.id.pb_lv)
    lateinit var mPbLv: ProgressBar

    @BindView(R.id.tv_experience)
    lateinit var mTvExperience: TextView

    @BindView(R.id.tv_distance)
    lateinit var mTvDistance: TextView

    @BindView(R.id.tv_description)
    lateinit var mTvDescription: TextView

//    val gradeUrl = "file:///android_asset/my_grade/grade.html"

    companion object {
        fun newInstance(): MyUserLvFragment {
            return MyUserLvFragment()
        }
    }

    override fun createPresenter(): MyUserLvPresenter = MyUserLvPresenter()

    override fun setLayoutId(): Int = R.layout.fragment_my_user_lv

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        GlideAppUtil.loadImage(activity, SPUtils.getInstance().userInfo.userPicture, mIvAvatar)
        mPresenter.getUserGrade()
    }

    override fun onUserGradeSuccess(result: GradeResult?) {
        if (result == null) return

        mTvLvNum.text = "Lv.${result.level}"

        if (result.rate != null) {
            var rate = result.rate
            var schedule = (rate!! * 100).toInt()
            mPbLv.progress = schedule
        }

        mTvExperience.text = "${result.level}"

        mTvDistance.text = "${result.disparity}"


    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)

    }

    @OnClick(R.id.tv_description)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_description -> {
                var bundle = Bundle()
                bundle.putString(Constant.WEB_URL,URLConstant.GRADE_URL)
                bundle.putString(Constant.WEB_TITLE,"用户等级")
                ActStartUtils.startAct(activity, WebActivity::class.java,bundle)
            }
        }
    }

}