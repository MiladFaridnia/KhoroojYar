package com.faridnia.khoroojyar.domain.repository

import com.faridnia.khoroojyar.data.data_store.dto.TimeSettings
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getTimeSettings(): Flow<TimeSettings>
    suspend fun updateTimeSettings(timeSettings: TimeSettings)
}