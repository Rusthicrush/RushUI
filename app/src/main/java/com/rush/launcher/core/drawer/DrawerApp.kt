package com.rush.launcher.core.drawer

import androidx.compose.ui.graphics.painter.Painter

enum class AppCategory { GAMES, SOCIAL, PRODUCTIVITY, MEDIA, TOOLS, SYSTEM, OTHER }

enum class DrawerSort { NAME_AZ, NAME_ZA, MOST_USED, RECENTLY_INSTALLED, CATEGORY }

data class DrawerApp(
    val packageName: String,
    val label: String,
    val icon: Painter,
    val category: AppCategory,
    val isFavorite: Boolean = false,
    val usageScore: Int = 0,        // higher = opened more often, drives MOST_USED sort
    val installedAtMillis: Long = 0L,
    val onClick: () -> Unit
)

fun List<DrawerApp>.sortedBy(mode: DrawerSort): List<DrawerApp> = when (mode) {
    DrawerSort.NAME_AZ -> sortedBy { it.label.lowercase() }
    DrawerSort.NAME_ZA -> sortedByDescending { it.label.lowercase() }
    DrawerSort.MOST_USED -> sortedByDescending { it.usageScore }
    DrawerSort.RECENTLY_INSTALLED -> sortedByDescending { it.installedAtMillis }
    DrawerSort.CATEGORY -> sortedBy { it.category.ordinal }
}
