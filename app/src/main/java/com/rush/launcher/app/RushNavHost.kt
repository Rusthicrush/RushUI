package com.rush.launcher.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rush.launcher.core.control.ControlCenterScreen
import com.rush.launcher.core.control.ControlCenterState
import com.rush.launcher.core.control.DeviceStats
import com.rush.launcher.core.customize.CustomizationScreen
import com.rush.launcher.core.customize.CustomizationState
import com.rush.launcher.core.drawer.AppDrawerScreen
import com.rush.launcher.core.drawer.DrawerApp
import com.rush.launcher.core.features.AutomationRule
import com.rush.launcher.core.features.GameTurboSettings
import com.rush.launcher.core.features.GameTurboSettingsScreen
import com.rush.launcher.core.features.MultiDeviceScreen
import com.rush.launcher.core.features.PairedDevice
import com.rush.launcher.core.features.RushAutomationScreen
import com.rush.launcher.core.features.RushHubScreen
import com.rush.launcher.core.features.RushHubTool
import com.rush.launcher.core.features.RushSpaceScreen
import com.rush.launcher.core.features.RushSyncScreen
import com.rush.launcher.core.features.SyncedDevice
import com.rush.launcher.core.home.DockApp
import com.rush.launcher.core.home.RushHomeScreen
import com.rush.launcher.core.lock.LockClockStyle
import com.rush.launcher.core.lock.RushLockScreen
import com.rush.launcher.core.theme.RushThemeVariant

/**
 * RUSH UI — root navigation graph.
 *
 * Everything below is wired with local `remember` state so the whole app is
 * click-through demoable immediately. Swap each `remember { mutableStateOf(...) }`
 * for a real ViewModel (backed by PackageManager, WifiManager, BatteryManager,
 * BluetoothAdapter, MediaSessionManager, etc.) as those system integrations
 * get built — the screens themselves don't change.
 */
