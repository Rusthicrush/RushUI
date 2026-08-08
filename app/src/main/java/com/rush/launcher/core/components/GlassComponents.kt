package com.rush.launcher.core.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess
import com.rush.launcher.core.theme.rushSpring

/**
 * The signature RUSH glass panel: translucent fill + soft neon-tinted border +
 * a subtle outer glow. Every card, dock, sheet, and control-center tile is built on this.
 *
 * Note on blur: real background blur (BlurEffect) requires API 31+. For API < 31 fallback,
 * pair this with a pre-blurred wallpaper snapshot behind the panel instead of a live blur.
 */
@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    glowRadius: Dp = 18.dp,
    content: @Composable () -> Unit
) {
    val colors = RushThemeAccess.colors
    Box(
        modifier = modifier
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(colors.glassFill, colors.glassFill.copy(alpha = colors.glassFill.alpha * 0.5f)),
                    start = Offset(0f, 0f),
                    end = Offset(400f, 400f)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(listOf(colors.glassBorder, colors.accentGlow.copy(alpha = 0.25f))),
                shape = RoundedCornerShape(cornerRadius)
            )
            .clip(RoundedCornerShape(cornerRadius))
    ) {
        content()
    }
}

/** Primary action button: neon-filled, glows on press, metallic edge highlight. */
@Composable
fun NeonButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(listOf(colors.accent, colors.accentGlow))
            )
            .border(1.dp, colors.metallic.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .clickable(interactionSource = interaction, indication = null, onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = Color.Black.copy(alpha = 0.85f))
    }
}

/** Thin holographic divider — animated diagonal shimmer sweep, used to separate glass sections. */
@Composable
fun HoloDivider(modifier: Modifier = Modifier) {
    val colors = RushThemeAccess.colors
    val transition = rememberInfiniteTransition(label = "holo-sweep")
    val offset by transition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "holo-offset"
    )

    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        colors.accentGlow.copy(alpha = 0.6f),
                        colors.metallic.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    start = Offset(offset * 600f, 0f),
                    end = Offset((offset * 600f) + 200f, 0f)
                )
            )
    )
}

/** Soft ambient glow placed behind a panel for the "premium flagship" halo effect. */
@Composable
fun NeonGlow(
    modifier: Modifier = Modifier,
    radius: Dp = 40.dp,
    color: Color = RushThemeAccess.colors.accentGlow
) {
    Box(
        modifier = modifier
            .blur(radius)
            .background(color.copy(alpha = 0.35f), RoundedCornerShape(50))
    )
}
