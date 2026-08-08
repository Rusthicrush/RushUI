package com.rush.launcher.core.lock

import androidx.compose.runtime.getValue
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * Charging animation — 3 expanding neon rings pulse outward from center while plugged in.
 * Drive `isCharging` from BatteryManager.ACTION_BATTERY_CHANGED / EXTRA_STATUS.
 */
@Composable
fun ChargingAnimation(
    isCharging: Boolean,
    batteryPercent: Int,
    modifier: Modifier = Modifier
) {
    if (!isCharging) return
    val colors = RushThemeAccess.colors
    val transition = rememberInfiniteTransition(label = "charge-pulse")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing), RepeatMode.Restart),
        label = "charge-progress"
    )

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxRadius = size.minDimension / 3f
            listOf(0f, 0.33f, 0.66f).forEach { phaseOffset ->
                val phase = (progress + phaseOffset) % 1f
                drawCircle(
                    color = colors.accentGlow.copy(alpha = (1f - phase) * 0.5f),
                    radius = maxRadius * phase,
                    center = center,
                    style = Stroke(width = 3f)
                )
            }
        }
        Text("$batteryPercent% · Charging", color = colors.textPrimary)
    }
}
