package com.faridnia.khoroojyar.domain.use_case.db

import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.domain.repository.WorkDayInfoRepository
import java.time.LocalDate
import javax.inject.Inject

class GetWorkDayInfoByDayUseCase @Inject constructor(
    private val repository: WorkDayInfoRepository
) {
    suspend operator fun invoke(day: LocalDate): WorkDayInfo? {
        return repository.getWorkDayInfoByDay(day)
    }
}
