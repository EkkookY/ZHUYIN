package com.melody.map.gd_compose.model

import android.graphics.drawable.Drawable

interface ClusterRender {
    /**
     * 根据聚合点的元素数目返回渲染背景样式
     */
    fun getDrawable(clusterNum: Int): Drawable?
}