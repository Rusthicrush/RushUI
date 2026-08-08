package com.rush.launcher.core.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

/** Horizontal scrollable category chips: All + one per AppCategory. Selecting one filters the grid. */
@Composable
fun CategoryChipRow(
    selected: AppCategory?,
    onSelect: (AppCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    val categories = listOf(null) + AppCategory.values().toList()

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selected
            Text(
                text = category?.name?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "All",
                color = if (isSelected) colors.background else colors.textPrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) colors.accent else colors.glassFill)
                    .clickable { onSelect(category) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
