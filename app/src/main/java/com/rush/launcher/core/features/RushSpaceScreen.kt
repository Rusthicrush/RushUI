package com.rush.launcher.core.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rush.launcher.core.components.GlassPanel
import com.rush.launcher.core.components.NeonButton
import com.rush.launcher.core.theme.RushThemeAccess

data class VaultItem(val name: String, val isApp: Boolean)

/**
 * Rush Space — PIN/biometric-gated private area for hidden apps and files.
 * This screen only renders the LOCKED state UI + the unlocked content list;
 * the actual auth check (BiometricPrompt or PIN compare) happens in the
 * caller and flips `isUnlocked` — never store or compare the PIN here.
 */
@Composable
fun RushSpaceScreen(
    isUnlocked: Boolean,
    items: List<VaultItem>,
    onUnlockAttempt: (pin: String) -> Unit,
    onLock: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = RushThemeAccess.colors

    Column(
        modifier = modifier.fillMaxSize().background(colors.background).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Rush Space", color = colors.textPrimary, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            if (isUnlocked) {
                NeonButton(label = "Lock", onClick = onLock)
            }
        }

        if (!isUnlocked) {
            RushSpaceLockPrompt(onUnlockAttempt = onUnlockAttempt)
        } else {
            items.forEach { item ->
                GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 16.dp) {
                    Text(
                        text = item.name,
                        color = colors.textPrimary,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun RushSpaceLockPrompt(onUnlockAttempt: (String) -> Unit) {
    val colors = RushThemeAccess.colors
    var pin by remember { mutableStateOf("") }

    GlassPanel(modifier = Modifier.fillMaxWidth(), cornerRadius = 20.dp) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Enter PIN to unlock", color = colors.textSecondary)
            androidx.compose.foundation.text.BasicTextField(
                value = pin,
                onValueChange = { pin = it },
                visualTransformation = PasswordVisualTransformation(),
                textStyle = androidx.compose.ui.text.TextStyle(color = colors.textPrimary),
                singleLine = true
            )
            NeonButton(label = "Unlock", onClick = { onUnlockAttempt(pin) })
        }
    }
}
