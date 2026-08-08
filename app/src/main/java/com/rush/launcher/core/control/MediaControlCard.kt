package com.rush.launcher.core.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

data class NowPlaying(
    val title: String,
    val artist: String,
    val isPlaying: Boolean
)

/**
 * Now-playing card — shows current media session (from MediaSessionManager) with
 * play/pause/skip. Skip callbacks are no-ops if no session is active; caller decides.
 */
@Composable
fun MediaControlCard(
    nowPlaying: NowPlaying?,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    GlassPanel(modifier = modifier.fillMaxWidth(), cornerRadius = 20.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nowPlaying?.title ?: "Nothing playing",
                    color = colors.textPrimary,
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge
                )
                Text(
                    text = nowPlaying?.artist ?: "—",
                    color = colors.textSecondary,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onPrevious) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Previous", tint = colors.textSecondary)
            }
            IconButton(onClick = onPlayPause) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Play/Pause", tint = colors.accentGlow)
            }
            IconButton(onClick = onNext) {
                Icon(Icons.Filled.PlayArrow, contentDescription = "Next", tint = colors.textSecondary)
            }
        }
    }
}
