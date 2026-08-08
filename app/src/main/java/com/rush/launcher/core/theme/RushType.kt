package com.rush.launcher.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rush.launcher.R

/**
 * Font: bundle a geometric/futuristic variable font (e.g. "Orbitron" for display sizes,
 * a clean grotesk like "Sora" or "Space Grotesk" for body text — both free/OFL licensed).
 * Drop the .ttf files into res/font/ with these exact names, or swap the resource ids.
 */
val RushDisplayFont = FontFamily(
    Font(R.font.orbitron_regular, FontWeight.Normal),
    Font(R.font.orbitron_medium, FontWeight.Medium),
    Font(R.font.orbitron_bold, FontWeight.Bold),
)

val RushBodyFont = FontFamily(
    Font(R.font.space_grotesk_regular, FontWeight.Normal),
    Font(R.font.space_grotesk_medium, FontWeight.Medium),
    Font(R.font.space_grotesk_bold, FontWeight.Bold),
)

val RushTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = RushDisplayFont,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        letterSpacing = 0.5.sp
    ),
    // Used for the home screen clock
    displayMedium = TextStyle(
        fontFamily = RushDisplayFont,
        fontWeight = FontWeight.Medium,
        fontSize = 45.sp,
        letterSpacing = 1.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = RushDisplayFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = RushBodyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = RushBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RushBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RushBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = RushBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
    ),
)
