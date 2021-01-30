package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseActivity
import com.xiaoshanghai.nancang.callback.ReportViewCallback
import com.xiaoshanghai.nancang.constant.Constant
import com.xiaoshanghai.nancang.mvp.ui.activity.msg.ReportAct
import com.xiaoshanghai.nancang.mvp.ui.activity.square.GraphicReleaseAct
import com.xiaoshanghai.nancang.net.bean.ReportFoot
import com.xiaoshanghai.nancang.utils.PermissionUtil
import com.xiaoshanghai.nancang.utils.PermissionUtil.RequestPermission
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File

class ReportFooterView : RelativeLayout, BGASortableNinePhotoLayout.Delegate {

    @BindView(R.id.snpl_moment_add_photos)
    lateinit var mPhotosSnpl: BGASortableNinePhotoLayout

    @BindView(R.id.bt_commit)
    lateinit var mBtCommit: Button

    @BindView(R.id.et_report)
    lateinit var mEtReport: EditText

    @BindView(R.id.tv_text_num)
    lateinit var mTvTextNum: TextView

    private var mView: View? = null

    private var mContext: Context? = null

    private var mCallback: ReportViewCallback? = null

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    fun setOnReportViewCallback(callback: ReportViewCallback) {
        this.mCallback = callback
    }

    private fun initView(context: Context?) {
        mContext = context
        mView = LayoutInflater.from(context).inflate(R.layout.view_report_foot, this, true)
        ButterKnife.bind(mView!!)
        initBGA(mPhotosSnpl)
    }

    private fun initBGA(mPhotosSnpl: BGASortableNinePhotoLayout) {
        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this)
    }

    override fun onClickNinePhotoItem(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, view: View?, position: Int, model: String?, models: ArrayList<String>?) {
        val photoPickerPreviewIntent = BGAPhotoPickerPreviewActivity.IntentBuilder(mContext)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.maxItemCount) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build()
        (mContext as BaseActivity).startActivityForResult(photoPickerPreviewIntent, GraphicReleaseAct.RC_PHOTO_PREVIEW)
    }

    override fun onClickAddNinePhotoItem(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, view: View?, position: Int, models: ArrayList<String>?) {
        val rxPermissions = RxPermissions((mContext as BaseActivity?)!!)
        PermissionUtil.launchCamera(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                choicePhotoWrapper()
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {}
            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {}
        }, rxPermissions)
    }

    override fun onNinePhotoItemExchanged(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, fromPosition: Int, toPosition: Int, models: ArrayList<String>?) {

    }

    override fun onClickDeleteNinePhotoItem(sortableNinePhotoLayout: BGASortableNinePhotoLayout?, view: View?, position: Int, model: String?, models: ArrayList<String>?) {
        mPhotosSnpl.removeItem(position)
    }

    private fun choicePhotoWrapper() {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        val takePhotoDir = File(Constant.FILE_PATH)
        val photoPickerIntent = BGAPhotoPickerActivity.IntentBuilder(mContext)
                .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(mPhotosSnpl.maxItemCount - mPhotosSnpl.itemCount) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build()
        (mContext as BaseActivity).startActivityForResult(photoPickerIntent, ReportAct.RC_CHOOSE_PHOTO)
    }

    fun addPhoto(photoList: ArrayList<String>) {
        mPhotosSnpl.addMoreData(photoList)

    }

    fun setPhoto(photoList: ArrayList<String>) {
        mPhotosSnpl.data = photoList
    }

    @OnTextChanged(R.id.et_report)
    fun onReportChanged(editable: Editable) {
        mTvTextNum.text = mEtReport.length().toString() + "/140"
    }

    @OnClick(R.id.bt_commit)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_commit -> {

                val data = mPhotosSnpl.data

                var reportResult = ReportFoot(mEtReport.text.toString().trim(),data)

                mCallback?.reportResult(reportResult)

            }
        }
    }
}