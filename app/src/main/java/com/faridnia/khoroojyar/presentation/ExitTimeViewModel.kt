package com.faridnia.khoroojyar.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.presentation.component.snackbar.SnackbarController
import com.faridnia.khoroojyar.presentation.component.snackbar.SnackbarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val earliestStart = LocalTime.of(7, 0)
    private val latestStart = LocalTime.of(9, 0)
    private val latestEnd = LocalTime.of(17, 45)

    fun onEvent(event: ExitTimeCalculatorEvent) {
        when (event) {
            is ExitTimeCalculatorEvent.OnEnterTimeChange -> {
                var newEnterTime = LocalTime.parse(event.time, timeFormatter)
                if (newEnterTime.isBefore(earliestStart)) {
                    newEnterTime = earliestStart
                }
                _state.update { currentState ->
                    currentState.copy(
                        enterTime = newEnterTime,
                        enterTimeInput = event.time
                    )
                }
                calculateTime()
            }
            is ExitTimeCalculatorEvent.OnExitTimeChange -> {
                _state.update { currentState ->
                    currentState.copy(exitTimeInput = event.time)
                }
                calculateTime()
            }

            is ExitTimeCalculatorEvent.OnEnterTimeSave -> {

            }

            is ExitTimeCalculatorEvent.OnExitTimeSave -> {

            }
        }
    }

    private fun calculateTime() {
        try {
            state.value.enterTime?.let { enterTime ->
                if (_state.value.isExitTimeEntered()) {
                    handleExitTimeProvided(enterTime)
                } else {
                    handleNoExitTime(enterTime)
                }
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
            val lateEntryTimeOff = calculateLateEntryTimeOff(enterTime)
            val overtime = calculateOvertime(enterTime, exitTimeProvided)

            calculateVacation(enterTime, exitTimeProvided)
            updateTotalTimeSpent(enterTime, exitTimeProvided, overtime)
        }
    }

    private fun calculateLateEntryTimeOff(enterTime: LocalTime): Duration {
        return if (enterTime.isAfter(latestStart)) {
            Duration.between(latestStart, enterTime)
        } else {
            Duration.ZERO
        }
    }

    private fun calculateOvertime(enterTime: LocalTime, exitTime: LocalTime): Duration {
        var overtime = Duration.ZERO
        val standardEndTime = LocalTime.of(17, 45)

        // Calculate overtime for working past the standard end time
        if (exitTime.isAfter(standardEndTime)) {
            overtime = overtime.plus(Duration.between(standardEndTime, exitTime))
        }

        // Calculate early entry overtime (if entered between earliestStart and latestStart)
        if (enterTime.isAfter(earliestStart) && enterTime.isBefore(latestStart)) {
            val earlyEntryDuration = Duration.between(enterTime, latestStart)
            val totalWorkDuration = Duration.between(enterTime, exitTime)

            if (totalWorkDuration > workDuration) {
                overtime = overtime.plus(earlyEntryDuration)
            }
        }

        return overtime
    }

    private fun handleNoExitTime(enterTime: LocalTime) {
        if (enterTime.isAfter(latestStart)) {
            updateLateStartState(enterTime)
        } else {
            _state.update { currentState ->
                currentState.copy(
                    timeWorked = null,
                    overtime = null,
                    vacationList = emptyList()
                )
            }
            calculateAndUpdateDefaultExitTime(enterTime)
        }
    }

    private fun updateTotalTimeSpent(
        enterTime: LocalTime,
        exitTime: LocalTime,
        overtime: Duration
    ) {
        val totalTimeWorked = Duration.between(enterTime, exitTime)
        val totalTimeWorkedHours = totalTimeWorked.toHours().toInt()
        val totalTimeWorkedMinutes = (totalTimeWorked.toMinutes() % 60).toInt()

        val timeWorkedDuration = String.format(
            Locale.US,
            "%02d:%02d",
            totalTimeWorkedHours,
            totalTimeWorkedMinutes
        )

        val timeWorkedSegment = TimeSegment(
            startTime = enterTime.toString(),
            endTime = exitTime.toString(),
            duration = timeWorkedDuration
        )

        val overtimeSegment = if (!overtime.isZero) {
            val overtimeHours = overtime.toHours().toInt()
            val overtimeMinutes = (overtime.toMinutes() % 60).toInt()

            TimeSegment(
                startTime = exitTime.toString(),
                endTime = exitTime.plusHours(overtimeHours.toLong()).plusMinutes(overtimeMinutes.toLong()).toString(),
                duration = String.format(
                    Locale.US,
                    "%02d:%02d",
                    overtimeHours,
                    overtimeMinutes
                )
            )
        } else {
            null
        }

        _state.update { currentState ->
            currentState.copy(
                exitTime = "",
                timeWorked = timeWorkedSegment,
                overtime = overtimeSegment
            )
        }
    }

    private fun updateLateStartState(enterTime: LocalTime) {
        val vacationStart = latestStart
        val vacationDuration = Duration.between(vacationStart, enterTime)

        _state.update { currentState ->
            currentState.copy(
                exitTime = "17:45",
                timeWorked = null,
                overtime = null,
                vacationList = listOf(
                    TimeSegment(
                        startTime = vacationStart.format(timeFormatter),
                        endTime = enterTime.format(timeFormatter),
                        duration = formatDuration(vacationDuration)
                    )
                )
            )
        }
    }

    private fun calculateAndUpdateDefaultExitTime(enterTime: LocalTime) {
        val exitTimeCalculated = enterTime.plusHours(8).plusMinutes(45)

        _state.update { currentState ->
            currentState.copy(
                exitTime = exitTimeCalculated.format(timeFormatter)
            )
        }
    }

    private fun handleInvalidTimeFormat() {
        _state.update { currentState ->
            currentState.copy(
                exitTime = "Invalid time format",
                timeWorked = null,
                overtime = null,
                vacationList = emptyList()
            )
        }
        showSnackbar("Invalid time format")
    }

    private fun handleInvalidExitTime() {
        _state.update { currentState ->
            currentState.copy(
                exitTime = "Invalid exit time",
                timeWorked = null,
                overtime = null,
            )
        }
    }

    private fun calculateVacation(enterTime: LocalTime, exitTime: LocalTime) {
        val workedDuration = Duration.between(enterTime, exitTime)
        val vacationList = mutableListOf<TimeSegment>()

        if (enterTime.isAfter(LocalTime.of(9, 0))) {
            val lateEntryVacationStart = LocalTime.of(9, 0)
            val vacationDuration = Duration.between(lateEntryVacationStart, enterTime)
            vacationList.add(
                TimeSegment(
                    startTime = lateEntryVacationStart.format(timeFormatter),
                    endTime = enterTime.format(timeFormatter),
                    duration = formatDuration(vacationDuration)
                )
            )
        }

        if (workedDuration < workDuration) {
            val missingDuration = workDuration.minus(workedDuration)
            if (exitTime.isBefore(latestEnd)) {
                val endVacationEnd = minOf(exitTime.plus(missingDuration), latestEnd)
                val vacationDuration = Duration.between(exitTime, endVacationEnd)
                vacationList.add(
                    TimeSegment(
                        startTime = exitTime.format(timeFormatter),
                        endTime = endVacationEnd.format(timeFormatter),
                        duration = formatDuration(vacationDuration)
                    )
                )
            }
        }

        // Update state with the vacation list
        _state.update { currentState ->
            currentState.copy(
                vacationList = vacationList
            )
        }
    }

    private fun formatDuration(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        return String.format(Locale.US, "%02d:%02d", hours, minutes)
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
            timeWorked = null,
            overtime = null,
            vacationList = emptyList()
        )
    }
}