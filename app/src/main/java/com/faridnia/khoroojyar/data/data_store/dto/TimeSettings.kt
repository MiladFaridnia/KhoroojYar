package com.faridnia.khoroojyar.data.data_store.dto

import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.LocalTime

@Serializable
data class TimeSettings(
    @Serializable(with = DurationSerializer::class)
    val workDuration: Duration = Duration.ofHours(8).plusMinutes(45),

    @Serializable(with = LocalTimeSerializer::class)
    val earliestStart: LocalTime = LocalTime.of(7, 0),

    @Serializable(with = LocalTimeSerializer::class)
    val latestStart: LocalTime = LocalTime.of(9, 0)
) {
    val earliestEnd: LocalTime
        get() = earliestStart.plus(workDuration)

    val latestEnd: LocalTime
        get() = latestStart.plus(workDuration)
}