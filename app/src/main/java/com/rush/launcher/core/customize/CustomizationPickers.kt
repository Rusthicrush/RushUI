package com.rush.launcher.core.customize

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

data class WallpaperOption(val id: String, val name: String, val thumbnail: Painter, val isDynamic: Boolean)
data class IconPackOption(val id: String, val name: String, val previewIcon: Painter)
data class FontOption(val id: String, val displayName: String)
data class AodStyleOption(val id: String, val name: String, val preview: Painter)

/** Horizontal wallpaper picker — static or dynamic (animated) wallpapers, tagged in the corner. */
@Composable
fun WallpaperPicker(
    options: List<WallpaperOption>,
    selectedId: String,
    onSelect: (WallpaperOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(options, key = { it.id }) { option ->
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = if (option.id == selectedId) 2.dp else 0.dp,
                        color = colors.accentGlow,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelect(option) }
            ) {
                Image(
                    painter = option.thumbnail,
                    contentDescription = option.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(90.dp).height(160.dp)
                )
                if (option.isDynamic) {
                    Text(
                        "LIVE",
                        color = colors.accentGlow,
                        style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .padding(6.dp)
                            .background(colors.background.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

/** Icon pack picker — swaps every app icon system-wide via the launcher's icon resolver. */
@Composable
fun IconPackPicker(
    options: List<IconPackOption>,
    selectedId: String,
    onSelect: (IconPackOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        items(options, key = { it.id }) { option ->
            Box(
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colors.glassFill)
                    .border(
                        width = if (option.id == selectedId) 2.dp else 1.dp,
                        color = if (option.id == selectedId) colors.accentGlow else colors.glassBorder,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable { onSelect(option) }
            ) {
                Image(painter = option.previewIcon, contentDescription = option.name, modifier = Modifier.padding(10.dp))
            }
        }
    }
}

/** Font picker — simple selectable list, applies system-wide across Rush UI + companion apps. */
@Composable
fun FontPicker(
    options: List<FontOption>,
    selectedId: String,
    onSelect: (FontOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(options, key = { it.id }) { option ->
            val isSelected = option.id == selectedId
            Text(
                text = option.displayName,
                color = if (isSelected) colors.background else colors.textPrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) colors.accent else colors.glassFill)
                    .clickable { onSelect(option) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

/** Always On Display style picker — thumbnail previews of each AOD layout. */
@Composable
fun AodStylePicker(
    options: List<AodStyleOption>,
    selectedId: String,
    onSelect: (AodStyleOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(options, key = { it.id }) { option ->
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.surface)
                    .border(
                        width = if (option.id == selectedId) 2.dp else 1.dp,
                        color = if (option.id == selectedId) colors.accentGlow else colors.glassBorder,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelect(option) }
            ) {
                Image(painter = option.preview, contentDescription = option.name, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
