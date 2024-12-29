package com.faridnia.khoroojyar.presentation

import java.time.LocalTime

data class ExitTimeState(
    val enterTime: LocalTime? = null,
    val exitTime: LocalTime? = null,
    val timeOffList: List<TimeSegment> = emptyList(),
    val timeWorked: TimeSegment? = null,
    val overtime: TimeSegment? = null,
    val canExitTime: LocalTime? = null
) {
    fun isExitTimeEntered() = exitTime?.isAfter(LocalTime.MIN) ?: false
}
