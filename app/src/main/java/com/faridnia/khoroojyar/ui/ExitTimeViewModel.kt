package com.faridnia.khoroojyar.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ExitTimeViewModel : ViewModel() {

    var enterTimeInput = mutableStateOf("")
        private set
    var exitTimeInput = mutableStateOf("")
        private set
    var exitTime = mutableStateOf("")
        private set
    var vacationMessage = mutableStateOf("")
        private set

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val workDuration = Duration.ofMinutes(525) // 8 hours and 45 minutes
    private val earliestStart = LocalTime.of(8, 0)
    private val latestStart = LocalTime.of(9, 0)
    private val latestEnd = LocalTime.of(17, 45)

    fun onEnterTimeChange(newTime: String) {
        enterTimeInput.value = newTime
    }

    fun onExitTimeChange(newTime: String) {
        exitTimeInput.value = newTime
    }

    fun calculateTime() {
        try {
            val enterTime = LocalTime.parse(enterTimeInput.value, timeFormatter)

            if (exitTimeInput.value.isNotEmpty()) {
                // Exit time provided, calculate vacation
                val exitTimeProvided = LocalTime.parse(exitTimeInput.value, timeFormatter)
                calculateVacation(enterTime, exitTimeProvided)
                exitTime.value = ""
            } else {
                // Default exit time
                if (enterTime.isAfter(LocalTime.of(9, 0))) {
                    vacationMessage.value =
                        "Vacation needed: ${latestStart.format(timeFormatter)} to ${
                            enterTime.format(
                                timeFormatter
                            )
                        }"
                    exitTime.value = "17:45"
                } else {
                    vacationMessage.value = ""
                    val exitTimeCalculated = enterTime.plusHours(8).plusMinutes(45)
                    exitTime.value = exitTimeCalculated.format(timeFormatter)
                }
            }
        } catch (e: DateTimeParseException) {
            exitTime.value = "Invalid time format"
            vacationMessage.value = ""
        }
    }

    private fun calculateVacation(enterTime: LocalTime, exitTime: LocalTime) {
        val workedDuration = Duration.between(enterTime, exitTime)

        if (exitTime == latestEnd && enterTime.isAfter(LocalTime.of(9, 0))) {
            val lateEntryVacationStart = LocalTime.of(9, 0)
            vacationMessage.value =
                "Vacation needed: ${lateEntryVacationStart.format(timeFormatter)} to ${
                    enterTime.format(
                        timeFormatter
                    )
                }"
            return
        }

        if (workedDuration < workDuration) {
            val missingDuration = workDuration.minus(workedDuration)
            val vacationParts = mutableListOf<String>()

            if (exitTime == latestEnd) {
                val vacationStart = LocalTime.of(9, 0)
                vacationParts.add(
                    "${vacationStart.format(timeFormatter)} to ${
                        enterTime.format(
                            timeFormatter
                        )
                    }"
                )
            } else {
                if (exitTime.isBefore(latestEnd)) {
                    val endVacationEnd = minOf(exitTime.plus(missingDuration), latestEnd)
                    vacationParts.add(
                        "${exitTime.format(timeFormatter)} to ${
                            endVacationEnd.format(
                                timeFormatter
                            )
                        }"
                    )
                }
                val remainingMissing = missingDuration.minus(
                    Duration.between(exitTime, minOf(exitTime.plus(missingDuration), latestEnd))
                )
                if (!remainingMissing.isZero && enterTime.isAfter(earliestStart)) {
                    val startVacationStart = maxOf(earliestStart, enterTime.minus(remainingMissing))
                    vacationParts.add(
                        "${startVacationStart.format(timeFormatter)} to ${
                            enterTime.format(
                                timeFormatter
                            )
                        }"
                    )
                }
            }

            vacationMessage.value = "Vacation needed: ${vacationParts.joinToString(", ")}"
        } else {
            vacationMessage.value = ""
        }
    }
}