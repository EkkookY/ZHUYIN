package com.melody.map.gd_compose.model

import com.amap.api.maps.model.LatLng


interface ClusterItem {
    /**
     * 返回聚合元素的地理位置
     */
    fun getPosition(): LatLng
}