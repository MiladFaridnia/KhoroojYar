package com.faridnia.khoroojyar.presentation.component

import androidx.annotation.DrawableRes
import com.faridnia.khoroojyar.R

enum class DateButtonType(val id: Int, @DrawableRes val icon: Int = R.drawable.ic_clock) {
    DATE(0, R.drawable.ic_calendar),
    TIME(1, R.drawable.ic_clock)
}