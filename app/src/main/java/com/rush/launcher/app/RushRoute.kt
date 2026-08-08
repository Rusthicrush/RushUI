package com.rush.launcher.app

/**
 * All screens in the Rush UI app, in one place so nav calls never use raw strings.
 */
sealed class RushRoute(val path: String) {
    data object Lock : RushRoute("lock")
    data object Home : RushRoute("home")
    data object AppDrawer : RushRoute("app_drawer")
    data object ControlCenter : RushRoute("control_center")
    data object Customization : RushRoute("customization")
    data object RushHub : RushRoute("rush_hub")
    data object RushSync : RushRoute("rush_sync")
    data object RushSpace : RushRoute("rush_space")
    data object RushAutomation : RushRoute("rush_automation")
    data object GameTurboSettings : RushRoute("game_turbo_settings")
    data object RushFiles : RushRoute("rush_files")
    data object MultiDevice : RushRoute("multi_device")
}
