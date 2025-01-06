package com.faridnia.khoroojyar.presentation.settings

sealed class SettingsEvent {
    data class TimeConfirmed(val hour: Int, val minute: Int) : SettingsEvent()
    data object TimeDialogDismissed : SettingsEvent()
    data object EarliestStartClicked : SettingsEvent()
    data object LatestStartClicked : SettingsEvent()
    data object WorkDurationClicked : SettingsEvent()
    data class DarkModeClicked(val isDark: Boolean) : SettingsEvent()
    data class NotificationsToggled(val areEnabled: Boolean) : SettingsEvent()
    data object ProfileClicked : SettingsEvent()
}