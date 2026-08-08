package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

data class EdgePanelShortcut(val label: String, val icon: ImageVector, val onClick: () -> Unit)

/**
 * Rush Edge Panel — a slim vertical glass strip pinned to the screen edge that
 * expands into a shortcuts panel. The pull-tab (`EdgePanelHandle`) lives on
 * screen at all times; `EdgePanelContent` is what's shown once expanded.
 * Caller owns the expanded/collapsed boolean and the edge-swipe detection zone.
 */
@Composable
fun EdgePanelHandle(
    onExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    Box(
        modifier = modifier
            .width(6.dp)
            .height(80.dp)
            .background(colors.accentGlow.copy(alpha = 0.6f))
            .clickable(onClick = onExpand)
    )
}

@Composable
fun EdgePanelContent(
    shortcuts: List<EdgePanelShortcut>,
    onCollapse: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    GlassPanel(
        modifier = modifier
            .width(72.dp)
            .fillMaxHeight()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 20f) onCollapse()  // swipe back toward the edge to collapse
                }
            },
        cornerRadius = 0.dp
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(vertical = 24.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            shortcuts.forEach { shortcut ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(onClick = shortcut.onClick)
                ) {
                    Icon(imageVector = shortcut.icon, contentDescription = shortcut.label, tint = colors.textPrimary, modifier = Modifier.size(24.dp))
                    Text(shortcut.label, color = colors.textSecondary, style = androidx.compose.material3.MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
