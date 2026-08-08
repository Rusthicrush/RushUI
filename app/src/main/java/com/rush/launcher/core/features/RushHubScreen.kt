package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

data class RushHubTool(
    val id: String,
    val name: String,
    val icon: ImageVector,
    val onOpen: () -> Unit
)

/**
 * Rush Hub — "Tools Center", the single grid entry point for every exclusive
 * Rush feature (Sync, Space, Game Turbo, Automation, plus any future add-on
 * like Advanced File Manager, Floating Windows, Edge Panel). Each tile just
 * navigates to its own screen; this composable has no feature logic itself.
 */
@Composable
fun RushHubScreen(
    tools: List<RushHubTool>,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp)
    ) {
        Text("Rush Hub", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Text("Tools center", color = colors.textSecondary, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize().padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(tools, key = { it.id }) { tool ->
                GlassPanel(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable(onClick = tool.onOpen),
                    cornerRadius = 20.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = tool.icon, contentDescription = tool.name, tint = colors.accentGlow)
                        Text(
                            text = tool.name,
                            color = colors.textPrimary,
                            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
