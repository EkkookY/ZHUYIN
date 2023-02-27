package com.melody.map.myapplication.contract

import com.amap.api.maps.model.LatLng
import com.melody.map.gd_compose.poperties.MapProperties
import com.melody.map.gd_compose.poperties.MapUiSettings
import com.melody.map.myapplication.base.state.IUiEffect
import com.melody.map.myapplication.base.state.IUiEvent
import com.melody.map.myapplication.base.state.IUiState
import com.melody.map.myapplication.model.EthnicityDataModel

/**
 * HomeContract
 * @author TheMelody
 * email developer_melody@163.com
 * created 2023/2/18 11:03
 */
class HomeContract {
    sealed class Event : IUiEvent {
        data class OpenDetailPage(val latLng: LatLng,val iconName: String):Event()
    }

    data class State(
        val isLoading: Boolean,
        val uiSettings: MapUiSettings,
        val mapProperties: MapProperties,
        val ethnicityDataList: List<EthnicityDataModel>
    ) : IUiState

    sealed class Effect : IUiEffect {
        sealed class Navigation {
            data class OpenDetailPage(val latLng: LatLng,val iconName: String): Effect()
        }
    }
}