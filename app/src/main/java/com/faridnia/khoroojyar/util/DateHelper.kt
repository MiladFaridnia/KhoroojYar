package com.faridnia.khoroojyar.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateHelper {

    fun getCurrentGregorianDayInMonth(): String {
        val currentDate = Date()
        val sdf = SimpleDateFormat("d MMMM", Locale.US)
        return sdf.format(currentDate) // Outputs: 27 September
    }

    fun getCurrentGregorianDate(): String {
        val currentDate = Date()
        val sdf = SimpleDateFormat("MMM d, yyyy", Locale.US)
        return sdf.format(currentDate) // Outputs: Dec 4, 2024
    }

    fun getDayOfWeek(): String {
        val currentDate = Date()
        val sdf = SimpleDateFormat("EEEE", Locale.US)
        return sdf.format(currentDate) // Outputs: Monday, Tuesday, etc.
    }
}