package com.rush.launcher.core.control

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * A single slider row for the control center — used for both brightness and volume,
 * just swap the icon and range. Track/thumb colors pull from the active theme
 * so it automatically re-skins across Neon Blue / AMOLED / Gold / Titanium.
 */
@Composable
fun RushSliderRow(
    icon: ImageVector,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f
) {
    val colors = RushThemeAccess.colors

    Row(
        modifier = modifier.fillMaxWidth().height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = colors.textSecondary)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
            colors = SliderDefaults.colors(
                thumbColor = colors.accentGlow,
                activeTrackColor = colors.accent,
                inactiveTrackColor = colors.glassFill
            )
        )
    }
}

/** Convenience wrapper for brightness specifically — swap the icon import for a sun/brightness icon in your icon set. */
@Composable
fun BrightnessSlider(value: Float, onValueChange: (Float) -> Unit, modifier: Modifier = Modifier) =
    RushSliderRow(icon = Icons.Filled.Star, value = value, onValueChange = onValueChange, modifier = modifier)

/** Convenience wrapper for media/ringer volume — swap the icon import for a speaker icon in your icon set. */
@Composable
fun VolumeSlider(value: Float, onValueChange: (Float) -> Unit, modifier: Modifier = Modifier) =
    RushSliderRow(icon = Icons.Filled.Star, value = value, onValueChange = onValueChange, modifier = modifier)
