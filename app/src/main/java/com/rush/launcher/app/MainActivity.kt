package com.rush.launcher.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rush.launcher.core.theme.RushTheme
import com.rush.launcher.core.theme.RushThemeVariant

/**
 * RUSH UI — single-activity entry point. This Activity is what gets declared
 * with the HOME/LAUNCHER intent filters in AndroidManifest.xml so Android
 * offers it as a home-screen replacement.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // TODO: replace NEON_BLUE with the persisted theme choice (DataStore/SharedPreferences)
            RushTheme(variant = RushThemeVariant.NEON_BLUE) {
                RushNavHost()
            }
        }
    }
}
