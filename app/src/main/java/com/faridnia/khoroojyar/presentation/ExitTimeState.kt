package com.faridnia.khoroojyar.presentation

data class ExitTimeState(
    val enterTimeInput: String = "00:00",
    val exitTimeInput: String = "00:00",
    val exitTime: String = "",
    val vacationList: List<TimeSegment> = emptyList(),
    val timeWorked: TimeSegment? = null,
    val overtime: TimeSegment? = null
){
    fun isExitTimeEntered() = exitTimeInput.isNotEmpty() && exitTimeInput != "00:00"
}
