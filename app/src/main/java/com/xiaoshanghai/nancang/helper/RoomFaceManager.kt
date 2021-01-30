package com.xiaoshanghai.nancang.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.DisplayMetrics
import com.xiaoshanghai.nancang.R
import com.xiaoshanghai.nancang.base.BaseApplication
import com.xiaoshanghai.nancang.net.bean.RoomFace
import com.tencent.qcloud.tim.uikit.utils.ScreenUtil
import java.io.IOException
import java.io.InputStream
import java.util.*

class RoomFaceManager {

    companion object {
        private val drawableWidth = ScreenUtil.getPxByDp(32f)
        private val faceList: MutableList<RoomFace> = ArrayList<RoomFace>()
        private val newFaceList: MutableList<RoomFace> = ArrayList<RoomFace>()
        private val vipFaceList: MutableList<RoomFace> = ArrayList<RoomFace>()
        private val context: Context = BaseApplication.getApplication()
        private val faceFilter = context.resources.getStringArray(R.array.face_filter)
        private val newFaceFilterZh = context.resources.getStringArray(R.array.new_face_filter_zh)
        private val newFaceFilteren = context.resources.getStringArray(R.array.new_face_filter_en)
        private val vipFaceFilter = context.resources.getStringArray(R.array.vip_face_filter)

        fun getRoomFace(): MutableList<RoomFace> {
            return faceList
        }

        fun getRoomNewFace():MutableList<RoomFace> {
            return newFaceList
        }

        fun getRoomVipFace(): MutableList<RoomFace> {
            return vipFaceList
        }


        fun loadFaceFiles() {

            object : Thread() {
                override fun run() {
                    for (i in faceFilter.indices) {
                        loadAssetBitmap(faceFilter[i], "face/" + faceFilter[i] + "@2x.png", true, faceFilter[i])
                    }

                    for (i in vipFaceFilter.indices) {
                        loadAssetBitmap(vipFaceFilter[i], "vip_face/" + vipFaceFilter[i] + ".png", false, vipFaceFilter[i])
                    }

                    for (i in newFaceFilteren.indices) {
                        loadAssetBitmapZh(newFaceFilteren[i], "new_face/" + newFaceFilteren[i] + ".png", true, newFaceFilterZh[i])
                    }

                }
            }.start()
        }

        private fun loadAssetBitmap(filter: String, assetPath: String, isFace: Boolean, faceName: String): RoomFace? {
            var inputStream: InputStream? = null

            try {

                var face: RoomFace
                val resources: Resources = context.resources
                val options = BitmapFactory.Options()
                options.inDensity = DisplayMetrics.DENSITY_XXHIGH
                options.inScreenDensity = resources.displayMetrics.densityDpi
                options.inTargetDensity = resources.displayMetrics.densityDpi
                context.assets.list("")
                inputStream = context.assets.open(assetPath)

                val bitmap = BitmapFactory.decodeStream(inputStream, Rect(0, 0, drawableWidth, drawableWidth), options)

                if (bitmap != null) {

                    face = RoomFace(icon = bitmap, filter = filter, isGeneral = isFace, faceName = faceName)
                    if (isFace) {
                        faceList.add(face)
                    } else {
                        vipFaceList.add(face)
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return null
        }

        private fun loadAssetBitmapZh(filter: String, assetPath: String, isFace: Boolean, faceName: String): RoomFace? {
            var inputStream: InputStream? = null

            try {

                var face: RoomFace
                val resources: Resources = context.resources
                val options = BitmapFactory.Options()
                options.inDensity = DisplayMetrics.DENSITY_XXHIGH
                options.inScreenDensity = resources.displayMetrics.densityDpi
                options.inTargetDensity = resources.displayMetrics.densityDpi
                context.assets.list("")
                inputStream = context.assets.open(assetPath)

                val bitmap = BitmapFactory.decodeStream(inputStream, Rect(0, 0, drawableWidth, drawableWidth), options)

                if (bitmap != null) {

                    face = RoomFace(icon = bitmap, filter = filter, isGeneral = isFace, faceName = faceName)
                    newFaceList.add(face)

                }


            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return null
        }

        fun dip2px(mContext: Context, dipValue: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

    }

}