package com.rush.launcher.core.customize

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess
import com.rush.launcher.core.theme.RushThemeVariant
import com.rush.launcher.core.theme.schemeFor

/**
 * Theme switcher — each card previews its own colors regardless of the app's
 * currently active theme (pulls colors via schemeFor(variant) directly, not
 * RushThemeAccess.colors, so all 4 previews render correctly side by side).
 */
@Composable
fun ThemeSwitcher(
    selected: RushThemeVariant,
    onSelect: (RushThemeVariant) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeColors = RushThemeAccess.colors

    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        items(RushThemeVariant.values().toList()) { variant ->
            val preview = schemeFor(variant)
            val isSelected = variant == selected

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onSelect(variant) }
            ) {
                Box(
                    modifier = Modifier
                        .width(84.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(preview.background)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) preview.accentGlow else preview.glassBorder,
                            shape = RoundedCornerShape(18.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(30.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(preview.accent)
                    )
                }
                Text(
                    text = variant.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() },
                    color = if (isSelected) activeColors.accentGlow else activeColors.textSecondary,
                    style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}
