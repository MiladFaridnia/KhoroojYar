package com.faridnia.khoroojyar.domain.use_case

import com.faridnia.khoroojyar.domain.data_store.PreferencesRepository

class SaveInOutTimeUseCase(private val repository: PreferencesRepository) {
    suspend operator fun invoke(inOutTime: String) {
        repository.saveInOutTime(inOutTime)
    }
}
