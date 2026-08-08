package com.rush.launcher.core.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween

/**
 * RUSH UI Motion System
 * Tuned for perceived 120Hz smoothness: short durations, low-overshoot springs,
 * and an easing curve ("rushEase") used everywhere instead of default FastOutSlowIn.
 */
data class RushMotionSpec(
    val fast: Int = 120,        // taps, toggles, icon presses
    val standard: Int = 220,    // panel open/close, dock reveal
    val slow: Int = 360,        // screen transitions, app-drawer reveal
    val easing: CubicBezierEasing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f), // snappy-in, smooth-out
)

val RushMotion = RushMotionSpec()

/** Standard tween for UI state changes (color, alpha, size) */
fun <T> rushTween(durationMillis: Int = RushMotion.standard) =
    tween<T>(durationMillis = durationMillis, easing = RushMotion.easing)

/** Spring for anything that should feel physical: dock bounce, card drag-release, gesture snap-back */
fun <T> rushSpring(bouncy: Boolean = false) = spring<T>(
    dampingRatio = if (bouncy) Spring.DampingRatioMediumBouncy else Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMediumLow
)
