package com.rush.launcher.core.theme

import androidx.compose.ui.graphics.Color

/**
 * RUSH UI — Core Color System
 * "Fast. Smart. Limitless."
 *
 * Every theme shares the same structural roles (bg, surface, glass, accent,
 * accentGlow, metallic, text) so components never hardcode a color —
 * they always pull from RushColors.current (see RushTheme.kt).
 */

// ---------- Base neutrals shared across themes ----------
val RushPureBlack = Color(0xFF000000)
val RushVoid = Color(0xFF060608)
val RushCarbon = Color(0xFF0D0F12)
val RushSteel = Color(0xFF1A1D22)
val RushMist = Color(0xFFE6EAF0)

// ---------- Neon Blue (default / flagship) ----------
object NeonBlueTheme {
    val background = RushVoid
    val surface = RushCarbon
    val glassFill = Color(0x1A2E8CFF)      // translucent neon blue for glass panels
    val glassBorder = Color(0x662E8CFF)
    val accent = Color(0xFF2E8CFF)          // primary neon blue
    val accentGlow = Color(0xFF5FD4FF)      // brighter cyan-blue glow
    val metallic = Color(0xFF8A97A8)        // brushed steel highlight
    val metallicDark = Color(0xFF3B4552)
    val textPrimary = RushMist
    val textSecondary = Color(0xFF9AA6B8)
    val danger = Color(0xFFFF4D6D)
    val success = Color(0xFF2EFFC1)
}

// ---------- AMOLED Black ----------
object AmoledBlackTheme {
    val background = RushPureBlack
    val surface = Color(0xFF0A0A0A)
    val glassFill = Color(0x14FFFFFF)
    val glassBorder = Color(0x33FFFFFF)
    val accent = Color(0xFF3D7FFF)
    val accentGlow = Color(0xFF6FE0FF)
    val metallic = Color(0xFF6E6E6E)
    val metallicDark = Color(0xFF1C1C1C)
    val textPrimary = Color(0xFFF5F5F5)
    val textSecondary = Color(0xFF8C8C8C)
    val danger = Color(0xFFFF4D6D)
    val success = Color(0xFF2EFFC1)
}

// ---------- Luxury Gold ----------
object LuxuryGoldTheme {
    val background = Color(0xFF0B0906)
    val surface = Color(0xFF14100A)
    val glassFill = Color(0x1AF2C879)
    val glassBorder = Color(0x66D4A954)
    val accent = Color(0xFFE3B25C)           // champagne gold
    val accentGlow = Color(0xFFFFE1A8)
    val metallic = Color(0xFFC9AE7E)         // brushed gold
    val metallicDark = Color(0xFF4A3E28)
    val textPrimary = Color(0xFFF7EFDD)
    val textSecondary = Color(0xFFB5A583)
    val danger = Color(0xFFFF5C6C)
    val success = Color(0xFFB9E38C)
}

// ---------- Titanium ----------
object TitaniumTheme {
    val background = Color(0xFF101216)
    val surface = Color(0xFF181B20)
    val glassFill = Color(0x1AAEB6C2)
    val glassBorder = Color(0x66AEB6C2)
    val accent = Color(0xFF9DA7B3)            // brushed titanium
    val accentGlow = Color(0xFFD6DEE8)
    val metallic = Color(0xFFBFC6D1)
    val metallicDark = Color(0xFF3A3F47)
    val textPrimary = Color(0xFFEDEFF2)
    val textSecondary = Color(0xFF9199A6)
    val danger = Color(0xFFFF4D6D)
    val success = Color(0xFF7FE0C4)
}

/** Unified role set every theme maps into. Components reference THIS, never a theme object directly. */
data class RushColorScheme(
    val background: Color,
    val surface: Color,
    val glassFill: Color,
    val glassBorder: Color,
    val accent: Color,
    val accentGlow: Color,
    val metallic: Color,
    val metallicDark: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val danger: Color,
    val success: Color,
)

enum class RushThemeVariant { NEON_BLUE, AMOLED_BLACK, LUXURY_GOLD, TITANIUM }

fun schemeFor(variant: RushThemeVariant): RushColorScheme = when (variant) {
    RushThemeVariant.NEON_BLUE -> with(NeonBlueTheme) {
        RushColorScheme(background, surface, glassFill, glassBorder, accent, accentGlow, metallic, metallicDark, textPrimary, textSecondary, danger, success)
    }
    RushThemeVariant.AMOLED_BLACK -> with(AmoledBlackTheme) {
        RushColorScheme(background, surface, glassFill, glassBorder, accent, accentGlow, metallic, metallicDark, textPrimary, textSecondary, danger, success)
    }
    RushThemeVariant.LUXURY_GOLD -> with(LuxuryGoldTheme) {
        RushColorScheme(background, surface, glassFill, glassBorder, accent, accentGlow, metallic, metallicDark, textPrimary, textSecondary, danger, success)
    }
    RushThemeVariant.TITANIUM -> with(TitaniumTheme) {
        RushColorScheme(background, surface, glassFill, glassBorder, accent, accentGlow, metallic, metallicDark, textPrimary, textSecondary, danger, success)
    }
}
