package com.faridnia.khoroojyar.presentation.analitics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.use_case.CalculateOvertimeUseCase
import com.faridnia.khoroojyar.domain.use_case.CalculateTimeOffUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetAllWorkDayInfosUseCase
import com.faridnia.khoroojyar.presentation.calculate_days_off.WorkDayInfoChartState
import com.patrykandpatrick.vico.core.extension.sumOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkDayInfoViewModel @Inject constructor(
    private val getAllWorkDayInfosUseCase: GetAllWorkDayInfosUseCase,
    private val calculateTimeOffUseCase: CalculateTimeOffUseCase,
    private val calculateOvertimeUseCase: CalculateOvertimeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WorkDayInfoChartState())
    val state: StateFlow<WorkDayInfoChartState> = _state

    init {
        loadChartData()
        calculateWorkDayInfos()
    }

    private fun calculateWorkDayInfos() {
        calculateTotalTimeWorkedInMonth()
        calculateTotalTimeOffsInMonth()
        calculateOverTimeInMonth()
    }

    private fun calculateOverTimeInMonth() {
        viewModelScope.launch {
            val workDayInfosInCurrentMonth = getWorkDayInfosInCurrentMonth()

            _state.update {
                it.copy(
                    totalOverTimeInMonth = workDayInfosInCurrentMonth
                        .sumOf { workDayInfo ->
                            calculateOvertimeUseCase(workDayInfo).toMinutes().toFloat()
                        }
                )
            }
        }
    }

    private fun calculateTotalTimeOffsInMonth() {
        viewModelScope.launch {
            val workDayInfosInCurrentMonth = getWorkDayInfosInCurrentMonth()

            _state.update { state ->
                state.copy(
                    totalUsedTimeOffInMonth = workDayInfosInCurrentMonth
                        .sumOf { workDayInfo ->
                            getTotalTimeOfInAWorkDay(workDayInfo)
                        }
                )
            }
        }
    }

    private suspend fun WorkDayInfoViewModel.getTotalTimeOfInAWorkDay(
        workDayInfo: WorkDayInfo
    ) = calculateTimeOffUseCase(workDayInfo).sumOf { timeSegment ->
        timeSegment.duration.toMinutes().toFloat()
    }

    private fun calculateTotalTimeWorkedInMonth() {
        viewModelScope.launch {
            val workDayInfosInCurrentMonth = getWorkDayInfosInCurrentMonth()

            _state.update { state ->
                state.copy(totalTimeWorkedInMonth = workDayInfosInCurrentMonth.sumOf { workDayInfo -> workDayInfo.workedTime })
            }
        }
    }

    private suspend fun WorkDayInfoViewModel.getWorkDayInfosInCurrentMonth(): List<WorkDayInfo> {
        val workDayInfosInCurrentMonth = getAllWorkDayInfosUseCase()
            .filter { it.day.monthValue == LocalDate.now().monthValue }
        return workDayInfosInCurrentMonth
    }

    private fun loadChartData() {
        viewModelScope.launch {
            val workDayInfos = getAllWorkDayInfosUseCase()
            _state.update {
                it.copy(
                    workedTimeList = workDayInfos.map { workDayInfo -> workDayInfo.workedTime },
                    timeOffList = workDayInfos.map { workDayInfo ->
                        getTotalTimeOfInAWorkDay(
                            workDayInfo
                        )
                    })
            }
        }
    }

    fun onEvent(event: WorkDayChartEvent) {
        when (event) {
            WorkDayChartEvent.OnDateSelected -> {

            }
        }
    }
}
