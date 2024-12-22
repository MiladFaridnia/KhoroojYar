package com.faridnia.khoroojyar.domain.use_case

import com.faridnia.khoroojyar.domain.data_store.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class RetrieveInOutTimeUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(): Flow<String?> {
        return repository.retrieveInOutTime()
    }
}
