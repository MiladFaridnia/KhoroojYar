package com.faridnia.khoroojyar.domain.use_case

import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import java.time.LocalTime
import javax.inject.Inject

class CalculateExitTimeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(enterTime: LocalTime?): LocalTime? {
        val timeSettings = dataStoreRepository.getTimeSettings().first()

        val adjustedStartTime = adjustStartTime(
            enterTime,
            timeSettings.earliestStart,
            timeSettings.latestStart
        )

        return adjustedStartTime?.plus(timeSettings.workDuration)
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
}
