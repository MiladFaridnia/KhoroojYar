package com.faridnia.khoroojyar.domain.use_case.db

import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.repository.WorkDayInfoRepository
import javax.inject.Inject

class DeleteWorkDayInfoUseCase @Inject constructor(
    private val repository: WorkDayInfoRepository
) {
    suspend operator fun invoke(workDayInfo: WorkDayInfo) {
        repository.delete(workDayInfo)
    }
}
