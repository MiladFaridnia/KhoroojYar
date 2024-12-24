package com.faridnia.khoroojyar.data.data_store

import com.faridnia.khoroojyar.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

// Repository Implementation (Data Layer)
class PreferencesRepositoryImpl(private val dataStoreManager: DataStoreManager) :
    PreferencesRepository {
    override suspend fun saveInOutTime(inOutTime: String) {
        dataStoreManager.saveInOutTime(inOutTime)
    }

    override fun retrieveInOutTime(): Flow<String?> {
        return dataStoreManager.retrieveInOutTimeFlow
    }
}