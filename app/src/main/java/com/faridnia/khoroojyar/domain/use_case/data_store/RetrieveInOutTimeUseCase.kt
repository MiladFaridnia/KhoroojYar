package com.faridnia.khoroojyar.domain.use_case.data_store

import com.faridnia.khoroojyar.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class RetrieveInOutTimeUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(): Flow<String?> {
        return repository.retrieveInOutTime()
    }
}
