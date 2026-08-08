package com.rush.launcher.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * Brushed-metal panel for elements that should read as "hardware" rather than glass —
 * dock background, control-center slider tracks, edge-panel handle.
 */
@Composable
fun MetallicSurface(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    content: @Composable () -> Unit
) {
    val colors = RushThemeAccess.colors
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colors.metallicDark,
                        colors.metallic.copy(alpha = 0.6f),
                        colors.metallicDark
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, 300f)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        content()
    }
}
