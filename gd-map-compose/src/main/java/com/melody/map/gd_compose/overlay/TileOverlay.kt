package com.melody.map.gd_compose.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.amap.api.maps.model.TileOverlay
import com.amap.api.maps.model.TileOverlayOptions
import com.amap.api.maps.model.TileProvider
import com.melody.map.gd_compose.MapApplier
import com.melody.map.gd_compose.MapNode
import com.melody.map.gd_compose.model.GDMapComposable

private class TileOverlayNode(
    var tileOverlay: TileOverlay
) : MapNode {
    override fun onRemoved() {
        tileOverlay.remove()
    }
}

/**
 * 瓦片图层
 *
 * @param tileProvider 瓦片图层的提供者
 * @param visible 瓦片图层是否可见
 * @param zIndex 瓦片图层显示层级
 * @param memoryCacheEnabled 瓦片图层是否开启内存缓存
 * @param diskCacheEnabled 瓦片图层是否开启磁盘缓存
 * @param diskCacheDir 瓦片图层的磁盘缓存目录
 * @param memCacheSize 瓦片图层的内存缓存大小，默认值5MB
 * @param diskCacheSize 瓦片图层的默认磁盘缓存大小，默认值20MB
 */
@Composable
@GDMapComposable
fun TileOverlay(
    tileProvider: TileProvider,
    visible: Boolean = true,
    memoryCacheEnabled: Boolean = true,
    diskCacheEnabled: Boolean = true,
    diskCacheDir: String? = null,
    memCacheSize: Int = 5242880,
    diskCacheSize: Int = 20971520,
    zIndex: Float = 0f
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<TileOverlayNode, MapApplier>(
        factory = {
            val tileOverlay = mapApplier?.map?.addTileOverlay(
                TileOverlayOptions().apply {
                    tileProvider(tileProvider)
                    memoryCacheEnabled(memoryCacheEnabled)
                    diskCacheEnabled(diskCacheEnabled)
                    diskCacheDir(diskCacheDir)
                    memCacheSize(memCacheSize)
                    diskCacheSize(diskCacheSize)
                    visible(visible)
                    zIndex(zIndex)
                }
            ) ?: error("Error adding tile overlay")
            TileOverlayNode(tileOverlay)
        },
        update = {
            set(tileProvider) {
                this.tileOverlay.clearTileCache()
                this.tileOverlay.remove()
                this.tileOverlay = mapApplier?.map?.addTileOverlay(
                    TileOverlayOptions().apply {
                        tileProvider(tileProvider)
                        memoryCacheEnabled(memoryCacheEnabled)
                        diskCacheEnabled(diskCacheEnabled)
                        diskCacheDir(diskCacheDir)
                        memCacheSize(memCacheSize)
                        diskCacheSize(diskCacheSize)
                        visible(visible)
                        zIndex(zIndex)
                    }
                ) ?: error("Error adding tile overlay")
            }
            set(visible) { this.tileOverlay.isVisible = it }
            set(zIndex) { this.tileOverlay.zIndex = it }
        }
    )
}