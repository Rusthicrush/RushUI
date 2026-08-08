package com.rush.launcher.core.lock

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.control.NowPlaying
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * RUSH Lock Screen.
 *
 * Gesture: swipe UP anywhere to unlock (dispatches onUnlock; actual keyguard
 * dismissal / biometric prompt is handled by the caller, this just signals intent).
 *
 * Layered top to bottom: wallpaper -> clock -> charging ring (if charging) ->
 * visualizer (if music playing) -> shortcuts row, all sitting on the active theme.
 */
@Composable
fun RushLockScreen(
    clockStyle: LockClockStyle,
    isCharging: Boolean,
    batteryPercent: Int,
    nowPlaying: NowPlaying?,
    shortcuts: List<LockShortcut>,
    onUnlock: () -> Unit,
    modifier: Modifier = Modifier,
    wallpaper: @Composable () -> Unit = {}
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
                    onVerticalDrag = { change, amount ->
                        dragAccum += amount
                        change.consume()
                    },
                    onDragEnd = {
                        if (dragAccum < -140f) onUnlock()
                        dragAccum = 0f
                    }
                )
            }
    ) {
        wallpaper()

        if (isCharging) {
            ChargingAnimation(isCharging = true, batteryPercent = batteryPercent, modifier = Modifier.fillMaxSize())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(top = 80.dp, start = 20.dp, end = 20.dp, bottom = 40.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LockScreenClock(style = clockStyle)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                if (nowPlaying != null && nowPlaying.isPlaying) {
                    Text("${nowPlaying.title} — ${nowPlaying.artist}", color = colors.textSecondary)
                    MusicVisualizer(isPlaying = true)
                }
                Text("Swipe up to unlock", color = colors.textSecondary)
                LockShortcutsRow(shortcuts = shortcuts)
            }
        }
    }
}
