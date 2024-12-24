package com.faridnia.khoroojyar.presentation

sealed class ExitTimeCalculatorEvent {
    data class OnEnterTimeChange(val time: String) : ExitTimeCalculatorEvent()
    data class OnExitTimeChange(val time: String) : ExitTimeCalculatorEvent()
    data class OnEnterTimeSave(
        val isChecked: Boolean,
        val time: String
    ) : ExitTimeCalculatorEvent()

    data class OnExitTimeSave(
        val isChecked: Boolean,
        val time: String
    ) : ExitTimeCalculatorEvent()
}