package com.melody.map.myapplication.contract

import android.content.Intent
import com.amap.api.fence.GeoFence
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.melody.map.gd_compose.poperties.MapProperties
import com.melody.map.gd_compose.poperties.MapUiSettings
import com.melody.map.myapplication.base.state.IUiEffect
import com.melody.map.myapplication.base.state.IUiEvent
import com.melody.map.myapplication.base.state.IUiState
import com.melody.map.myapplication.model.GeoFenceDataModel

/**
 * DetailContract
 * @author TheMelody
 * email developer_melody@163.com
 * created 2023/2/18 17:46
 */
class DetailContract {
    sealed class Event : IUiEvent {
        object ShowOpenGPSDialog : Event()
        object HideOpenGPSDialog : Event()
        data class HandleGeoFenceReceiver(val intent: Intent?): Event()
    }

    data class State(
        /**
         * 是否打开了系统GPS权限
         */
        val isOpenGps: Boolean?,
        /**
         * 是否显示打开GPS的确认弹框
         */
        val isShowOpenGPSDialog: Boolean,
        /**
         * 是否已经初始化成功了地理围栏
         */
        val isInitGeoFence: Boolean,
        /**
         * App是否打开了定位权限
         */
        val grantLocationPermission:Boolean,
        /**
         * 围栏经纬度可会知的数据集合
         */
        val geoFenceDrawList :MutableList<GeoFenceDataModel>,
        val mapProperties: MapProperties,
        val mapUiSettings: MapUiSettings
    ) : IUiState

    sealed class Effect : IUiEffect {
        internal data class Toast(val msg: String?) : Effect()
    }
}