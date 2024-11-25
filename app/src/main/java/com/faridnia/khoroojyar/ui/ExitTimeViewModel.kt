package com.faridnia.khoroojyar.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.ui.component.snackbar.SnackbarController
import com.faridnia.khoroojyar.ui.component.snackbar.SnackbarEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class ExitTimeViewModel : ViewModel() {

    private val _state = MutableStateFlow(ExitTimeState())
    val state = _state.asStateFlow()

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val workDuration = Duration.ofMinutes(525) // 8 hours and 45 minutes
    private val earliestStart = LocalTime.of(8, 0)
    private val latestStart = LocalTime.of(9, 0)
    private val latestEnd = LocalTime.of(17, 45)

    // UI Event channel for controlling bottom sheet
    private val _uiEventChannel = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvents: Flow<UiEvent> = _uiEventChannel.receiveAsFlow()

    fun onFabClicked() {
        sendUiEvent(UiEvent.ShowBottomSheet)
    }

    fun closeBottomSheet() {
        sendUiEvent(UiEvent.HideBottomSheet)
    }

    private fun sendUiEvent(event: UiEvent) {
        _uiEventChannel.trySend(event)
    }

    fun onEnterTimeChange(newTime: String) {
        _state.update { currentState ->
            currentState.copy(enterTimeInput = newTime)
        }
        calculateTime()
    }

    fun onExitTimeChange(newTime: String) {
        _state.update { currentState ->
            currentState.copy(exitTimeInput = newTime)
        }
        calculateTime()
    }

    private fun calculateTime() {
        try {
            val enterTime = LocalTime.parse(_state.value.enterTimeInput, timeFormatter)
            if (_state.value.exitTimeInput.isNotEmpty()) {
                handleExitTimeProvided(enterTime)
            } else {
                handleNoExitTime(enterTime)
            }
        } catch (e: DateTimeParseException) {
            handleInvalidTimeFormat()
        } catch (e: IllegalArgumentException) {
            handleInvalidExitTime()
        }
    }

    private fun handleExitTimeProvided(enterTime: LocalTime) {
        val exitTimeProvided = LocalTime.parse(_state.value.exitTimeInput, timeFormatter)

        if (exitTimeProvided.isBefore(enterTime)) {
            showSnackbar("Exit time cannot be before enter time")
        } else {
            calculateVacation(enterTime, exitTimeProvided)
            updateTotalTimeSpent(enterTime, exitTimeProvided)
        }
    }

    private fun handleNoExitTime(enterTime: LocalTime) {
        if (enterTime.isAfter(LocalTime.of(9, 0))) {
            updateLateStartState(enterTime)
        } else {
            _state.update { it.copy(vacationMessage = "") }
            calculateAndUpdateDefaultExitTime(enterTime)
        }
    }

    private fun updateTotalTimeSpent(enterTime: LocalTime, exitTime: LocalTime) {
        val totalTimeWorked = Duration.between(enterTime, exitTime)
        val hoursWorked = totalTimeWorked.toHours()
        val minutesWorked = totalTimeWorked.toMinutes() % 60

        _state.update { currentState ->
            currentState.copy(
                exitTime = "",
                totalTimeSpent = String.format(
                    Locale.US,
                    "Time worked:\n %02d hours and %02d minutes",
                    hoursWorked,
                    minutesWorked
                )
            )
        }
    }

    private fun updateLateStartState(enterTime: LocalTime) {
        _state.update { currentState ->
            currentState.copy(
                vacationMessage = "Time Off:\n -> ${latestStart.format(timeFormatter)} to ${enterTime.format(timeFormatter)}",
                exitTime = "17:45",
                totalTimeSpent = ""
            )
        }
    }

    private fun calculateAndUpdateDefaultExitTime(enterTime: LocalTime) {
        val exitTimeCalculated = enterTime.plusHours(8).plusMinutes(45)
        val totalTimeWorked = Duration.between(enterTime, exitTimeCalculated)
        val hoursWorked = totalTimeWorked.toHours()
        val minutesWorked = totalTimeWorked.toMinutes() % 60

        _state.update { currentState ->
            currentState.copy(
                exitTime = exitTimeCalculated.format(timeFormatter),
                totalTimeSpent = String.format(
                    Locale.US,
                    "Time worked:\n %02d hours and %02d minutes",
                    hoursWorked,
                    minutesWorked
                )
            )
        }
    }

    private fun handleInvalidTimeFormat() {
        _state.update { currentState ->
            currentState.copy(
                exitTime = "Invalid time format",
                vacationMessage = "",
                totalTimeSpent = ""
            )
        }
        showSnackbar("Invalid time format")
    }

    private fun handleInvalidExitTime() {
        _state.update { currentState ->
            currentState.copy(
                exitTime = "Invalid exit time",
                totalTimeSpent = ""
            )
        }
    }

    private fun calculateVacation(enterTime: LocalTime, exitTime: LocalTime) {
        val workedDuration = Duration.between(enterTime, exitTime)

        if (exitTime == latestEnd && enterTime.isAfter(LocalTime.of(9, 0))) {
            val lateEntryVacationStart = LocalTime.of(9, 0)
            _state.update { currentState ->
                currentState.copy(
                    vacationMessage = "Time off:\n ->${lateEntryVacationStart.format(timeFormatter)} to ${enterTime.format(timeFormatter)}"
                )
            }
            return
        }

        if (workedDuration < workDuration) {
            val missingDuration = workDuration.minus(workedDuration)
            val vacationParts = mutableListOf<String>()

            if (exitTime == latestEnd) {
                val vacationStart = LocalTime.of(9, 0)
                vacationParts.add("${vacationStart.format(timeFormatter)} to ${enterTime.format(timeFormatter)}")
            } else {
                if (exitTime.isBefore(latestEnd)) {
                    val endVacationEnd = minOf(exitTime.plus(missingDuration), latestEnd)
                    vacationParts.add("${exitTime.format(timeFormatter)} to ${endVacationEnd.format(timeFormatter)}")
                }
                val remainingMissing = missingDuration.minus(
                    Duration.between(
                        exitTime,
                        minOf(exitTime.plus(missingDuration), latestEnd)
                    )
                )
                if (!remainingMissing.isZero && enterTime.isAfter(earliestStart)) {
                    val startVacationStart = maxOf(earliestStart, enterTime.minus(remainingMissing))
                    vacationParts.add("-> ${startVacationStart.format(timeFormatter)} to ${enterTime.format(timeFormatter)}")
                }
            }

            _state.update { currentState ->
                currentState.copy(vacationMessage = "Time off:\n -> ${vacationParts.joinToString("\n ")}")
            }
        } else {
            _state.update { currentState ->
                currentState.copy(vacationMessage = "")
            }
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = message
                )
            )
        }
    }

    fun clearEntries() {
        _state.value = _state.value.copy(
            enterTimeInput = "",
            exitTimeInput = "",
            exitTime = "",
            vacationMessage = ""
        )
    }


    sealed class UiEvent {
        data object ShowBottomSheet : UiEvent()
        data object HideBottomSheet : UiEvent()
    }
}
