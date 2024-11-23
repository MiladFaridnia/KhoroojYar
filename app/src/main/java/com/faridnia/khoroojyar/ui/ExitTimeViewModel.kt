package com.faridnia.khoroojyar.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ExitTimeViewModel : ViewModel() {

    var enterTimeInput = mutableStateOf("")
        private set
    var exitTime = mutableStateOf("")
        private set
    var vacationMessage = mutableStateOf("")
        private set

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun onEnterTimeChange(newTime: String) {
        enterTimeInput.value = newTime
    }

    fun calculateExitTime() {
        try {
            val enterTime = LocalTime.parse(enterTimeInput.value, timeFormatter)
            if (enterTime.isAfter(LocalTime.of(9, 0))) {
                val vacationStart = LocalTime.of(9, 0)
                vacationMessage.value =
                    "Vacation time needed: ${vacationStart.format(timeFormatter)}_${
                        enterTime.format(timeFormatter)
                    }"
                exitTime.value = "17:45"
            } else {
                vacationMessage.value = ""
                val exitTimeCalculated = enterTime.plusHours(8).plusMinutes(45)
                exitTime.value = exitTimeCalculated.format(timeFormatter)
            }
        } catch (e: DateTimeParseException) {
            exitTime.value = "Invalid time format"
            vacationMessage.value = ""
        }
    }
}
