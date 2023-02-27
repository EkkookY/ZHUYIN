package com.melody.map.gd_compose.model

import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker

internal class Cluster internal constructor(val centerLatLng: LatLng) {
    private val mClusterItems: MutableList<ClusterItem> = mutableListOf()
    var marker: Marker? = null

    fun addClusterItem(clusterItem: ClusterItem) {
        mClusterItems.add(clusterItem)
    }

    val clusterCount: Int
        get() = mClusterItems.size
    val clusterItems: List<ClusterItem>
        get() = mClusterItems
}