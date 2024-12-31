package com.faridnia.khoroojyar.presentation.exit_time_calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.use_case.db.DeleteWorkDayInfoUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetAllWorkDayInfosUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetWorkDayInfoByDayUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetWorkDayInfoByIdUseCase
import com.faridnia.khoroojyar.domain.use_case.db.InsertWorkDayInfoUseCase
import com.faridnia.khoroojyar.domain.use_case.db.UpdateWorkDayInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkDayInfoViewModel @Inject constructor(
    private val insertWorkDayInfoUseCase: InsertWorkDayInfoUseCase,
    private val updateWorkDayInfoUseCase: UpdateWorkDayInfoUseCase,
    private val deleteWorkDayInfoUseCase: DeleteWorkDayInfoUseCase,
    private val getWorkDayInfoByIdUseCase: GetWorkDayInfoByIdUseCase,
    private val getAllWorkDayInfosUseCase: GetAllWorkDayInfosUseCase,
    private val getWorkDayInfoByDayUseCase: GetWorkDayInfoByDayUseCase
) : ViewModel() {

    fun insert(workDayInfo: WorkDayInfo) {
        viewModelScope.launch {
            insertWorkDayInfoUseCase(workDayInfo)
        }
    }

    fun update(workDayInfo: WorkDayInfo) {
        viewModelScope.launch {
            updateWorkDayInfoUseCase(workDayInfo)
        }
    }

    fun delete(workDayInfo: WorkDayInfo) {
        viewModelScope.launch {
            deleteWorkDayInfoUseCase(workDayInfo)
        }
    }

    suspend fun getWorkDayInfoById(id: Int): WorkDayInfo? {
        return getWorkDayInfoByIdUseCase(id)
    }

    suspend fun getAllWorkDayInfos(): List<WorkDayInfo> {
        return getAllWorkDayInfosUseCase()
    }

    suspend fun getWorkDayInfoByDay(day: LocalDate): WorkDayInfo? {
        return getWorkDayInfoByDayUseCase(day)
    }
}
