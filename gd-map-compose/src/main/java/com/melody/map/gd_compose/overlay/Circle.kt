package com.melody.map.gd_compose.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.amap.api.maps.model.Circle
import com.amap.api.maps.model.CircleOptions
import com.amap.api.maps.model.LatLng
import com.melody.map.gd_compose.MapApplier
import com.melody.map.gd_compose.MapNode
import com.melody.map.gd_compose.model.GDMapComposable

internal class CircleNode(
    val circle: Circle
) : MapNode {
    override fun onRemoved() {
        circle.remove()
    }
}

/**
 * 在地图上绘制圆的覆盖物.
 *
 * @param center 圆心经纬度坐标
 * @param fillColor 圆的填充颜色
 * @param radius 圆的半径，单位米.
 * @param strokeColor 圆的边框颜色
 * @param strokeDottedLineType 圆的边框虚线形状
 * @param strokeWidth 圆的边框宽度
 * @param visible 圆是否可见
 * @param zIndex 设置显示的层级，越大越靠上显示
 */
@Composable
@GDMapComposable
fun Circle(
    center: LatLng,
    fillColor: Color = Color.Transparent,
    radius: Double = 0.0,
    strokeColor: Color = Color.Black,
    strokeDottedLineType: Int = -1,
    strokeWidth: Float = 10f,
    visible: Boolean = true,
    zIndex: Float = 0f,
) {
    val mapApplier = currentComposer.applier as? MapApplier
    ComposeNode<CircleNode, MapApplier>(
        factory = {
            val circle = mapApplier?.map?.addCircle(
                CircleOptions().apply  {
                    center(center)
                    fillColor(fillColor.toArgb())
                    radius(radius)
                    strokeColor(strokeColor.toArgb())
                    setStrokeDottedLineType(strokeDottedLineType)
                    strokeWidth(strokeWidth)
                    visible(visible)
                    zIndex(zIndex)
                }
            ) ?: error("Error adding circle")
            CircleNode(circle)
        },
        update = {
            set(center) { this.circle.center = it }
            set(fillColor) { this.circle.fillColor = it.toArgb() }
            set(radius) { this.circle.radius = it }
            set(strokeColor) { this.circle.strokeColor = it.toArgb() }
            set(strokeDottedLineType) {this.circle.strokeDottedLineType = it }
            set(strokeWidth) { this.circle.strokeWidth = it }
            set(visible) { this.circle.isVisible = it }
            set(zIndex) { this.circle.zIndex = it }
        }
    )
}