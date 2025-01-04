package com.faridnia.khoroojyar.presentation.analitics

sealed class WorkDayChartEvent {
    data object OnDateSelected : WorkDayChartEvent()
}