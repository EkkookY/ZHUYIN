package com.melody.map.gd_compose.poperties

import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.maps.model.MyLocationStyle
import com.melody.map.gd_compose.model.MapType
import java.util.Objects

val DefaultMapProperties = MapProperties()


class MapProperties(
    val language: String = AMap.CHINESE,
    val isShowBuildings: Boolean = false,
    val isShowMapLabels: Boolean = true,
    val isIndoorEnabled: Boolean = false,
    val isMyLocationEnabled: Boolean = false,
    val isTrafficEnabled: Boolean = false,
    val myLocationStyle: MyLocationStyle? = null,
    val maxZoomPreference: Float = 21.0F,
    val minZoomPreference: Float = 0F,
    val mapShowLatLngBounds: LatLngBounds? = null,
    val mapType: MapType = MapType.NORMAL,
) {

    override fun equals(other: Any?): Boolean = other is MapProperties &&
        language == other.language &&
        isShowBuildings == other.isShowBuildings &&
        isShowMapLabels == other.isShowMapLabels &&
        isIndoorEnabled == other.isIndoorEnabled &&
        isMyLocationEnabled == other.isMyLocationEnabled &&
        isTrafficEnabled == other.isTrafficEnabled &&
        myLocationStyle == other.myLocationStyle &&
        maxZoomPreference == other.maxZoomPreference &&
        minZoomPreference == other.minZoomPreference &&
        mapShowLatLngBounds == other.mapShowLatLngBounds &&
        mapType == other.mapType

    override fun hashCode(): Int = Objects.hash(
        language,
        isShowBuildings,
        isShowMapLabels,
        isIndoorEnabled,
        isMyLocationEnabled,
        isTrafficEnabled,
        myLocationStyle,
        maxZoomPreference,
        minZoomPreference,
        mapShowLatLngBounds,
        mapType
    )

    fun copy(
        language: String = this.language,
        isShowBuildings: Boolean = this.isShowBuildings,
        isShowMapLabels: Boolean = this.isShowMapLabels,
        isIndoorEnabled: Boolean = this.isIndoorEnabled,
        isMyLocationEnabled: Boolean = this.isMyLocationEnabled,
        isTrafficEnabled: Boolean = this.isTrafficEnabled,
        myLocationStyle: MyLocationStyle? = this.myLocationStyle,
        maxZoomPreference: Float = this.maxZoomPreference,
        minZoomPreference: Float = this.minZoomPreference,
        mapShowLatLngBounds: LatLngBounds? = this.mapShowLatLngBounds,
        mapType: MapType = this.mapType,
    ): MapProperties = MapProperties(
        language = language,
        isShowBuildings = isShowBuildings,
        isShowMapLabels = isShowMapLabels,
        isIndoorEnabled = isIndoorEnabled,
        isMyLocationEnabled = isMyLocationEnabled,
        isTrafficEnabled = isTrafficEnabled,
        myLocationStyle = myLocationStyle,
        maxZoomPreference = maxZoomPreference,
        minZoomPreference = minZoomPreference,
        mapShowLatLngBounds = mapShowLatLngBounds,
        mapType = mapType,
    )

    override fun toString(): String {
        return "MapProperties(language=$language, " +
                "isShowBuildings=$isShowBuildings, " +
                "isShowMapLabels=$isShowMapLabels, " +
                "isIndoorEnabled=$isIndoorEnabled, " +
                "isMyLocationEnabled=$isMyLocationEnabled, " +
                "isTrafficEnabled=$isTrafficEnabled, " +
                "myLocationStyle=$myLocationStyle, " +
                "maxZoomPreference=$maxZoomPreference, " +
                "minZoomPreference=$minZoomPreference, " +
                "mapShowLatLngBounds=$mapShowLatLngBounds, " +
                "mapType=$mapType)"
    }
}

