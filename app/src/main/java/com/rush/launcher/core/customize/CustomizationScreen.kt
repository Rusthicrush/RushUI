package com.rush.launcher.core.customize

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess
import com.rush.launcher.core.theme.RushThemeVariant

data class CustomizationState(
    val selectedTheme: RushThemeVariant,
    val wallpapers: List<WallpaperOption>,
    val selectedWallpaperId: String,
    val iconPacks: List<IconPackOption>,
    val selectedIconPackId: String,
    val fonts: List<FontOption>,
    val selectedFontId: String,
    val aodStyles: List<AodStyleOption>,
    val selectedAodStyleId: String
)

/**
 * RUSH Customization screen — one scrollable hub for everything under
 * Settings > Customization. Each section is self-contained; changes call back
 * immediately (no "Apply" button) since Rush UI applies changes live.
 */
@Composable
fun CustomizationScreen(
    state: CustomizationState,
    onThemeChange: (RushThemeVariant) -> Unit,
    onWallpaperChange: (WallpaperOption) -> Unit,
    onIconPackChange: (IconPackOption) -> Unit,
    onFontChange: (FontOption) -> Unit,
    onAodStyleChange: (AodStyleOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        item { CustomizationSection("Theme") { ThemeSwitcher(state.selectedTheme, onThemeChange) } }
        item { CustomizationSection("Wallpaper") { WallpaperPicker(state.wallpapers, state.selectedWallpaperId, onWallpaperChange) } }
        item { CustomizationSection("Icon Pack") { IconPackPicker(state.iconPacks, state.selectedIconPackId, onIconPackChange) } }
        item { CustomizationSection("Font") { FontPicker(state.fonts, state.selectedFontId, onFontChange) } }
        item { CustomizationSection("Always On Display") { AodStylePicker(state.aodStyles, state.selectedAodStyleId, onAodStyleChange) } }
    }
}

@Composable
private fun CustomizationSection(title: String, content: @Composable () -> Unit) {
    val colors = RushThemeAccess.colors
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        content()
    }
}
