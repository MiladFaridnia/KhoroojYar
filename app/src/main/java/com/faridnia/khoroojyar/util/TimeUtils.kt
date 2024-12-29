package com.faridnia.khoroojyar.util

import java.time.Duration
import java.time.LocalTime
import java.util.Locale

fun Duration.formatDuration(): String {
    val hours = toHours()
    val minutes = toMinutes() % 60
    return String.format(Locale.US, "%02d:%02d", hours, minutes)
}

fun LocalTime?.toFormattedString(): String {
    val selectedHour = this?.hour ?: 0
    val selectedMinute = this?.minute ?: 0
    val selectedTime =
        String.format(Locale.US, "%02d:%02d", selectedHour, selectedMinute)
    return selectedTime
}