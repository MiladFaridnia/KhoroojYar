package com.faridnia.khoroojyar.domain.use_case

import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class CalculateOvertimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(workDayInfo: WorkDayInfo): Duration {
        val timeSettings = dataStoreRepository.getTimeSettings().first()

        val adjustedStartTime = adjustStartTime(
            workDayInfo.firstEnterTime,
            timeSettings.earliestStart,
            timeSettings.latestStart
        )

        val workedDuration = calculateShiftDuration(adjustedStartTime, workDayInfo.firstExitTime)

        val overtime = if (workedDuration > timeSettings.workDuration) {
            workedDuration - timeSettings.workDuration
        } else {
            Duration.ZERO
        }

        return overtime
    }

    private fun adjustStartTime(
        enterTime: LocalTime?,
        earliestStart: LocalTime,
        latestStart: LocalTime
    ): LocalTime? {
        return when {
            enterTime == null -> null
            enterTime.isBefore(earliestStart) -> earliestStart
            enterTime.isAfter(latestStart) -> latestStart
            else -> enterTime
        }
    }

    private fun calculateShiftDuration(startTime: LocalTime?, endTime: LocalTime?): Duration {
        return if (startTime != null && endTime != null) {
            Duration.between(startTime, endTime)
        } else {
            Duration.ZERO
        }
    }
}
