package com.rush.launcher.core.control

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * Screen recorder trigger. `isRecording` + `elapsedSeconds` are owned by the caller
 * (a foreground service using MediaProjection); this is just the CC entry point.
 */
@Composable
fun ScreenRecorderTile(
    isRecording: Boolean,
    elapsedSeconds: Int,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    GlassPanel(
        modifier = modifier.fillMaxWidth().clickable(onClick = onToggle),
        cornerRadius = 18.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Screen Recorder",
                tint = if (isRecording) colors.danger else colors.textPrimary
            )
            Text(
                text = if (isRecording) "Recording · ${formatElapsed(elapsedSeconds)}" else "Screen Recorder",
                color = colors.textPrimary,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

private fun formatElapsed(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}
