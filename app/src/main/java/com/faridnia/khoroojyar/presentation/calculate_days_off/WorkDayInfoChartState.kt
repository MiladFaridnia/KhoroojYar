package com.faridnia.khoroojyar.presentation.calculate_days_off

data class WorkDayInfoChartState(
    val workedTimeList: List<Float> = emptyList(),
    val timeOffList: List<Float> = emptyList(),
    val remainedTimeOff: Float = 0f,
    val totalTimeWorkedInMonth: Float = 0f,
    val totalOverTimeInMonth: Float = 0f,
    val totalUsedTimeOffInMonth: Float = 0f
)