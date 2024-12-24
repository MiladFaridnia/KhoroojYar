package com.faridnia.khoroojyar.domain.use_case.data_store

import com.faridnia.khoroojyar.domain.repository.PreferencesRepository

class SaveInOutTimeInPrefsUseCase(private val repository: PreferencesRepository) {
    suspend operator fun invoke(inOutTime: String) {
        repository.saveInOutTime(inOutTime)
    }
}
