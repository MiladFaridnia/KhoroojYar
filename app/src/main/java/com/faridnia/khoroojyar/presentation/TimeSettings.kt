package com.faridnia.khoroojyar.presentation

import java.time.Duration
import java.time.LocalTime

data class TimeSettings(
    val workDuration: Duration = Duration.ofMinutes(525),
    val earliestStart: LocalTime? = LocalTime.of(7, 0),
    val latestStart: LocalTime = LocalTime.of(9, 0),
    val earliestEnd: LocalTime = LocalTime.of(16, 45),
    val latestEnd: LocalTime = LocalTime.of(17, 45)
)
