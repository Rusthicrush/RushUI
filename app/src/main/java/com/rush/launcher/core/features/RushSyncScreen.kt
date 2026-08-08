package com.rush.launcher.core.features

import androidx.compose.foundation.background
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

enum class SyncStatus { DISCONNECTED, PAIRING, CONNECTED }

data class SyncedDevice(val name: String, val status: SyncStatus)

/**
 * Rush Sync — pairs the phone with a PC (likely over local Wi-Fi/LAN using a
 * QR-code handshake, similar in spirit to Phone Link, but Rush-branded and
 * standalone — no Microsoft/Google account dependency). This screen is the
 * pairing + status UI; the transport layer (socket/WebRTC) lives elsewhere.
 */
@Composable
fun RushSyncScreen(
    device: SyncedDevice?,
    onStartPairing: () -> Unit,
    onDisconnect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier.fillMaxSize().background(colors.background).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Rush Sync", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Text("Connect your phone to your PC", color = colors.textSecondary)

        GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 20.dp) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(device?.name ?: "No device connected", color = colors.textPrimary)
                    Text(
                        text = when (device?.status) {
                            SyncStatus.CONNECTED -> "Connected"
                            SyncStatus.PAIRING -> "Pairing…"
                            else -> "Disconnected"
                        },
                        color = if (device?.status == SyncStatus.CONNECTED) colors.success else colors.textSecondary
                    )
                }
                if (device?.status == SyncStatus.CONNECTED) {
                    NeonButton(label = "Disconnect", onClick = onDisconnect)
                } else {
                    NeonButton(label = "Pair Device", onClick = onStartPairing)
                }
            }
        }

        Text(
            "Once connected: mirror notifications, transfer files, and control media without cables.",
            color = colors.textSecondary,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
    }
}
