package com.melody.map.gd_compose.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.platform.LocalContext
import com.amap.api.maps.model.BitmapDescriptor
import com.amap.api.maps.model.Marker
import com.melody.map.gd_compose.MapApplier
import com.melody.map.gd_compose.MapNode
import com.melody.map.gd_compose.kernel.KernelClusterOverlay
import com.melody.map.gd_compose.model.ClusterClickListener
import com.melody.map.gd_compose.model.ClusterItem
import com.melody.map.gd_compose.model.ClusterRender
import com.melody.map.gd_compose.model.GDMapComposable

internal class ClusterOverlayNode(val overlay: KernelClusterOverlay): MapNode {
    override fun onRemoved() {
        overlay.onDestroy()
    }
}

/**
 * 点聚合效果ClusterOverlay
 * @param clusterRadius  聚合范围的大小（指点像素单位距离内的点会聚合到一个点显示）
 * @param defaultCacheSize 默认最多会缓存80张图片作为聚合显示元素图片,根据自己显示需求和app使用内存情况,可以修改数量
 * @param clusterRenderer 设置聚合元素的渲染样式，不设置则默认为气泡加数字形式进行渲染
 * @param clusterItems 设置聚合数据列表
 * @param defaultClusterIcon 没有设置render，或者render里面没有找到数据的话，显示的图标
 * @param onClick 聚合元素点击回调
 */
@Composable
@GDMapComposable
fun ClusterOverlay(
    clusterRadius: Int,
    defaultCacheSize: Int = 80,
    clusterRenderer : ClusterRender?,
    clusterItems: List<ClusterItem>,
    defaultClusterIcon: BitmapDescriptor,
    onClick: (Marker, List<ClusterItem>)-> Unit
) {
    val mapApplier = currentComposer.applier as? MapApplier
    val context = LocalContext.current
    ComposeNode<ClusterOverlayNode, MapApplier>(
        factory = {
            val aMap = mapApplier?.map?: error("Error adding ClusterOverlay")
            val clusterOverlay = KernelClusterOverlay(
                aMap = aMap,
                context = context,
                clusterRadius = clusterRadius,
                defaultClusterIcon = defaultClusterIcon,
                defaultCacheSize = defaultCacheSize
            )
            clusterOverlay.setClusterItems(clusterItems)
            clusterOverlay.setClusterRenderer(clusterRenderer)
            clusterOverlay.setOnClusterClickListener(object : ClusterClickListener {
                override fun onClick(marker: Marker, clusterItems: List<ClusterItem>) {
                    onClick.invoke(marker,clusterItems)
                }
            })
            ClusterOverlayNode(clusterOverlay)
        },
        update = {
            set(clusterItems) { this.overlay.setClusterItems(it) }
            set(clusterRenderer) { this.overlay.setClusterRenderer(it) }
        }
    )
}
