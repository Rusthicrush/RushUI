package com.rush.launcher.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LocalRushColors = compositionLocalOf { schemeFor(RushThemeVariant.NEON_BLUE) }
val LocalRushMotion = compositionLocalOf { RushMotion }

/**
 * Root theme wrapper. Wrap RushUI's setContent { } with this once at the top —
 * every screen and component reads colors via RushTheme.colors, never hardcodes hex.
 */
@Composable
fun RushTheme(
    variant: RushThemeVariant = RushThemeVariant.NEON_BLUE,
    content: @Composable () -> Unit
) {
    val scheme = schemeFor(variant)
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as? android.app.Activity)?.window
        window?.let {
            it.statusBarColor = scheme.background.toArgb()
            it.navigationBarColor = scheme.background.toArgb()
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = false
        }
    }

    CompositionLocalProvider(
        LocalRushColors provides scheme,
        LocalRushMotion provides RushMotion
    ) {
        MaterialTheme(
            colorScheme = darkColorScheme(
                primary = scheme.accent,
                background = scheme.background,
                surface = scheme.surface,
                onPrimary = scheme.textPrimary,
                onBackground = scheme.textPrimary,
                onSurface = scheme.textPrimary,
                error = scheme.danger,
            ),
            typography = RushTypography,
            content = content
        )
    }
}

/** Convenience accessor: RushTheme.colors.accent instead of LocalRushColors.current.accent */
object RushThemeAccess {
    val colors: RushColorScheme
        @Composable get() = LocalRushColors.current
    val motion: RushMotionSpec
        @Composable get() = LocalRushMotion.current
}
