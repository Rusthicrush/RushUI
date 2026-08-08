package com.rush.launcher.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val RushDisplayFont = FontFamily.SansSerif
val RushBodyFont = FontFamily.SansSerif

val RushTypography = Typography(
    displayLarge = TextStyle(fontFamily = RushDisplayFont, fontWeight = FontWeight.Bold, fontSize = 57.sp, letterSpacing = 0.5.sp),
    displayMedium = TextStyle(fontFamily = RushDisplayFont, fontWeight = FontWeight.Medium, fontSize = 45.sp, letterSpacing = 1.sp),
    headlineLarge = TextStyle(fontFamily = RushDisplayFont, fontWeight = FontWeight.Bold, fontSize = 32.sp),
    headlineMedium = TextStyle(fontFamily = RushBodyFont, fontWeight = FontWeight.Bold, fontSize = 24.sp),
    titleLarge = TextStyle(fontFamily = RushBodyFont, fontWeight = FontWeight.Medium, fontSize = 20.sp),
    bodyLarge = TextStyle(fontFamily = RushBodyFont, fontWeight = FontWeight.Normal, fontSize = 16.sp, letterSpacing = 0.2.sp),
    bodyMedium = TextStyle(fontFamily = RushBodyFont, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelSmall = TextStyle(fontFamily = RushBodyFont, fontWeight = FontWeight.Medium, fontSize = 11.sp, letterSpacing = 0.5.sp),
)
