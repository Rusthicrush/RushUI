package com.rush.launcher.core.control

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * Single quick-toggle tile (WiFi, Bluetooth, Mobile Data, Flashlight, etc).
 * `isOn` drives the fill color — accent-glow when active, plain glass when off.
 */
@Composable
fun QuickToggleTile(
    label: String,
    icon: ImageVector,
    isOn: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    GlassPanel(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(onClick = onToggle),
        cornerRadius = 18.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isOn) colors.accent.copy(alpha = 0.18f) else androidx.compose.ui.graphics.Color.Transparent)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isOn) colors.accentGlow else colors.textSecondary
            )
            Text(
                text = label,
                color = if (isOn) colors.textPrimary else colors.textSecondary,
                style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
