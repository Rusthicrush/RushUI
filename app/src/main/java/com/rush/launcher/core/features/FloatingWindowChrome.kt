package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * Rush Floating Window — the chrome around any app content shown as a floating
 * bubble/window (requires TYPE_APPLICATION_OVERLAY + SYSTEM_ALERT_WINDOW permission
 * at the platform level; this composable only handles in-window drag + the
 * title bar / close button, not the OS-level overlay setup).
 */
@Composable
fun FloatingWindowChrome(
    title: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    initialOffset: Offset = Offset(100f, 200f),
    content: @Composable () -> Unit
) {
    val colors = RushThemeAccess.colors
    var offset by remember { mutableStateOf(initialOffset) }

    Box(
        modifier = modifier.offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
    ) {
        GlassPanel(modifier = Modifier.width(280.dp), cornerRadius = 18.dp) {
            Column {
                // Title bar — drag handle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(colors.metallicDark)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                offset += dragAmount
                            }
                        }
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(title, color = colors.textPrimary, modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(18.dp).clickable(onClick = onClose)
                    )
                }
                Box(modifier = Modifier.padding(8.dp)) {
                    content()
                }
            }
        }
    }
}
