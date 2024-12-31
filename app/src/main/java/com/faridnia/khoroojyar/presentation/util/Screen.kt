package com.faridnia.khoroojyar.presentation.util

sealed class Screen(val route: String) {
    data object ExitTimeCalculatorScreen : Screen("screen_exit_time_calculator")
    data object Settings : Screen("screen_settings")
    data object Analytics : Screen("screen_analytics")
}