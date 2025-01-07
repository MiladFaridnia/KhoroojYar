package com.faridnia.khoroojyar.presentation.exit_time_calculator

import java.time.LocalTime

data class ExitTimeState(
    val enterTime: LocalTime? = null,
    val exitTime: LocalTime? = null,
    val timeOffList: List<TimeSegment> = emptyList(),
    val timeWorked: TimeSegment? = null,
    val overtime: TimeSegment? = null,
    val canExitTime: LocalTime? = null
)