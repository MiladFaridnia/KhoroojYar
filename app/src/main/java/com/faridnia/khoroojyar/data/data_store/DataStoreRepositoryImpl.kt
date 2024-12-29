package com.faridnia.khoroojyar.data.data_store

import androidx.datastore.core.DataStore
import com.faridnia.khoroojyar.data.data_store.dto.TimeSettings
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<TimeSettings>
) : DataStoreRepository {

    override fun getTimeSettings(): Flow<TimeSettings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(TimeSettings())
            } else {
                throw exception
            }
        }

    override suspend fun updateTimeSettings(timeSettings: TimeSettings) {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(
                workDuration = timeSettings.workDuration,
                earliestStart = timeSettings.earliestStart,
                latestStart = timeSettings.latestStart
            )
        }
    }
}