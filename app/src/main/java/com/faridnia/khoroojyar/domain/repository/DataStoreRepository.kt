package com.faridnia.khoroojyar.domain.repository

import com.faridnia.khoroojyar.data.data_store.dto.Settings
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import java.time.LocalTime

interface DataStoreRepository {
    fun getTimeSettings(): Flow<Settings>
    suspend fun updateTimeSettings(settings: Settings)
    suspend fun updateEarliestStart(earliestStart: LocalTime)
    suspend fun updateLatestStart(latestStart: LocalTime)
    suspend fun updateWorkDuration(workDuration: Duration)
    suspend fun updateDarkMode(isDark: Boolean)
}