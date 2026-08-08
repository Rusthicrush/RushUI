package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

data class GameTurboSettings(
    val blockNotifications: Boolean,
    val lockBrightness: Boolean,
    val performanceOverlay: Boolean,
    val autoRejectCalls: Boolean,
    val cpuGpuPriorityBoost: Boolean
)

/**
 * Rush Game Turbo — full settings screen (the Control Center card is just the
 * master on/off; this is where each individual behavior is configured).
 */
@Composable
fun GameTurboSettingsScreen(
    settings: GameTurboSettings,
    onChange: (GameTurboSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier.fillMaxSize().background(colors.background).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Rush Game Turbo", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Text("Fine-tune what activates during gaming sessions", color = colors.textSecondary)

        SettingToggleRow("Block notifications", settings.blockNotifications) { onChange(settings.copy(blockNotifications = it)) }
        SettingToggleRow("Lock brightness", settings.lockBrightness) { onChange(settings.copy(lockBrightness = it)) }
        SettingToggleRow("Performance overlay (FPS/temp)", settings.performanceOverlay) { onChange(settings.copy(performanceOverlay = it)) }
        SettingToggleRow("Auto-reject calls", settings.autoRejectCalls) { onChange(settings.copy(autoRejectCalls = it)) }
        SettingToggleRow("CPU/GPU priority boost", settings.cpuGpuPriorityBoost) { onChange(settings.copy(cpuGpuPriorityBoost = it)) }
    }
}

@Composable
private fun SettingToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val colors = RushThemeAccess.colors
    GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 16.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = colors.textPrimary)
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = colors.accentGlow, checkedTrackColor = colors.accent)
            )
        }
    }
}
