package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

enum class FileEntryType { FOLDER, IMAGE, VIDEO, AUDIO, DOCUMENT, APK, OTHER }

data class FileEntry(
    val name: String,
    val type: FileEntryType,
    val sizeBytes: Long,
    val path: String,
    val onClick: () -> Unit
)

data class StorageSummary(val usedBytes: Long, val totalBytes: Long)

/**
 * Rush Files — Advanced File Manager. `entries` reflects the current directory
 * (caller supplies breadcrumb/back navigation); this screen renders one level.
 * All file I/O (java.io.File / MediaStore / SAF) stays outside this composable.
 */
@Composable
fun RushFileManagerScreen(
    currentPath: String,
    entries: List<FileEntry>,
    storage: StorageSummary,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier.fillMaxSize().background(colors.background).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Rush Files", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)

        GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 16.dp) {
            val usedPercent = if (storage.totalBytes > 0) (storage.usedBytes * 100 / storage.totalBytes).toInt() else 0
            Row(
                modifier = Modifier.fillMaxWidth().padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Storage", color = colors.textSecondary)
                Text("$usedPercent% used", color = colors.textPrimary)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = onNavigateUp)) {
            Text(currentPath.ifBlank { "/" }, color = colors.accent)
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(entries, key = { it.path }) { entry ->
                GlassPanel(modifier = Modifier.fillMaxWidth().clickable(onClick = entry.onClick), cornerRadius = 14.dp) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (entry.type == FileEntryType.FOLDER) Icons.Filled.List else Icons.Filled.Settings,
                            contentDescription = entry.type.name,
                            tint = colors.textSecondary
                        )
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(entry.name, color = colors.textPrimary)
                            if (entry.type != FileEntryType.FOLDER) {
                                Text(formatBytes(entry.sizeBytes), color = colors.textSecondary, style = androidx.compose.material3.MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatBytes(bytes: Long): String {
    if (bytes < 1024) return "$bytes B"
    val kb = bytes / 1024.0
    if (kb < 1024) return "%.1f KB".format(kb)
    val mb = kb / 1024.0
    if (mb < 1024) return "%.1f MB".format(mb)
    return "%.1f GB".format(mb / 1024.0)
}
