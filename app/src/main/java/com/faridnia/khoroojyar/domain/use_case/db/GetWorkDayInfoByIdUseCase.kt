package com.faridnia.khoroojyar.domain.use_case.db

import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.repository.WorkDayInfoRepository
import javax.inject.Inject

class GetWorkDayInfoByIdUseCase @Inject constructor(
    private val repository: WorkDayInfoRepository
) {
    suspend operator fun invoke(id: Int): WorkDayInfo? {
        return repository.getWorkDayInfoById(id)
    }
}
