package com.faridnia.khoroojyar.data.data_store


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define a single instance of DataStore
private val Context.dataStore by preferencesDataStore(name = "settings")


class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    // Key for storing a string value
    companion object {
        val IN_OUT_EVENT = stringPreferencesKey("in-out-event")
    }

    // Function to save data
    suspend fun saveInOutTime(time: String) {
        dataStore.edit { preferences ->
            preferences[IN_OUT_EVENT] = time
        }
    }

    // Function to retrieve data
    val retrieveInOutTimeFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[IN_OUT_EVENT] // Returns null if the key doesn't exist
        }
}