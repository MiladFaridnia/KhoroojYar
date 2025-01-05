package com.faridnia.khoroojyar.presentation.settings

import java.time.Duration
import java.time.LocalTime

data class SettingState(
    val earliestStart: LocalTime? = null,
    val latestStart: LocalTime? = null,
    val workDuration: Duration? = null,
    val isDark: Boolean? = null,
    val currentTimeForTimePicker: LocalTime? = null,
    val showTimePickerDialog: Boolean = false,
    val areNotificationsEnabled: Boolean = false
)