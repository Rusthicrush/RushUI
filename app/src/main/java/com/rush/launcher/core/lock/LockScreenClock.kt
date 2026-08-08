package com.rush.launcher.core.lock

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rush.launcher.core.theme.RushThemeAccess
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

enum class LockClockStyle { MINIMAL, BOLD_DISPLAY, SPLIT_DIGITS, ORBIT }

/**
 * Lock Screen clock. Distinct from RushClock (home screen) because lock screen
 * styles are user-selectable in Customization > Always On Display / Lock Screen,
 * and each style needs its own layout, not just a font-size change.
 */
@Composable
fun LockScreenClock(
    style: LockClockStyle,
    modifier: Modifier = Modifier,
    show24Hour: Boolean = true
) {
    val colors = RushThemeAccess.colors
    var time by remember { mutableStateOf(currentTime(show24Hour)) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        while (true) {
            time = currentTime(show24Hour)
            delay(1000L - (System.currentTimeMillis() % 1000L))
        }
    }

    when (style) {
        LockClockStyle.MINIMAL -> Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(time, fontSize = 64.sp, fontWeight = FontWeight.Light, color = colors.textPrimary)
        }
        LockClockStyle.BOLD_DISPLAY -> Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(time, fontSize = 88.sp, fontWeight = FontWeight.Bold, color = colors.textPrimary)
        }
        LockClockStyle.SPLIT_DIGITS -> Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            val parts = time.split(":")
            Text(parts.getOrElse(0) { "" }, fontSize = 72.sp, color = colors.accentGlow, fontWeight = FontWeight.Bold)
            Text(parts.getOrElse(1) { "" }, fontSize = 72.sp, color = colors.textPrimary, fontWeight = FontWeight.Light)
        }
        LockClockStyle.ORBIT -> Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(time, fontSize = 56.sp, color = colors.textPrimary, fontWeight = FontWeight.Medium)
            Text("• RUSH •", fontSize = 12.sp, color = colors.accent, modifier = Modifier)
        }
    }
}

private fun currentTime(show24Hour: Boolean): String {
    val fmt = SimpleDateFormat(if (show24Hour) "HH:mm" else "hh:mm", Locale.getDefault())
    return fmt.format(Calendar.getInstance().time)
}
