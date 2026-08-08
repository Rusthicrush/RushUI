package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.theme.RushThemeAccess

data class AutomationRule(
    val id: String,
    val trigger: String,   // e.g. "When Wi-Fi connects to Home"
    val action: String,    // e.g. "Enable Battery Saver"
    val isEnabled: Boolean
)

/**
 * Rush Automation — list of trigger→action rules (IFTTT-style), each toggleable.
 * Rule *creation* would open a separate builder flow; this screen is the list +
 * enable/disable + "add new" entry point.
 */
@Composable
fun RushAutomationScreen(
    rules: List<AutomationRule>,
    onToggleRule: (AutomationRule, Boolean) -> Unit,
    onAddRule: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier.fillMaxSize().background(colors.background).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Rush Automation", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add rule",
                tint = colors.accentGlow,
                modifier = Modifier.clickable(onClick = onAddRule)
            )
        }

        rules.forEach { rule ->
            GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 18.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(rule.trigger, color = colors.textPrimary)
                        Text("→ ${rule.action}", color = colors.textSecondary, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                    }
                    Switch(
                        checked = rule.isEnabled,
                        onCheckedChange = { onToggleRule(rule, it) },
                        colors = SwitchDefaults.colors(checkedThumbColor = colors.accentGlow, checkedTrackColor = colors.accent)
                    )
                }
            }
        }

        if (rules.isEmpty()) {
            Text("No automations yet. Tap + to create one.", color = colors.textSecondary)
        }
    }
}
