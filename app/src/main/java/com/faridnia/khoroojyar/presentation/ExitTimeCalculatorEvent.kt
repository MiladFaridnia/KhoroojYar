package com.faridnia.khoroojyar.presentation

sealed class ExitTimeCalculatorEvent {
    data class OnEnterTimeChange(val time: String) : ExitTimeCalculatorEvent()
    data class OnExitTimeChange(val time: String) : ExitTimeCalculatorEvent()
}