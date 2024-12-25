package com.faridnia.khoroojyar.domain.use_case

import com.faridnia.khoroojyar.data.room.WorkDayInfo
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class CalculateOvertimeUseCase @Inject constructor() {

    suspend operator fun invoke(workDayInfo: WorkDayInfo): Duration {
        // Calculate working duration
        val firstShiftDuration = calculateShiftDuration(
            workDayInfo.firstEnterTime,
            workDayInfo.firstExitTime
        )

        val secondShiftDuration = calculateShiftDuration(
            workDayInfo.secondEnterTime,
            workDayInfo.secondExitTime
        )

        val totalWorkingDuration = firstShiftDuration.plus(secondShiftDuration)

        // Standard work duration (8 hours 45 minutes)
        val standardWorkDuration = Duration.ofHours(8).plusMinutes(45)

        // Calculate overtime
        return if (totalWorkingDuration > standardWorkDuration) {
            totalWorkingDuration.minus(standardWorkDuration)
        } else {
            Duration.ZERO // No overtime if total duration is less than or equal to standard
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
