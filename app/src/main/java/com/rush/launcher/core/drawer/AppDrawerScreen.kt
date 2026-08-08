package com.rush.launcher.core.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

/**
 * RUSH App Drawer — opened via swipe-up from the Home Screen.
 * Fully local filtering/sorting; no network, no AI ranking — pure list ops
 * on whatever `allApps` the caller provides (from PackageManager query).
 */
@Composable
fun AppDrawerScreen(
    allApps: List<DrawerApp>,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<AppCategory?>(null) }
    var sortMode by remember { mutableStateOf(DrawerSort.NAME_AZ) }
    var sortMenuOpen by remember { mutableStateOf(false) }

    val filtered = remember(allApps, query, selectedCategory, sortMode) {
        allApps
            .filter { app -> query.isBlank() || app.label.contains(query, ignoreCase = true) }
            .filter { app -> selectedCategory == null || app.category == selectedCategory }
            .sortedBy(sortMode)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DrawerSearchBar(
                query = query,
                onQueryChange = { query = it },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { sortMenuOpen = true }) {
                Icon(imageVector = Icons.Filled.List, contentDescription = "Sort", tint = colors.textPrimary)
            }
            DropdownMenu(expanded = sortMenuOpen, onDismissRequest = { sortMenuOpen = false }) {
                DrawerSort.values().forEach { mode ->
                    DropdownMenuItem(
                        text = { Text(mode.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() }) },
                        onClick = { sortMode = mode; sortMenuOpen = false }
                    )
                }
            }
        }

        if (query.isBlank()) {
            FavoritesRow(apps = allApps)
            CategoryChipRow(selected = selectedCategory, onSelect = { selectedCategory = it })
        }

        AppGrid(apps = filtered, modifier = Modifier.fillMaxSize())
    }
}
