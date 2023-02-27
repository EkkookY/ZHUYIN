package com.melody.map.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.example.testzhuyin.ui.page.HomeUIbutton
import com.melody.map.gd_compose.GDMap
import com.melody.map.gd_compose.overlay.Marker
import com.melody.map.gd_compose.overlay.rememberMarkerState
import com.melody.map.gd_compose.position.rememberCameraPositionState
import com.melody.map.myapplication.contract.HomeContract
import com.example.testzhuyin.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
internal fun HomeMapScreen(openDetailPage: (LatLng, String)-> Unit) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(34.3227, 108.5525), 3.5F, 0F, 0F)
    }
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel.effect){
        viewModel.effect.onEach {
            when(it) {
                is HomeContract.Effect.Navigation.OpenDetailPage -> {
                    openDetailPage.invoke(it.latLng,it.iconName)
                }
            }
        }.collect()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GDMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = uiState.uiSettings,
            properties = uiState.mapProperties
        ) {
            uiState.ethnicityDataList.forEach {
                Marker(
                    icon = BitmapDescriptorFactory.fromAsset(it.assets_name),
                    tag = it.assets_name, // 设置tag
                    state = rememberMarkerState(position = LatLng(it.latitude,it.longitude)),
                    onClick = { marker ->
                        // 获取assets里面的民族图片的名称
                        viewModel.openDetailPage(marker.position, marker.`object` as String)
                        true
                    }
                )
            }
        }
        HomeUIbutton(modifier =Modifier.padding(top=300.dp))
    }
}