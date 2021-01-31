package com.xiaoshanghai.nancang.utils

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener


/**
 * 地图工具类
 */
object  AMapLocationTools {
    //声明AMapLocationClient类对象
    var mLocationClient: AMapLocationClient? = null
    var mLocationOption: AMapLocationClientOption? = null
    fun startLocation(mContext: Context, mLocationListener: AMapLocationListener){
//初始化AMapLocationClientOption对象
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption()
//设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
//获取一次定位结果：
//该方法默认为false。
        mLocationOption?.isOnceLocation = true
//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption?.isOnceLocationLatest = true
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption?.isMockEnable = true;
        mLocationClient?.setLocationOption(mLocationOption)
        //初始化定位
        mLocationClient =  AMapLocationClient(mContext)
        //设置定位回调监听
        mLocationClient?.setLocationListener(mLocationListener)
        //启动定位
        mLocationClient?.startLocation()
    }
   fun stopLocation(){
       mLocationClient?.stopLocation()

   }

}