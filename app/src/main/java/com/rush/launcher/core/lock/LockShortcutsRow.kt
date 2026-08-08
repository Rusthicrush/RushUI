package com.rush.launcher.core.lock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

data class LockShortcut(val label: String, val icon: ImageVector, val onClick: () -> Unit)

/** Row of quick-launch shortcuts on the lock screen — camera, flashlight, QR scanner, etc. */
@Composable
fun LockShortcutsRow(
    shortcuts: List<LockShortcut>,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        shortcuts.forEach { shortcut ->
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(colors.glassFill)
                    .clickable(onClick = shortcut.onClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = shortcut.icon, contentDescription = shortcut.label, tint = colors.textPrimary)
            }
        }
    }
}
