package com.melody.map.gd_compose.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.amap.api.maps.AMap
import com.amap.api.maps.CustomRenderer
import com.melody.map.gd_compose.MapApplier
import com.melody.map.gd_compose.MapNode
import com.melody.map.gd_compose.model.GDMapComposable


internal class OpenGLOverlayNode(
    val aMap: AMap
) : MapNode

/**
 * 地图3D模型，开放OpenGL绘制接口
 * @param showBuildingInfo 是否显示建筑物
 * @param showMapTextInfo 是否显示文字
 * @param render OpenGL Render
 */
@Composable
@GDMapComposable
fun OpenGLOverlay(showBuildingInfo: Boolean, showMapTextInfo: Boolean, render: CustomRenderer) {
    val mapApplier = currentComposer.applier as? MapApplier
    ComposeNode<OpenGLOverlayNode, MapApplier>(
        factory = {
            val aMap = mapApplier?.map?.apply {
                showMapText(showMapTextInfo)
                showBuildings(showBuildingInfo)
                setCustomRenderer(render)
            } ?: error("Error adding OpenGLOverlay")
            OpenGLOverlayNode(aMap)
        },
        update = {
            set(showMapTextInfo) { this.aMap.showMapText(it) }
            set(showBuildingInfo) { this.aMap.showBuildings(it) }
            set(render) { this.aMap.setCustomRenderer(it) }
        }
    )
}