@Composable
fun RushNavHost(navController: NavHostController = rememberNavController()) {
    var currentTheme by remember { mutableStateOf(RushThemeVariant.NEON_BLUE) }

    NavHost(navController = navController, startDestination = RushRoute.Lock.path) {

        composable(RushRoute.Lock.path) {
            RushLockScreen(
                clockStyle = LockClockStyle.BOLD_DISPLAY,
                isCharging = false,
                batteryPercent = 82,
                nowPlaying = null,
                shortcuts = emptyList(),
                onUnlock = {
                    navController.navigate(RushRoute.Home.path) {
                        popUpTo(RushRoute.Lock.path) { inclusive = true }
                    }
                }
            )
        }

        composable(RushRoute.Home.path) {
            RushHomeScreen(
                dockApps = listOf(
                    DockApp("Phone", Icons.Filled.Call) {},
                    DockApp("Messages", Icons.Filled.Email) {},
                    DockApp("Music", Icons.Filled.PlayArrow) {},
                    DockApp("Settings", Icons.Filled.Settings) { navController.navigate(RushRoute.Customization.path) }
                ),
                onOpenAppDrawer = { navController.navigate(RushRoute.AppDrawer.path) },
                onOpenControlCenter = { navController.navigate(RushRoute.ControlCenter.path) },
                onEnterEditMode = { /* TODO: home-screen edit mode */ }
            )
        }

        composable(RushRoute.AppDrawer.path) {
            AppDrawerScreen(allApps = emptyList<DrawerApp>())
        }

        composable(RushRoute.ControlCenter.path) {
            var state by remember {
                mutableStateOf(
                    ControlCenterState(
                        wifiOn = true, bluetoothOn = false, mobileDataOn = true, flashlightOn = false,
                        brightness = 0.7f, volume = 0.5f, nowPlaying = null,
                        deviceStats = DeviceStats(ramUsedPercent = 54, batteryPercent = 82, batteryTempCelsius = 33.5f, storageUsedPercent = 61),
                        isRecording = false, recordingElapsedSeconds = 0, gamingModeOn = false
                    )
                )
            }
            ControlCenterScreen(
                state = state,
                onToggleWifi = { state = state.copy(wifiOn = !state.wifiOn) },
                onToggleBluetooth = { state = state.copy(bluetoothOn = !state.bluetoothOn) },
                onToggleMobileData = { state = state.copy(mobileDataOn = !state.mobileDataOn) },
                onToggleFlashlight = { state = state.copy(flashlightOn = !state.flashlightOn) },
                onBrightnessChange = { state = state.copy(brightness = it) },
                onVolumeChange = { state = state.copy(volume = it) },
                onPlayPause = { /* TODO: MediaSessionManager */ },
                onNext = { /* TODO */ },
                onPrevious = { /* TODO */ },
                onToggleRecording = { state = state.copy(isRecording = !state.isRecording) },
                onToggleGamingMode = { state = state.copy(gamingModeOn = it) }
            )
        }

        composable(RushRoute.Customization.path) {
            var state by remember {
                mutableStateOf(
                    CustomizationState(
                        selectedTheme = currentTheme,
                        wallpapers = emptyList(), selectedWallpaperId = "",
                        iconPacks = emptyList(), selectedIconPackId = "",
                        fonts = emptyList(), selectedFontId = "",
                        aodStyles = emptyList(), selectedAodStyleId = ""
                    )
                )
            }
            CustomizationScreen(
                state = state,
                onThemeChange = { currentTheme = it; state = state.copy(selectedTheme = it) },
                onWallpaperChange = { state = state.copy(selectedWallpaperId = it.id) },
                onIconPackChange = { state = state.copy(selectedIconPackId = it.id) },
                onFontChange = { state = state.copy(selectedFontId = it.id) },
                onAodStyleChange = { state = state.copy(selectedAodStyleId = it.id) }
            )
        }

        composable(RushRoute.RushHub.path) {
            RushHubScreen(
                tools = listOf(
                    RushHubTool("sync", "Rush Sync", Icons.Filled.Settings) { navController.navigate(RushRoute.RushSync.path) },
                    RushHubTool("space", "Rush Space", Icons.Filled.Settings) { navController.navigate(RushRoute.RushSpace.path) },
                    RushHubTool("automation", "Automation", Icons.Filled.Settings) { navController.navigate(RushRoute.RushAutomation.path) },
                    RushHubTool("turbo", "Game Turbo", Icons.Filled.Settings) { navController.navigate(RushRoute.GameTurboSettings.path) },
                    RushHubTool("files", "Rush Files", Icons.Filled.Settings) { navController.navigate(RushRoute.RushFiles.path) },
                    RushHubTool("devices", "Devices", Icons.Filled.Settings) { navController.navigate(RushRoute.MultiDevice.path) },
                )
            )
        }

        composable(RushRoute.RushSync.path) {
            RushSyncScreen(device = null, onStartPairing = { /* TODO */ }, onDisconnect = { /* TODO */ })
        }

        composable(RushRoute.RushSpace.path) {
            var unlocked by remember { mutableStateOf(false) }
            RushSpaceScreen(
                isUnlocked = unlocked,
                items = emptyList(),
                onUnlockAttempt = { pin -> /* TODO: real auth check */ unlocked = pin.isNotEmpty() },
                onLock = { unlocked = false }
            )
        }

        composable(RushRoute.RushAutomation.path) {
            var rules by remember { mutableStateOf(listOf<AutomationRule>()) }
            RushAutomationScreen(
                rules = rules,
                onToggleRule = { rule, enabled -> rules = rules.map { if (it.id == rule.id) it.copy(isEnabled = enabled) else it } },
                onAddRule = { /* TODO: rule builder flow */ }
            )
        }

        composable(RushRoute.GameTurboSettings.path) {
            var settings by remember {
                mutableStateOf(GameTurboSettings(blockNotifications = true, lockBrightness = true, performanceOverlay = false, autoRejectCalls = false, cpuGpuPriorityBoost = true))
            }
            GameTurboSettingsScreen(settings = settings, onChange = { settings = it })
        }

        composable(RushRoute.RushFiles.path) {
            com.rush.launcher.core.features.RushFileManagerScreen(
                currentPath = "/storage/emulated/0",
                entries = emptyList(),
                storage = com.rush.launcher.core.features.StorageSummary(usedBytes = 0L, totalBytes = 1L),
                onNavigateUp = { /* TODO */ }
            )
        }

        composable(RushRoute.MultiDevice.path) {
            var devices by remember { mutableStateOf(listOf<PairedDevice>()) }
            MultiDeviceScreen(
                devices = devices,
                onSetActive = { device -> devices = devices.map { it.copy(isActive = it.id == device.id) } },
                onAddDevice = { /* TODO: pairing flow */ },
                onRemoveDevice = { device -> devices = devices.filterNot { it.id == device.id } }
            )
        }
    }
}
