package com.faridnia.khoroojyar.util

import com.razaghimahdi.compose_persian_date.core.PersianDatePickerController

fun PersianDatePickerController.getSimpleFormattedPersianDate() =
    "${getPersianYear()}/${getPersianMonth().addZeroBehind()}/${getPersianDay().addZeroBehind()}"

operator fun PersianDatePickerController.compareTo(other: PersianDatePickerController): Int {
    return when {
        this.getPersianYear() != other.getPersianYear() -> this.getPersianYear() - other.getPersianYear()
        this.getPersianMonth() != other.getPersianMonth() -> this.getPersianMonth() - other.getPersianMonth()
        else -> this.getPersianDay() - other.getPersianDay()
    }
}

fun Int.addZeroBehind(): String = if (this < 10) "0$this" else this.toString()
