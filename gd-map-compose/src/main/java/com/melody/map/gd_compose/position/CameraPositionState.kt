
package com.melody.map.gd_compose.position

import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdate
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.Projection
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@Composable
inline fun rememberCameraPositionState(
    key: String? = null,
    crossinline init: CameraPositionState.() -> Unit = {}
): CameraPositionState = rememberSaveable(key = key, saver = CameraPositionState.Saver) {
    CameraPositionState().apply(init)
}

class CameraPositionState(position: CameraPosition = CameraPosition(LatLng(39.91, 116.40), 11f, 0f, 0f)) {

    var isMoving: Boolean by mutableStateOf(false)
        internal set


    val projection: Projection?
        get() = map?.projection

    val projectionMatrix: FloatArray?
        get() = map?.projectionMatrix

    val viewMatrix: FloatArray?
        get() = map?.viewMatrix

    internal var rawPosition by mutableStateOf(position)

    var position: CameraPosition
        get() = rawPosition
        set(value) {
            synchronized(lock) {
                val map = map
                if (map == null) {
                    rawPosition = value
                } else {
                    map.moveCamera(CameraUpdateFactory.newCameraPosition(value))
                }
            }
        }

    private var map: AMap? = null

    private val lock = Any()

    // An action to run when the map becomes available or unavailable.
    // represents a mutually exclusive mutation to perform while holding `lock`.
    // Guarded by `lock`.
    private var onMapChanged: OnMapChangedCallback? = null

    private fun doOnMapChangedLocked(callback: OnMapChangedCallback) {
        onMapChanged?.onCancelLocked()
        onMapChanged = callback
    }

    // A token representing the current owner of any ongoing motion in progress.
    // Used to determine if map animation should stop when calls to animate end.
    // Guarded by `lock`.
    private var movementOwner: Any? = null

    private fun interface OnMapChangedCallback {
        fun onMapChangedLocked(newMap: AMap?)
        fun onCancelLocked() {}
    }

    internal fun setMap(map: AMap?) {
        synchronized(lock) {
            if (this.map == null && map == null) return
            if (this.map != null && map != null) {
                error("CameraPositionState may only be associated with one AMap at a time")
            }
            this.map = map
            if (map == null) {
                isMoving = false
            } else {
                map.moveCamera(CameraUpdateFactory.newCameraPosition(position))
            }
            onMapChanged?.let {
                // Clear this first since the callback itself might set it again for later
                onMapChanged = null
                it.onMapChangedLocked(map)
            }
        }
    }


    @UiThread
    suspend fun animate(update: CameraUpdate, durationMs: Int = Integer.MAX_VALUE) {
        val myJob = currentCoroutineContext()[Job]
        try {
            suspendCancellableCoroutine { continuation ->
                synchronized(lock) {
                    movementOwner = myJob
                    val map = map
                    if (map == null) {
                        // Do it later
                        val animateOnMapAvailable = object : OnMapChangedCallback {
                            override fun onMapChangedLocked(newMap: AMap?) {
                                if (newMap == null) {
                                    // Cancel the animate caller and crash the map setter
                                    continuation.resumeWithException(CancellationException(
                                        "internal error; no AMap available"))
                                    error(
                                        "internal error; no AMap available to animate position"
                                    )
                                }
                                performAnimateCameraLocked(newMap, update, durationMs, continuation)
                            }

                            override fun onCancelLocked() {
                                continuation.resumeWithException(
                                    CancellationException("Animation cancelled")
                                )
                            }
                        }
                        doOnMapChangedLocked(animateOnMapAvailable)
                        continuation.invokeOnCancellation {
                            synchronized(lock) {
                                if (onMapChanged === animateOnMapAvailable) {
                                    // External cancellation shouldn't invoke onCancel
                                    // so we set this to null directly instead of going through
                                    // doOnMapChangedLocked(null).
                                    onMapChanged = null
                                }
                            }
                        }
                    } else {
                        performAnimateCameraLocked(map, update, durationMs, continuation)
                    }
                }
            }
        } finally {
            // continuation.invokeOnCancellation might be called from any thread, so stop the
            // animation in progress here where we're guaranteed to be back on the right dispatcher.
            synchronized(lock) {
                if (myJob != null && movementOwner === myJob) {
                    movementOwner = null
                    map?.stopAnimation()
                }
            }
        }
    }

    private fun performAnimateCameraLocked(
        map: AMap,
        update: CameraUpdate,
        durationMs: Int,
        continuation: CancellableContinuation<Unit>
    ) {
        val cancelableCallback = object : AMap.CancelableCallback {
            override fun onCancel() {
                continuation.resumeWithException(CancellationException("Animation cancelled"))
            }

            override fun onFinish() {
                continuation.resume(Unit)
            }
        }
        if (durationMs == Integer.MAX_VALUE) {
            map.animateCamera(update, cancelableCallback)
        } else {
            map.animateCamera(update, durationMs.toLong(), cancelableCallback)
        }
        doOnMapChangedLocked {
            check(it == null) {
                "New AMap unexpectedly set while an animation was still running"
            }
            map.stopAnimation()
        }
    }


    @UiThread
    fun move(update: CameraUpdate) {
        synchronized(lock) {
            val map = map
            movementOwner = null
            if (map == null) {
                // Do it when we have a map available
                doOnMapChangedLocked { it?.moveCamera(update) }
            } else {
                map.moveCamera(update)
            }
        }
    }
    companion object {
        val Saver: Saver<CameraPositionState, CameraPosition> = Saver(
            save = { it.position },
            restore = { CameraPositionState(it) }
        )
    }
}