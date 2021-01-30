package com.xiaoshanghai.nancang.mvp.ui.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.utils.ScreenUtils
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGAParser
import com.opensource.svgaplayer.SVGAVideoEntity
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

class MikeView : RelativeLayout {

    @BindView(R.id.avatar_view)
    lateinit var mImgHead: AvatarView

    @BindView(R.id.iv_off)
    lateinit var mIvOff: ImageView

    @BindView(R.id.tv_gift_value_num)
    lateinit var mTvGiftValueNum: TextView

    @BindView(R.id.ll_gift_value)
    lateinit var mLlGiftValue: LinearLayout

    @BindView(R.id.iv_sex)
    lateinit var mIvSex: ImageView

    @BindView(R.id.svga)
    lateinit var mSvga: SVGAImageView

    @BindView(R.id.tv_name)
    lateinit var mTvName: TextView

    @BindView(R.id.wave_view)
    lateinit var mWaveView: WaveView

    lateinit var mView: View

    lateinit var mContext: Context

    var mTheme: VoiceRoomSeatEntity? = null

    var mGiftValue: Boolean = false

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        mContext = context!!
        mView = LayoutInflater.from(context).inflate(R.layout.view_mike, this, true)
        ButterKnife.bind(mView)
    }

    fun setSeatGiftValue(giftValue: Boolean) {
        this.mGiftValue = giftValue
        if (mTheme != null) {
            mTheme?.userInfo?.giftCoinCount = 0
        }
        setSeatResult(mTheme)
    }

    fun setSeatResult(theme: VoiceRoomSeatEntity?) {
        mTheme = theme

        if (mGiftValue!!) {
            mLlGiftValue.visibility = View.VISIBLE
        } else {
            mLlGiftValue.visibility = View.INVISIBLE
        }

        if (theme == null) return

        //麦位没被锁并无人使用
        if (!mTheme?.isUsed!!) {

            //麦位被锁

            mTvGiftValueNum.text = "0"
            mIvSex.visibility = View.GONE
            //麦位被锁
            if (mTheme!!.isClose) {
                mImgHead.setDefAvatarAndHeadear(R.mipmap.icon_live_default_avatar, 0)
                mTvName.text = "主麦位"
                mTvGiftValueNum.text = "0"
            } else {
                mImgHead.setDefAvatarAndHeadear(R.mipmap.icon_live_tme, 0)
                mTvName.text = "主麦位"
                mTvGiftValueNum.text = "0"
            }


        } else {
            //麦位没被锁但是有人使用了
            mImgHead.setAvatarAndHeadear(mTheme?.userAvatar, if (!TextUtils.isEmpty(mTheme?.userInfo?.headwear)) mTheme?.userInfo?.headwear else "")

            mTvName.text = if (!TextUtils.isEmpty(mTheme?.userName)) mTheme?.userName else "主播名还在查找"

            if (mTheme?.userInfo?.userSex == 0) {
                mIvSex.setImageResource(R.mipmap.icon_gender_female)
                mIvSex.visibility = View.VISIBLE
            } else if (mTheme?.userInfo?.userSex == 1) {
                mIvSex.setImageResource(R.mipmap.icon_gender_male)
                mIvSex.visibility = View.VISIBLE
            }

            mTvGiftValueNum.text = if (mTheme?.userInfo?.giftCoinCount!! > 0) mTheme?.userInfo?.giftCoinCount.toString() else "0"

        }

        if (mTheme?.isMute!!) {

            mIvOff.visibility = View.VISIBLE

        } else {
            mIvOff.visibility = View.GONE

        }

    }

    fun setWave(@ColorRes color: Int) {
        mWaveView?.setColor(ContextCompat.getColor(mContext,color))
        mWaveView?.setMaxRadius(ScreenUtils.dp2px(mContext, 50f).toFloat())
        mWaveView?.start()
    }


    /**
     * 播放表情动画
     */
    fun setSvga(path: String?, svgaParser: SVGAParser) {

        var path = "vip_svga/$path.svga"
        svgaParser.init(mContext)

        svgaParser.decodeFromAssets(path, object : SVGAParser.ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                mSvga?.stopAnimation()
                mSvga?.loops = 1
                mSvga?.clearsAfterStop = true
                mSvga?.setVideoItem(videoItem)
                mSvga?.stepToFrame(0, true)
            }

            override fun onError() {
                com.xiaoshanghai.nancang.utils.ToastUtils.showShort("123")
            }
        })
    }


}