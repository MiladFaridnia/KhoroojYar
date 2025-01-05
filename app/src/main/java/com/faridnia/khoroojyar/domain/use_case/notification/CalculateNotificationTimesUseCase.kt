package com.faridnia.khoroojyar.domain.use_case.notification

import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.domain.use_case.CalculateExitTimeUseCase
import com.faridnia.khoroojyar.util.toFormattedString
import java.time.LocalTime
import javax.inject.Inject

class CalculateNotificationTimesUseCase @Inject constructor(
    private val calculateExitTimeUseCase: CalculateExitTimeUseCase
) {
    suspend operator fun invoke(enterTime: LocalTime?): List<NotificationExitTime> =
        calculateExitTimeUseCase(enterTime)?.let {
            listOf(
                NotificationExitTime(
                    R.string.run_the_office_doors_are_about_to_lock_in_15_minutes,
                    it.minusMinutes(15),
                    extraMessage = "\nExit Time: ${it.toFormattedString()}"
                ),
                NotificationExitTime(
                    R.string.run_the_office_doors_are_about_to_lock_at_exit_time,
                    it,
                    extraMessage = "\nExit Time: ${it.toFormattedString()}"
                )
            )
        } ?: emptyList()
}