package com.xiaoshanghai.nancang.mvp.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.constant.Constant

class NobleDialog private constructor() : DialogFragment() {

    var mTitle1: TextView? = null
    var mTitle2: TextView? = null
    var mIvNoble: ImageView? = null
    var mTvSelfNoble: TextView? = null
    var mTvNobleStart: TextView? = null

    private var mSelfNoble: String? = null

    private var mGiftNoble: String? = null

    private var mOnClick: View.OnClickListener? = null

    companion object {
        fun newInstance(selfNoble: String?, giftNoble: String?): NobleDialog {
            var mNobleDialog = NobleDialog()
            var bundle = Bundle()
            bundle.putString(Constant.SELF_NOBLE, selfNoble)
            bundle.putSerializable(Constant.GIFT_NOBLE, giftNoble)
            mNobleDialog.arguments = bundle
            return mNobleDialog
        }
    }

    fun setOnClick(onClick: View.OnClickListener) {
        this.mOnClick = onClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSelfNoble = arguments?.getString(Constant.SELF_NOBLE)
        mGiftNoble = arguments?.getString(Constant.GIFT_NOBLE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context, R.style.TRTCVoiceRoomDialogTheme)
        dialog.setContentView(R.layout.dialog_noble_bg)
        dialog.setCancelable(false)
        initView(dialog)
        return dialog
    }

    private fun initView(dialog: Dialog) {
        mTitle1 = dialog.findViewById<TextView>(R.id.tv_title_1)
        mTitle2 = dialog.findViewById<TextView>(R.id.tv_title_2)
        mIvNoble = dialog.findViewById<ImageView>(R.id.iv_noble)
        mTvSelfNoble = dialog.findViewById<TextView>(R.id.tv_self_noble)
        mTvNobleStart = dialog.findViewById<TextView>(R.id.tv_noble_start)
        if (mOnClick != null) {
            mTvNobleStart?.setOnClickListener(mOnClick)
        }

        setOneTitle(mGiftNoble, mTitle1, mIvNoble)

        setSelfNoble(mSelfNoble, mTvSelfNoble)
    }

    private fun setSelfNoble(mSelfNoble: String?, mTvSelfNoble: TextView?) {
        when (mSelfNoble) {

            "0" -> {
                mTvSelfNoble?.text = "???????????????????"
            }

            "1" -> {
                mTvSelfNoble?.text = "???????????????????"
            }

            "2" -> {
                mTvSelfNoble?.text = "???????????????????"
            }

            "3" -> {
                mTvSelfNoble?.text = "???????????????????"
            }

            "4" -> {
                mTvSelfNoble?.text = "???????????????????"
            }

            "5" -> {
                mTvSelfNoble?.text = "???????????????????"
            }

            "6" -> {
                mTvSelfNoble?.text = "???????????????????"
            }
            "7" -> {
                mTvSelfNoble?.text = "???????????????????"
            }
            else -> {
                mTvSelfNoble?.text = "???????????????????"
            }
        }
    }

    fun setOneTitle(giftNoble: String?, title1: TextView?, ivNoble: ImageView?) {

        when (giftNoble) {

            "0" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_baron)
            }

            "1" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_baron)
            }

            "2" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_viscount)
            }

            "3" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_earl)
            }

            "4" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_marquis)
            }

            "5" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_duke)
            }

            "6" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_king)
            }
            "7" -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_emperor)
            }
            else -> {
                title1?.text = "-???????????????-"
                ivNoble?.setImageResource(R.mipmap.img_baron)
            }
        }
    }

}