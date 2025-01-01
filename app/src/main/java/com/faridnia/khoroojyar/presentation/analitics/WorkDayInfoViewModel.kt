package com.faridnia.khoroojyar.presentation.analitics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.domain.use_case.db.GetAllWorkDayInfosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkDayInfoViewModel @Inject constructor(
    private val getAllWorkDayInfosUseCase: GetAllWorkDayInfosUseCase
) : ViewModel() {

    private val _chartData = MutableStateFlow<List<Float>>(emptyList())
    val chartData: StateFlow<List<Float>> = _chartData

    init {
        loadChartData()
    }

    private fun loadChartData() {
        viewModelScope.launch {
            val workDayInfos = getAllWorkDayInfosUseCase()
            _chartData.value = workDayInfos.map { workDayInfo -> workDayInfo.workedTime }
        }
    }

    fun onEvent(event: WorkDayChartEvent) {
        when (event) {
            WorkDayChartEvent.OnDateSelected -> {

            }
        }
    }
}
