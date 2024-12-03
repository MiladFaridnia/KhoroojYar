package com.faridnia.khoroojyar.presentation

data class ExitTimeState(
    val enterTimeInput: String = "00:00",
    val exitTimeInput: String = "00:00",
    val exitTime: String = "",
    val vacationMessage: String = "",
    val totalTimeSpent: String = ""
)
