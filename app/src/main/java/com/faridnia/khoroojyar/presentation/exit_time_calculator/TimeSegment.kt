package com.faridnia.khoroojyar.presentation.exit_time_calculator

data class TimeSegment(
    val startTime: String,
    val endTime: String,
    val duration: String
){
    val startToEndTime
        get() = "${startTime}_$endTime"
}