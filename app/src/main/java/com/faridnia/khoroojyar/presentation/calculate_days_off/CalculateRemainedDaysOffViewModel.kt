package com.faridnia.khoroojyar.presentation.calculate_days_off

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.math.floor

class CalculateRemainedDaysOffViewModel : ViewModel() {

    var hoursInput = mutableStateOf("")
        private set

    var calculatedDaysOff = mutableStateOf("")
        private set

    private val hoursPerDay = 8.75 // 8 hours and 45 minutes

    fun onHoursInputChange(newInput: String) {
        hoursInput.value = newInput
        calculateDaysOff(newInput)
    }

    private fun calculateDaysOff(hoursInput: String) {
        val totalHours = hoursInput.toFloatOrNull()
        if (totalHours != null) {
            val days = floor(totalHours / hoursPerDay).toInt() // Calculate whole days
            val remainingHours = totalHours % hoursPerDay       // Calculate remaining hours
            val remainingMinutes =
                (remainingHours - floor(remainingHours)) * 60 // Convert fractional hours to minutes

            // Format the result
            calculatedDaysOff.value = buildString {
                append("$days days")
                if (remainingHours > 0) {
                    append(
                        " and ${remainingHours.toInt()}:${
                            String.format(
                                "%02d",
                                remainingMinutes.toInt()
                            )
                        } hours"
                    )
                }
            }
        } else {
            calculatedDaysOff.value = ""
        }
    }
}
