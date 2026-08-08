package com.rush.launcher.core.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.MetallicSurface
import com.rush.launcher.core.theme.RushThemeAccess

data class DockApp(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

/**
 * RUSH Smart Dock — floating metallic bar pinned to the bottom of the home screen.
 * "Smart" = it can swap its app set based on context (e.g. gym app in the morning,
 * work apps during office hours) — that logic plugs in later via `apps` being
 * driven from a ViewModel instead of a static list.
 */
@Composable
fun SmartDock(
    apps: List<DockApp>,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    MetallicSurface(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp),
        cornerRadius = 28.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            apps.forEach { app ->
                DockIcon(app = app, tint = colors.textPrimary)
            }
        }
    }
}

@Composable
private fun DockIcon(app: DockApp, tint: Color) {
    val interaction = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = app.onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = app.icon,
            contentDescription = app.label,
            tint = tint,
            modifier = Modifier.size(28.dp)
        )
    }
}
