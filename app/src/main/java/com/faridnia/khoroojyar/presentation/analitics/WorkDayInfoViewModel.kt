package com.faridnia.khoroojyar.presentation.analitics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.use_case.db.GetAllWorkDayInfosUseCase
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import javax.inject.Inject

class WorkDayInfoViewModel @Inject constructor(
    private val getAllWorkDayInfosUseCase: GetAllWorkDayInfosUseCase
) : ViewModel() {

   // private val _chartData = MutableStateFlow<List<Entry>>(emptyList())
    //  val chartData: StateFlow<List<Entry>> = _chartData

    fun loadChartData() {
        viewModelScope.launch {
            val workDayInfos = getAllWorkDayInfosUseCase()
            val durations = calculateWorkingDurations(workDayInfos)
            //_chartData.value = createChartData(durations)
        }
    }

    private fun calculateWorkingDurations(workDayInfos: List<WorkDayInfo>): List<Pair<LocalDate, Float>> {
        return workDayInfos.mapNotNull { workDayInfo ->
            val duration =
                if (workDayInfo.firstEnterTime != null && workDayInfo.firstExitTime != null) {
                    Duration.between(workDayInfo.firstEnterTime, workDayInfo.firstExitTime)
                        .toMinutes()
                } else {
                    null
                }
            duration?.let {
                workDayInfo.day to it / 60f // Convert minutes to hours
            }
        }
    }

    /* private fun createChartData(workingDurations: List<Pair<LocalDate, Float>>): List<Entry> {
         return workingDurations.mapIndexed { index, (_, duration) ->
             Entry(index.toFloat(), duration) // X-axis: index, Y-axis: duration
         }
     }*/
}
