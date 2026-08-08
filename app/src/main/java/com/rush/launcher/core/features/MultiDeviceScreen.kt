package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.components.NeonButton
import com.rush.launcher.core.theme.RushThemeAccess

enum class DeviceKind { PHONE, TABLET, PC, LAPTOP }

data class PairedDevice(
    val id: String,
    val name: String,
    val kind: DeviceKind,
    val isActive: Boolean,
    val isOnline: Boolean
)

/**
 * Rush Multi-Device — manage every device paired via Rush Sync from one screen
 * (phones, tablets, PCs). "Active" = the device currently receiving handoff /
 * clipboard sync; only one device is active at a time.
 */
@Composable
fun MultiDeviceScreen(
    devices: List<PairedDevice>,
    onSetActive: (PairedDevice) -> Unit,
    onAddDevice: () -> Unit,
    onRemoveDevice: (PairedDevice) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier.fillMaxSize().background(colors.background).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Your Devices", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)

        devices.forEach { device ->
            GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 18.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).clickable { onSetActive(device) }) {
                        Text(device.name, color = colors.textPrimary)
                        Text(
                            text = "${device.kind.name.lowercase().replaceFirstChar { it.uppercase() }} · ${if (device.isOnline) "Online" else "Offline"}${if (device.isActive) " · Active" else ""}",
                            color = if (device.isActive) colors.accentGlow else colors.textSecondary,
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                        )
                    }
                    Text(
                        text = "Remove",
                        color = colors.danger,
                        modifier = Modifier.clickable { onRemoveDevice(device) }
                    )
                }
            }
        }

        NeonButton(label = "Add Device", onClick = onAddDevice, modifier = Modifier.fillMaxWidth())
    }
}
