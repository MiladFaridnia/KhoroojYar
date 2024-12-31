package com.faridnia.khoroojyar.data.data_store

import androidx.datastore.core.DataStore
import com.faridnia.khoroojyar.data.data_store.dto.Settings
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import java.time.Duration
import java.time.LocalTime

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Settings>
) : DataStoreRepository {

    override fun getTimeSettings(): Flow<Settings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(Settings())
            } else {
                throw exception
            }
        }

    override suspend fun updateTimeSettings(settings: Settings) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(
                workDuration = settings.workDuration,
                earliestStart = settings.earliestStart,
                latestStart = settings.latestStart
            )
        }
    }

    override suspend fun updateEarliestStart(earliestStart: LocalTime) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(earliestStart = earliestStart)
        }
    }

    override suspend fun updateLatestStart(latestStart: LocalTime) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(latestStart = latestStart)
        }
    }

    override suspend fun updateWorkDuration(workDuration: Duration) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(workDuration = workDuration)
        }
    }

    override suspend fun updateDarkMode(isDark: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(isDark = isDark)
        }
    }
}