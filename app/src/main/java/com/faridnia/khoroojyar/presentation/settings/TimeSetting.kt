package com.faridnia.khoroojyar.presentation.settings

import androidx.annotation.StringRes
import com.faridnia.khoroojyar.R
import java.time.Duration
import java.time.LocalTime

data class TimeSetting<T>(
    @StringRes val titleId: Int,
    val type: SettingType,
    val value: T? = null,
) {
    companion object {
        val timeItems = listOf(
            TimeSetting<LocalTime>(titleId = R.string.earliest_start, type = SettingType.EARLIEST_START),
            TimeSetting(titleId = R.string.latest_start, type = SettingType.LATEST_START)
        )
        val durationItems = listOf(
            TimeSetting<Duration>(titleId = R.string.work_duration, type = SettingType.WORK_DURATION)
        )
    }
}

enum class SettingType {
    EARLIEST_START,
    LATEST_START,
    WORK_DURATION
}
