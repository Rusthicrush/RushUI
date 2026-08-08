package com.rush.launcher.core.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

data class DeviceStats(
    val ramUsedPercent: Int,
    val batteryPercent: Int,
    val batteryTempCelsius: Float,
    val storageUsedPercent: Int
)

/**
 * Live device performance readout. Caller is responsible for polling
 * ActivityManager.MemoryInfo / BatteryManager / StatFs and passing fresh `stats`
 * on an interval (e.g. every 3-5s) — this composable is purely presentational.
 */
@Composable
fun DevicePerformanceCard(
    stats: DeviceStats,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    GlassPanel(modifier = modifier.fillMaxWidth(), cornerRadius = 20.dp) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Device", color = colors.textSecondary, style = androidx.compose.material3.MaterialTheme.typography.labelSmall)
            StatRow("RAM", "${stats.ramUsedPercent}%", colors.textPrimary)
            StatRow("Battery", "${stats.batteryPercent}% · ${stats.batteryTempCelsius}°C", colors.textPrimary)
            StatRow("Storage", "${stats.storageUsedPercent}% used", colors.textPrimary)
        }
    }
}

@Composable
private fun StatRow(label: String, value: String, textColor: androidx.compose.ui.graphics.Color) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = textColor.copy(alpha = 0.7f))
        Text(value, color = textColor)
    }
}
