package com.rush.launcher.core.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.theme.RushThemeAccess

/** Row of favorited apps, pinned above the category chips. Empty state renders nothing. */
@Composable
fun FavoritesRow(
    apps: List<DrawerApp>,
    modifier: Modifier = Modifier
) {
    if (apps.isEmpty()) return
    val colors = RushThemeAccess.colors

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(apps.filter { it.isFavorite }, key = { it.packageName }) { app ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(onClick = app.onClick)
            ) {
                Image(painter = app.icon, contentDescription = app.label, modifier = Modifier.size(48.dp))
                Text(text = app.label, color = colors.textSecondary, style = androidx.compose.material3.MaterialTheme.typography.labelSmall)
            }
        }
    }
}
