package com.faridnia.khoroojyar.presentation.exit_time_calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.use_case.CalculateExitTimeUseCase
import com.faridnia.khoroojyar.domain.use_case.CalculateOvertimeUseCase
import com.faridnia.khoroojyar.domain.use_case.CalculateTimeOffUseCase
import com.faridnia.khoroojyar.domain.use_case.notification.ScheduleNotificationUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetWorkDayInfoByDayUseCase
import com.faridnia.khoroojyar.domain.use_case.db.UpsertWorkDayInfoUseCase
import com.faridnia.khoroojyar.presentation.component.snackbar.SnackbarController
import com.faridnia.khoroojyar.presentation.component.snackbar.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ExitTimeViewModel @Inject constructor(
    private val calculateExitTimeUseCase: CalculateExitTimeUseCase,
    private val calculateOvertimeUseCase: CalculateOvertimeUseCase,
    private val calculateTimeOffUseCase: CalculateTimeOffUseCase,
    private val upsertWorkDayInfoUseCase: UpsertWorkDayInfoUseCase,
    private val getWorkDayInfo: GetWorkDayInfoByDayUseCase,
    private val scheduleNotificationUseCase: ScheduleNotificationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExitTimeState())
    val state = _state.asStateFlow()

    init {
        getInitialWorkDayInfo(LocalDate.now())
    }

    private fun getInitialWorkDayInfo(now: LocalDate) {
        viewModelScope.launch {
            val workDayInfo = getWorkDayInfo(now)
            _state.update { currentState ->
                currentState.copy(
                    enterTime = workDayInfo?.firstEnterTime,
                    exitTime = workDayInfo?.firstExitTime
                )
            }
        }.invokeOnCompletion {
            calculateTime()
        }
    }

    fun onEvent(event: ExitTimeCalculatorEvent) {
        when (event) {
            is ExitTimeCalculatorEvent.OnEnterTimeChange -> {
                handleEnterTimeChange(event.time)
            }

            is ExitTimeCalculatorEvent.OnExitTimeChange -> {
                handleExitTimeChange(event.time)
            }

            is ExitTimeCalculatorEvent.OnEnterTimeSave -> {
                if (event.isChecked) {
                    saveWorkDayInfo(
                        createWorkDayInfoWithEnterTime(event.hour, event.minute)
                    )
                }
            }

            is ExitTimeCalculatorEvent.OnExitTimeSave -> {
                if (event.isChecked) {
                    saveWorkDayInfo(
                        createWorkDayInfoWithExitTime(event.hour, event.minute)
                    )
                }
            }
        }
    }

    private fun handleEnterTimeChange(time: String) {
        try {
            val newEnterTime = LocalTime.parse(time)
            _state.update { currentState ->
                currentState.copy(enterTime = newEnterTime)
            }
            viewModelScope.launch {
                scheduleNotificationUseCase(newEnterTime)
            }
            calculateTime()
        } catch (e: Exception) {
            handleInvalidTimeFormat()
        }
    }

    private fun handleExitTimeChange(time: String) {
        try {
            val newExitTime = LocalTime.parse(time)
            _state.update { currentState ->
                currentState.copy(exitTime = newExitTime)
            }
            calculateTime()
        } catch (e: Exception) {
            handleInvalidTimeFormat()
        }
    }

    private fun calculateTime() {
        val enterTime = _state.value.enterTime ?: return
        val exitTime = _state.value.exitTime
        viewModelScope.launch {
            val timeOffs = calculateTimeOffUseCase(
                WorkDayInfo(
                    day = LocalDate.now(),
                    firstEnterTime = enterTime,
                    firstExitTime = exitTime
                )
            )
            if (state.value.isExitTimeEntered()) {
                val timeWorked = getTotalTimeWorkInSegment(
                    enterTime = enterTime,
                    exitTime = exitTime!!
                )
                val overtime = calculateOvertimeUseCase(
                    WorkDayInfo(
                        day = LocalDate.now(),
                        firstEnterTime = enterTime,
                        firstExitTime = exitTime
                    )
                )
                _state.update { currentState ->
                    currentState.copy(
                        canExitTime = null,
                        timeOffList = timeOffs,
                        timeWorked = timeWorked,
                        overtime = overTimeToTimeSegment(overtime, exitTime)
                    )
                }
            } else {
                val calculatedExitTime = calculateExitTimeUseCase(enterTime)
                _state.update {
                    it.copy(
                        timeOffList = timeOffs,
                        canExitTime = calculatedExitTime
                    )
                }
            }
        }
    }

    private fun saveWorkDayInfo(workDayInfo: WorkDayInfo) {
        viewModelScope.launch {
            upsertWorkDayInfoUseCase(workDayInfo)
        }
    }

    private fun createWorkDayInfoWithEnterTime(hour: Int, minute: Int): WorkDayInfo {
        val today = LocalDate.now()
        val selectedTime = LocalTime.of(hour, minute)
        return WorkDayInfo(
            id = 0,
            day = today,
            firstEnterTime = selectedTime,
            firstExitTime = null,
            secondEnterTime = null,
            secondExitTime = null
        )
    }

    private fun createWorkDayInfoWithExitTime(hour: Int, minute: Int): WorkDayInfo {
        val today = LocalDate.now()
        val selectedTime = LocalTime.of(hour, minute)
        return WorkDayInfo(
            id = 0,
            day = today,
            firstEnterTime = null,
            firstExitTime = selectedTime,
            secondEnterTime = null,
            secondExitTime = null
        )
    }

    private fun handleInvalidTimeFormat() {
        _state.update { currentState ->
            currentState.copy(
                exitTime = null,
                timeWorked = null,
                overtime = null,
                timeOffList = emptyList()
            )
        }
        showSnackbar("Invalid time format")
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(message = message)
            )
        }
    }

    private fun getTotalTimeWorkInSegment(enterTime: LocalTime, exitTime: LocalTime): TimeSegment {
        val totalTimeWorked = Duration.between(enterTime, exitTime)
        return TimeSegment(
            startTime = enterTime,
            endTime = exitTime,
            duration = totalTimeWorked
        )
    }

    private fun overTimeToTimeSegment(overtime: Duration, exitTime: LocalTime): TimeSegment? {
        return if (!overtime.isZero) {
            TimeSegment(
                startTime = exitTime,
                endTime = exitTime.plus(overtime),
                duration = overtime
            )
        } else {
            null
        }
    }
}