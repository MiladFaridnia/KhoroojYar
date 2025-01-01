package com.faridnia.khoroojyar.presentation.exit_time_calculator

import com.faridnia.khoroojyar.util.toFormattedString
import java.time.Duration
import java.time.LocalTime

data class TimeSegment(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val duration: Duration
) {
    val formattedDuration
        get() = duration.toFormattedString()

    val startToEndTime
        get() = "${startTime.toFormattedString()}_${startTime.toFormattedString()}"
}