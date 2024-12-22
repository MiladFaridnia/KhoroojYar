package com.faridnia.khoroojyar.domain.data_store

import kotlinx.coroutines.flow.Flow

// Repository Interface (Domain Layer)
interface PreferencesRepository {
    suspend fun saveInOutTime(inOutTime: String)
    fun retrieveInOutTime(): Flow<String?>
}