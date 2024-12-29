package com.faridnia.khoroojyar.domain.use_case

import com.faridnia.khoroojyar.data.data_store.dto.TimeSettings
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(timeSettings: TimeSettings) {
        dataStoreRepository.updateTimeSettings(timeSettings)
    }
}