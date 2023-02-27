package com.melody.map.myapplication.utils

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.*

@ExperimentalPermissionsApi
@Composable
fun requestMultiplePermission(
    permissions: List<String>,
    onNoGrantPermission: () -> Unit = {},
    onGrantAllPermission: () -> Unit = {}
): MultiplePermissionsState {
    return rememberMultiplePermissionsState(
        permissions = permissions,
        onPermissionsResult = { mapInfo ->
            val noGrantPermissionMap = mapInfo.filter { !it.value }
            if (noGrantPermissionMap.isNotEmpty()) {
                onNoGrantPermission.invoke()
            } else {
                onGrantAllPermission.invoke()
            }
        }
    )
}