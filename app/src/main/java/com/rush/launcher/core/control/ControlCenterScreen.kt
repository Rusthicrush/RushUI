package com.rush.launcher.core.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

data class ControlCenterState(
    val wifiOn: Boolean,
    val bluetoothOn: Boolean,
    val mobileDataOn: Boolean,
    val flashlightOn: Boolean,
    val brightness: Float,
    val volume: Float,
    val nowPlaying: NowPlaying?,
    val deviceStats: DeviceStats,
    val isRecording: Boolean,
    val recordingElapsedSeconds: Int,
    val gamingModeOn: Boolean
)

/**
 * RUSH Control Center — opened via swipe-down from the top of the Home Screen
 * (see RushHomeScreen's onOpenControlCenter). All state is hoisted; this screen
 * is pure UI wired to whatever ViewModel owns WifiManager/BluetoothAdapter/etc.
 */
@Composable
fun ControlCenterScreen(
    state: ControlCenterState,
    onToggleWifi: () -> Unit,
    onToggleBluetooth: () -> Unit,
    onToggleMobileData: () -> Unit,
    onToggleFlashlight: () -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onVolumeChange: (Float) -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onToggleRecording: () -> Unit,
    onToggleGamingMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickToggleTile("Wi-Fi", Icons.Filled.Settings, state.wifiOn, onToggleWifi, Modifier.weight(1f))
            QuickToggleTile("Bluetooth", Icons.Filled.Settings, state.bluetoothOn, onToggleBluetooth, Modifier.weight(1f))
            QuickToggleTile("Data", Icons.Filled.Settings, state.mobileDataOn, onToggleMobileData, Modifier.weight(1f))
            QuickToggleTile("Torch", Icons.Filled.LocationOn, state.flashlightOn, onToggleFlashlight, Modifier.weight(1f))
        }

        BrightnessSlider(value = state.brightness, onValueChange = onBrightnessChange)
        VolumeSlider(value = state.volume, onValueChange = onVolumeChange)

        MediaControlCard(
            nowPlaying = state.nowPlaying,
            onPlayPause = onPlayPause,
            onNext = onNext,
            onPrevious = onPrevious
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ScreenRecorderTile(
                isRecording = state.isRecording,
                elapsedSeconds = state.recordingElapsedSeconds,
                onToggle = onToggleRecording,
                modifier = Modifier.weight(1f)
            )
        }

        DevicePerformanceCard(stats = state.deviceStats)

        GamingModeCard(isEnabled = state.gamingModeOn, onToggle = onToggleGamingMode)
    }
}
