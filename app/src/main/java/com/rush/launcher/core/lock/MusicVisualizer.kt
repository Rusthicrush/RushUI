package com.rush.launcher.core.lock

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * Lightweight visualizer: N bars pulsing independently. For a *real* amplitude-reactive
 * visualizer, feed live levels from android.media.audiofx.Visualizer instead of the
 * built-in infinite animation — swap the `barHeights` source, keep the rendering below.
 */
@Composable
fun MusicVisualizer(
    modifier: Modifier = Modifier,
    barCount: Int = 24,
    isPlaying: Boolean = true
) {
    val colors = RushThemeAccess.colors
    if (!isPlaying) return

    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(barCount) { index ->
            val transition = rememberInfiniteTransition(label = "bar-$index")
            val heightFraction by transition.animateFloat(
                initialValue = 0.15f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 400 + (index % 5) * 80, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "bar-height-$index"
            )
            Box(
                modifier = Modifier
                    .padding(end = 2.dp)
                    .width(3.dp)
                    .height((40 * heightFraction).dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(colors.accentGlow)
            )
        }
    }
}
