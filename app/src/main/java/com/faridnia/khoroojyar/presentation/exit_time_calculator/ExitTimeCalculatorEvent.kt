package com.faridnia.khoroojyar.presentation.exit_time_calculator

sealed class ExitTimeCalculatorEvent {
    data class OnEnterTimeChange(val time: String) : ExitTimeCalculatorEvent()
    data class OnExitTimeChange(val time: String) : ExitTimeCalculatorEvent()
    data class OnEnterTimeSave(
        val isChecked: Boolean,
        val hour: Int,
        val minute: Int
    ) : ExitTimeCalculatorEvent()

    data class OnExitTimeSave(
        val isChecked: Boolean,
        val hour: Int,
        val minute: Int
    ) : ExitTimeCalculatorEvent()
}