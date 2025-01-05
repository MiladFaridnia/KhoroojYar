package com.faridnia.khoroojyar.util

import java.time.Duration
import java.time.LocalTime
import java.util.Locale

fun Duration.toFormattedString(): String {
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

fun String.toLocalTime(): LocalTime? {
    return try {
        val parts = split(":").map { it.toInt() }
        if (parts.size == 2) {
            LocalTime.of(parts[0], parts[1])
        } else {
            null // Invalid format
        }
    } catch (e: Exception) {
        null // Handle invalid input
    }
}

fun Duration.toLocalTime(): LocalTime {
    return LocalTime.ofSecondOfDay(toSeconds())
}