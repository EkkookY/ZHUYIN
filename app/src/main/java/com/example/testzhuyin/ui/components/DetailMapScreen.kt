package com.melody.map.myapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.melody.map.gd_compose.GDMap
import com.melody.map.gd_compose.overlay.Circle
import com.melody.map.gd_compose.overlay.Marker
import com.melody.map.gd_compose.overlay.Polygon
import com.melody.map.gd_compose.overlay.rememberMarkerState
import com.melody.map.gd_compose.position.rememberCameraPositionState
import com.melody.map.myapplication.model.CircleGeoFenceDataModel
import com.melody.map.myapplication.model.PolygonGeoFenceDataModel
import com.melody.map.myapplication.viewmodel.DetailViewModel

/**
 * DetailMapScreen
 */
@Composable
internal fun DetailMapScreen(latitude: Double, longitude: Double, targetAssets: String) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(latitude, longitude), 14F, 0F, 0F)
    }
    val viewModel: DetailViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    InitDetailPageEffect(viewModel, uiState, cameraPositionState, latitude, longitude)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)) {
        GDMap(
            modifier = Modifier.matchParentSize(),
            uiSettings = uiState.mapUiSettings,
            properties = uiState.mapProperties,
            locationSource = viewModel,
            cameraPositionState = cameraPositionState
        ) {
            // 绘制地理围栏(半径1000米，可自己在ViewModel中修改)
            uiState.geoFenceDrawList.onEach { data ->
                // 判断数据类型，选择高德支持的数据绘制，对应类型的围栏
                if (data.type == 1) {
                    Polygon(
                        points = (data as PolygonGeoFenceDataModel).pointList,
                        strokeWidth = 4F,
                        fillColor = Color(0x3376d4f3),
                        strokeColor = Color(0xcc3f91fc)
                    )
                } else {
                    Circle(
                        center = (data as CircleGeoFenceDataModel).point,
                        radius = data.radius.toDouble(),
                        strokeWidth = 4F,
                        fillColor = Color(0x3376d4f3),
                        strokeColor = Color(0xcc3f91fc)
                    )
                }
            }
            Marker(
                // 使用fromAsset注意：文件名称要有后缀.webp，否则不识别
                icon = BitmapDescriptorFactory.fromAsset(targetAssets),
                state = rememberMarkerState(position = LatLng(latitude,longitude))
            )
        }
    }
}

