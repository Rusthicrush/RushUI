package com.rush.launcher.core.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.rush.launcher.core.theme.RushThemeAccess
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

/**
 * RUSH Clock — large futuristic time display for the home screen.
 * Updates every second via LaunchedEffect; no AlarmManager/broadcast needed
 * since it's only visible while the launcher is on screen.
 */
@Composable
fun RushClock(
    modifier: Modifier = Modifier,
    show24Hour: Boolean = true
) {
    val colors = RushThemeAccess.colors
    var time by remember { mutableStateOf(currentTimeParts(show24Hour)) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        while (true) {
            time = currentTimeParts(show24Hour)
            delay(1000L - (System.currentTimeMillis() % 1000L)) // resync to the second boundary
        }
    }

    Column(modifier = modifier) {
        Text(
            text = time.hourMinute,
            style = MaterialTheme.typography.displayMedium,
            color = colors.textPrimary,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = time.dateLine,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary
        )
    }
}

private data class ClockParts(val hourMinute: String, val dateLine: String)

private fun currentTimeParts(show24Hour: Boolean): ClockParts {
    val now = Calendar.getInstance().time
    val timeFmt = SimpleDateFormat(if (show24Hour) "HH:mm" else "hh:mm a", Locale.getDefault())
    val dateFmt = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    return ClockParts(timeFmt.format(now), dateFmt.format(now))
}
