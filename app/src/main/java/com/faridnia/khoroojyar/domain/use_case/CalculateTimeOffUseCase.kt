package com.faridnia.khoroojyar.domain.use_case

import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import com.faridnia.khoroojyar.presentation.exit_time_calculator.TimeSegment
import com.faridnia.khoroojyar.util.safeLet
import kotlinx.coroutines.flow.first
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class CalculateTimeOffUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(workDayInfo: WorkDayInfo): List<TimeSegment> {
        val timeSettings = dataStoreRepository.getTimeSettings().first()
        val timeOffs = mutableListOf<TimeSegment>()

        val (adjustedStartTime, lateArrivalTimeOff) = calculateLateArrivalTimeOff(
            workDayInfo.firstEnterTime,
            timeSettings.earliestStart,
            timeSettings.latestStart
        )
        lateArrivalTimeOff?.let { timeOffs.add(it) }

        calculateEarlyExitTimeOff(workDayInfo.firstExitTime, timeSettings.earliestEnd)?.let {
            timeOffs.add(it)
        }

        calculateInsufficientWorkDurationTimeOff(
            adjustedStartTime,
            workDayInfo.firstExitTime,
            timeSettings.workDuration,
        )?.let { timeOffs.add(it) }

        return optimizeTimeOffs(timeOffs)
    }

    private fun calculateLateArrivalTimeOff(
        enterTime: LocalTime?,
        earliestStart: LocalTime,
        latestStart: LocalTime
    ): Pair<LocalTime?, TimeSegment?> {
        return when {
            enterTime == null -> Pair(null, null)
            enterTime.isBefore(earliestStart) -> {
                Pair(earliestStart, null)
            }

            enterTime.isAfter(latestStart) -> {
                Pair(
                    latestStart,
                    TimeSegment(
                        startTime = latestStart,
                        endTime = enterTime,
                        duration = Duration.between(latestStart, enterTime)
                    )
                )
            }

            else -> {
                Pair(enterTime, null)
            }
        }
    }

    private fun calculateEarlyExitTimeOff(
        exitTime: LocalTime?,
        earliestEnd: LocalTime
    ): TimeSegment? {
        return exitTime?.takeIf { it.isBefore(earliestEnd) }?.let {
            TimeSegment(
                startTime = it,
                endTime = earliestEnd,
                duration = Duration.between(it, earliestEnd)
            )
        }
    }

    private fun calculateInsufficientWorkDurationTimeOff(
        adjustedStartTime: LocalTime?,
        exitTime: LocalTime?,
        requiredWorkDuration: Duration
    ): TimeSegment? {
        val workedDuration = calculateShiftDuration(adjustedStartTime, exitTime)
        return if (workedDuration < requiredWorkDuration) {
            val missingDuration = requiredWorkDuration - workedDuration
            val missingEndTime = exitTime?.plus(missingDuration)

            safeLet(exitTime, missingEndTime) { exit, missingEnd ->
                TimeSegment(
                    startTime = exit,
                    endTime = missingEnd,
                    duration = missingDuration
                )
            }
        } else {
            null
        }
    }

    private fun calculateShiftDuration(startTime: LocalTime?, endTime: LocalTime?): Duration {
        return if (startTime != null && endTime != null) {
            Duration.between(startTime, endTime)
        } else {
            Duration.ZERO
        }
    }

    private fun optimizeTimeOffs(timeOffs: List<TimeSegment>): List<TimeSegment> {
        if (timeOffs.isEmpty()) return timeOffs

        val sortedTimeOffs = timeOffs.sortedBy { it.startTime }
        val optimized = mutableListOf<TimeSegment>()
        var currentSegment = sortedTimeOffs.first()

        for (nextSegment in sortedTimeOffs.drop(1)) {
            if (currentSegment.endTime >= nextSegment.startTime) {
                // Merge overlapping or adjacent segments
                currentSegment = TimeSegment(
                    startTime = currentSegment.startTime,
                    endTime = maxOf(currentSegment.endTime, nextSegment.endTime),
                    duration = Duration.between(
                        currentSegment.startTime,
                        maxOf(currentSegment.endTime, nextSegment.endTime)
                    )
                )
            } else {
                optimized.add(currentSegment)
                currentSegment = nextSegment
            }
        }
        optimized.add(currentSegment)
        return optimized
    }
}