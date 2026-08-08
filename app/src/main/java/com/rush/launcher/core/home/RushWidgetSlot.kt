package com.rush.launcher.core.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel

/**
 * Wraps any widget content (weather, battery, notes, music, RAM monitor, etc.)
 * in the standard Rush glass panel so every widget looks native to the OS
 * regardless of what data it shows. Widgets themselves plug in as `content`.
 */
@Composable
fun RushWidgetSlot(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    GlassPanel(modifier = modifier) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}
