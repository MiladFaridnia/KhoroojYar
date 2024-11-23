package com.faridnia.khoroojyar.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ExitTimeViewModel : ViewModel() {

    // Input and output states
    var enterTimeInput = mutableStateOf("")
        private set
    var exitTime = mutableStateOf("")
        private set

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // Update the input state
    fun onEnterTimeChange(newTime: String) {
        enterTimeInput.value = newTime
    }

    // Calculate the exit time
    fun calculateExitTime() {
        try {
            val enterTime = LocalTime.parse(enterTimeInput.value, timeFormatter)
            val exitTimeCalculated = enterTime.plusHours(8).plusMinutes(45)
            exitTime.value = exitTimeCalculated.format(timeFormatter)
        } catch (e: DateTimeParseException) {
            exitTime.value = "Invalid time format"
        }
    }
}
