package com.rush.launcher.core.home

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * RUSH Home Screen — the main launcher surface.
 *
 * Gestures (matches the vision doc):
 *  - Swipe UP from anywhere below the dock  -> onOpenAppDrawer
 *  - Swipe DOWN from the top                -> onOpenControlCenter
 *  - Long-press empty space                 -> onEnterEditMode
 *
 * Wallpaper: pass it in as `wallpaper` (drawable/bitmap layer) so this composable
 * stays agnostic to whether it's a static image or a live/dynamic wallpaper renderer.
 */
@Composable
fun RushHomeScreen(
    dockApps: List<DockApp>,
    onOpenAppDrawer: () -> Unit,
    onOpenControlCenter: () -> Unit,
    onEnterEditMode: () -> Unit,
    modifier: Modifier = Modifier,
    wallpaper: @Composable () -> Unit = {},
    widgets: @Composable () -> Unit = {}
) {
    val colors = RushThemeAccess.colors
    var dragAccum = 0f

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragStart = { dragAccum = 0f },
                    onVerticalDrag = { change, dragAmount ->
                        dragAccum += dragAmount
                        change.consume()
                    },
                    onDragEnd = {
                        when {
                            dragAccum < -120f -> onOpenAppDrawer()      // swiped up
                            dragAccum > 120f -> onOpenControlCenter()   // swiped down
                        }
                        dragAccum = 0f
                    }
                )
            }
    ) {
        // Wallpaper layer sits behind everything
        wallpaper()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(top = 56.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                RushClock()
                widgets()
            }

            SmartDock(apps = dockApps.ifEmpty { defaultDockApps() })
        }
    }
}

private fun defaultDockApps(): List<DockApp> = listOf(
    DockApp("Phone", Icons.Filled.Call, {}),
    DockApp("Messages", Icons.Filled.Email, {}),
    DockApp("Music", Icons.Filled.PlayArrow, {}),
    DockApp("Settings", Icons.Filled.Settings, {}),
)
