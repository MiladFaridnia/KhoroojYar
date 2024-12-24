package com.faridnia.khoroojyar.presentation

import java.time.LocalTime
import java.util.Locale

data class ExitTimeState(
    val exitTime: LocalTime? = null,
    val enterTime: LocalTime? = null,
    val vacationList: List<TimeSegment> = emptyList(),
    val timeWorked: TimeSegment? = null,
    val overtime: TimeSegment? = null
) {
    fun isExitTimeEntered() = exitTime?.isAfter(LocalTime.MIN) ?: false
}

fun LocalTime?.toFormattedString(): String {
    val selectedHour = this?.hour ?: 0
    val selectedMinute = this?.minute ?: 0
    val selectedTime =
        String.format(Locale.US, "%02d:%02d", selectedHour, selectedMinute)
    return selectedTime
}
