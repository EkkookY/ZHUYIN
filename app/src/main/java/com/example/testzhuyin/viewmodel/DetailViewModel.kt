package com.melody.map.myapplication.viewmodel

import android.content.Intent
import android.media.Ringtone
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.amap.api.fence.GeoFenceClient
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.LocationSource
import com.amap.api.maps.LocationSource.OnLocationChangedListener
import com.melody.map.myapplication.base.BaseViewModel
import com.melody.map.myapplication.contract.DetailContract
import com.melody.map.myapplication.repo.DetailRepo
import com.melody.map.myapplication.utils.SDKUtils
import com.melody.map.myapplication.utils.openAppPermissionDetailsPage
import com.melody.map.myapplication.utils.safeLaunch
import kotlinx.coroutines.Dispatchers

class DetailViewModel: BaseViewModel<DetailContract.Event,DetailContract.State,DetailContract.Effect>(),
    LocationSource, AMapLocationListener {
    //实例化地理围栏客户端
    private val geoFenceClient = GeoFenceClient(SDKUtils.getApplicationContext())
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var mListener: OnLocationChangedListener? = null
    private var mRingtone: Ringtone? = null

    override fun createInitialState(): DetailContract.State {
        return DetailContract.State(
            mapProperties = DetailRepo.initMapProperties(),
            mapUiSettings = DetailRepo.initMapUiSettings(),
            isInitGeoFence = false,
            isShowOpenGPSDialog = false,
            grantLocationPermission = false,
            isOpenGps = null,
            geoFenceDrawList = mutableListOf()
        )
    }

    override fun handleEvents(event: DetailContract.Event) {
        when(event) {
            is DetailContract.Event.ShowOpenGPSDialog -> {
                setState { copy(isShowOpenGPSDialog = true) }
            }
            is DetailContract.Event.HideOpenGPSDialog -> {
                setState { copy(isShowOpenGPSDialog = false) }
            }
            is DetailContract.Event.HandleGeoFenceReceiver -> {
                DetailRepo.handleGeoFenceReceiver(event.intent) {
                    if(it) {
                        asyncLaunch {
                            DetailRepo.repeatAction(3,1000) {
                                mRingtone?.play()
                            }
                        }
                    }
                }
            }
        }
    }

    init {
        DetailRepo.initRingtone {
            mRingtone = it
        }
    }

    /**
     * 初始化地理围栏
     */
    fun initGeoFence(latitude: Double, longitude: Double) = asyncLaunch(Dispatchers.IO) {
        if(currentState.isInitGeoFence) return@asyncLaunch
        setState { copy(isInitGeoFence = true) }
        startMapLocation()
        val result = kotlin.runCatching {
            DetailRepo.initGeoFence(geoFenceClient, latitude, longitude, 1000F)
        }
        if(result.isSuccess) {
            Log.d("TAG",">>>>>>创建成功，size:"+result.getOrNull()?.size)
            setState { copy(geoFenceDrawList = result.getOrNull()?: mutableListOf()) }
        } else {
            Log.d("TAG",">>>>>>创建失败！！！！")
            setEffect {
                DetailContract.Effect.Toast("地理围栏创建失败，请退出页面重试！")
            }
        }
    }

    /**
     * 检查系统GPS开关是否打开
     */
    fun checkGpsStatus() = asyncLaunch(Dispatchers.IO) {
        val isOpenGps = DetailRepo.checkGPSIsOpen()
        setState { copy(isOpenGps = isOpenGps) }
        if(!isOpenGps) {
            setEvent(DetailContract.Event.ShowOpenGPSDialog)
        } else {
            hideOpenGPSDialog()
        }
    }

    /**
     * 隐藏GPS授权Dialog
     */
    fun hideOpenGPSDialog() {
        setEvent(DetailContract.Event.HideOpenGPSDialog)
    }

    /**
     * 手机开了GPS，app没有授予权限
     */
    fun handleNoGrantLocationPermission() {
        setState { copy(grantLocationPermission = false) }
        setEvent(DetailContract.Event.ShowOpenGPSDialog)
    }

    /**
     * 处理定位权限
     */
    fun handleGrantLocationPermission() {
        setState { copy(grantLocationPermission = true) }
        checkGpsStatus()
    }

    /**
     * 没有主动授权，需要跳转到GPS权限授权系统页面
     */
    fun openSysGPSPage(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        if(DetailRepo.checkGPSIsOpen()) {
            // 已打开系统GPS，APP还没授权，跳权限页面
            openAppPermissionDetailsPage()
        } else {
            // 打开系统GPS开关页面
            launcher.safeLaunch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    /**
     * 处理地理围栏广播接收到的数据
     */
    fun handleGeoFenceReceiver(intent: Intent?) {
        setEvent(DetailContract.Event.HandleGeoFenceReceiver(intent))
    }

    /**
     * 仅仅只是为了展示当前用户的位置，如果不需要，可以删除mLocationClient相关代码，地理围栏内部实现了定位，只是没有定位蓝点
     */
    private fun startMapLocation() {
        DetailRepo.initAMapLocationClient(mLocationClient,this) { client, option->
            mLocationClient = client
            mLocationOption = option
        }
    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        DetailRepo.handleLocationChange(amapLocation) { aMapLocation, msg ->
            if(null != aMapLocation) {
                /*if(null == currentState.showGFAndLocationBounds) {
                    // 这里是为了保证用户的位置和覆盖物的位置能都在视野范围内显示
                    setState {
                        copy(
                            showGFAndLocationBounds = LatLngBounds(
                                LatLng(currentState.gfCenterLatitude, currentState.gfCenterLongitude),
                                LatLng(aMapLocation.latitude, aMapLocation.longitude)
                            )
                        )
                    }
                }*/
                // 显示系统小蓝点
                mListener?.onLocationChanged(aMapLocation)
            } else {
                setEffect { DetailContract.Effect.Toast(msg) }
            }
        }
    }

    override fun activate(listener: OnLocationChangedListener?) {
        mListener = listener
        if(DetailRepo.checkGPSIsOpen() && currentState.grantLocationPermission) {
            startMapLocation()
        }
    }

    override fun deactivate() {
        mListener = null
        mLocationClient?.stopLocation()
        mLocationClient?.onDestroy()
        mLocationClient = null
    }

    override fun onCleared() {
        kotlin.runCatching {
            mRingtone?.stop()
            mRingtone = null
        }
        deactivate()
        geoFenceClient.setGeoFenceListener(null)
        // 清除所有围栏
        geoFenceClient.removeGeoFence()
        super.onCleared()
    }

}