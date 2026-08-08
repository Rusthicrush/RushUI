package com.rush.launcher.core.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

/**
 * Rush Game Turbo toggle — when on, the launcher signals other system pieces to:
 * lock brightness/notifications, prioritize CPU/GPU scheduling, and enable the
 * in-game floating performance overlay. The actual system hooks live outside
 * this composable; this is just the control surface.
 */
@Composable
fun GamingModeCard(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    GlassPanel(modifier = modifier.fillMaxWidth(), cornerRadius = 20.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Rush Game Turbo", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                Text("Boost performance, silence interruptions", color = colors.textSecondary, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            }
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colors.accentGlow,
                    checkedTrackColor = colors.accent
                )
            )
        }
    }
}
