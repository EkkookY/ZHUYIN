package com.melody.map.myapplication.repo

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.LocationManager
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import com.amap.api.fence.GeoFence
import com.amap.api.fence.GeoFenceClient
import com.amap.api.location.*
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.example.testzhuyin.R
import com.melody.map.gd_compose.poperties.MapProperties
import com.melody.map.gd_compose.poperties.MapUiSettings

import com.melody.map.myapplication.model.CircleGeoFenceDataModel
import com.melody.map.myapplication.model.GeoFenceDataModel
import com.melody.map.myapplication.model.PolygonGeoFenceDataModel
import com.melody.map.myapplication.utils.SDKUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine

object DetailRepo {
    const val TAG = "DetailRepo"
    //定义接收广播的action字符串
    const val GEOFENCE_BROADCAST_ACTION = "com.melody.geofence.location.broadcast"

    fun checkGPSIsOpen(): Boolean {
        val locationManager: LocationManager? = SDKUtils.getApplicationContext()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)?: false
    }

    fun initMapProperties() : MapProperties {
        // 注意：这里不要用BitmapDescriptorFactory.fromResource(你的新图)，不然会出现不生效的情况
        val iconBitmap = BitmapFactory.decodeResource(SDKUtils.getApplicationContext().resources, R.drawable.ic_map_location_self)
        val locationIcon = BitmapDescriptorFactory.fromBitmap(iconBitmap)
        return MapProperties(
            isMyLocationEnabled = true,
            myLocationStyle = MyLocationStyle().apply {
                // 设置小蓝点的图标
                myLocationIcon(locationIcon)
                // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
                myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
                // 设置圆形的边框颜色
                strokeColor(Color.TRANSPARENT)
                // 设置圆形的填充颜色
                radiusFillColor(Color.TRANSPARENT)
            }
        )
    }

    fun initMapUiSettings(): MapUiSettings {
        return MapUiSettings(
            // 高德地图右上角：显示【定位按钮】
            myLocationButtonEnabled = true,
            isZoomEnabled = true,
            isZoomGesturesEnabled = true,
            isScrollGesturesEnabled = true,
        )
    }

    /**
     * 创建一个地理围栏
     */
    suspend fun initGeoFence(geoFenceClient: GeoFenceClient, latitude: Double, longitude: Double, radius: Float): MutableList<GeoFenceDataModel> {
        return suspendCancellableCoroutine { coroutine ->
            //设置希望侦测的围栏触发行为，默认只侦测用户进入围栏的行为
            //public static final int GEOFENCE_IN 进入地理围栏
            //public static final int GEOFENCE_OUT 退出地理围栏
            //public static final int GEOFENCE_STAYED 停留在地理围栏内10分钟
            geoFenceClient.setActivateAction(
                GeoFenceClient.GEOFENCE_IN
                        or GeoFenceClient.GEOFENCE_OUT
                        or GeoFenceClient.GEOFENCE_STAYED
            )
            //创建一个中心点坐标
            val centerPoint = DPoint()
            //设置中心点纬度
            centerPoint.latitude = latitude
            //设置中心点经度
            centerPoint.longitude = longitude
            //执行添加围栏的操作：业务ID，自己根据业务修改
            geoFenceClient.addGeoFence(centerPoint, radius, "100")
            // 创建并设置PendingIntent
            geoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION)
            // 围栏创建完成，会启动定位，无需我们设置，SDK内部执行
            geoFenceClient.setGeoFenceListener { geoFenceList, errorCode, _ ->
                //判断围栏是否创建成功
                if (errorCode == GeoFence.ADDGEOFENCE_SUCCESS) {
                    Log.d(TAG,">>>>>>添加围栏成功")
                    val customEntitys: MutableMap<String,Any> = mutableMapOf()
                    val geoFenceDataList: MutableList<GeoFenceDataModel> = mutableListOf()
                    for (fence in geoFenceList) {
                        if (!customEntitys.containsKey(fence.fenceId)) {
                            // 判断当前围栏的类型
                            if(fence.type == GeoFence.TYPE_ROUND || fence.type == GeoFence.TYPE_AMAPPOI) {
                                // 添加圆形围栏
                                geoFenceDataList.add(
                                    CircleGeoFenceDataModel(
                                        radius = fence.radius,
                                        point = LatLng(
                                            fence.center.latitude,
                                            fence.center.longitude
                                        )
                                    )
                                )
                                customEntitys[fence.fenceId] = geoFenceDataList
                            } else if(fence.type == GeoFence.TYPE_POLYGON || fence.type == GeoFence.TYPE_DISTRICT) {
                                // 添加多边形围栏
                                val latLngList : MutableList<LatLng> = mutableListOf()
                                fence.pointList.forEach { subList ->
                                    subList.forEach { dPoint ->
                                        latLngList.add(LatLng(dPoint.latitude,dPoint.longitude))
                                    }
                                }
                                geoFenceDataList.add(PolygonGeoFenceDataModel(pointList = latLngList))
                                customEntitys[fence.fenceId] = geoFenceDataList
                            }
                        }
                    }
                    // 添加围栏成功
                    coroutine.resumeWith(Result.success(geoFenceDataList))
                } else {
                    Log.d(TAG,">>>>>>添加围栏失败")
                    // 添加围栏失败
                    coroutine.resumeWith(Result.failure(NullPointerException()))
                }
            }
        }
    }

    /**
     * 处理地理围栏广播接收到的数据
     */
    inline fun handleGeoFenceReceiver(intent: Intent?, onStatus: (Boolean)->Unit) {
        // 解析广播内容，获取Bundle
        val bundle = intent?.extras
        // 获取围栏行为：
        val status = bundle?.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS)
        // 获取自定义的围栏标识：
        val customId = bundle?.getString(GeoFence.BUNDLE_KEY_CUSTOMID)
        // 获取围栏ID
        val fenceId = bundle?.getString(GeoFence.BUNDLE_KEY_FENCEID)
        val code = bundle?.getInt(GeoFence.BUNDLE_KEY_LOCERRORCODE)
        when (status) {
            GeoFence.STATUS_LOCFAIL -> {
                Log.e(TAG, "定位失败$code")
            }
            GeoFence.STATUS_IN -> {
                Log.e(TAG, "进入围栏$fenceId")
            }
            GeoFence.STATUS_OUT -> {
                Log.e(TAG, "离开围栏$fenceId")
            }
            GeoFence.STATUS_STAYED -> {
                Log.e(TAG, "停留在围栏内$fenceId")
            }
            else -> {}
        }
        // STATUS_IN 或者 STATUS_STAYED，表示当前进入围栏，需要播放声音
        onStatus.invoke(status == GeoFence.STATUS_IN || status == GeoFence.STATUS_STAYED)
    }

    /**
     * 初始化LocationClient并启动定位蓝点定位
     */
    inline fun initAMapLocationClient(
        locationClient: AMapLocationClient?,
        listener: AMapLocationListener,
        block: (AMapLocationClient, AMapLocationClientOption) -> Unit
    ) {
        if(null == locationClient) {
            val newLocationClient = AMapLocationClient(SDKUtils.getApplicationContext())
            //初始化定位参数
            val locationClientOption = AMapLocationClientOption()
            //设置定位回调监听
            newLocationClient.setLocationListener(listener)
            //设置为高精度定位模式
            locationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //设置定位参数
            newLocationClient.setLocationOption(locationClientOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            // startLocation() 启动定位
            block.invoke(newLocationClient.apply { startLocation() }, locationClientOption)
        }
    }

    inline fun handleLocationChange(amapLocation: AMapLocation?, block: (AMapLocation?, String?) -> Unit) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                // 显示系统小蓝点
                block.invoke(amapLocation, null)
            } else {
                block.invoke(null, "定位失败," + amapLocation.errorCode + ": " + amapLocation.errorInfo)
            }
        }
    }

    fun initRingtone(block: (Ringtone) -> Unit) {
        val sb = StringBuilder()
        sb.append("android.resource://")
        sb.append(SDKUtils.getApplicationContext().packageName)
        sb.append("/")
        sb.append(R.raw.kitbit_train_perfect)
        val ringtone: Ringtone = RingtoneManager.getRingtone(SDKUtils.getApplicationContext(), Uri.parse(sb.toString()))
        ringtone.audioAttributes =
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).build()
        block.invoke(ringtone)
    }

    suspend fun repeatAction(count: Int, timeMills: Long, block:()-> Unit) {
        repeat(count){
            delay(timeMills)
            block.invoke()
        }
    }
}