package com.faridnia.khoroojyar.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.faridnia.khoroojyar.data.data_store.DataStoreRepositoryImpl
import com.faridnia.khoroojyar.data.data_store.dto.TimeSettings
import com.faridnia.khoroojyar.data.data_store.dto.TimeSettingsSerializer
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import com.faridnia.khoroojyar.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreForSensitiveData(
        @ApplicationContext context: Context,
        serializer: TimeSettingsSerializer
    ): DataStore<TimeSettings> {
        return DataStoreFactory.create(
            serializer = serializer,
            produceFile = { context.dataStoreFile(Constants.DATA_STORE_FILE_NAME) }
        )
    }

    @Provides
    @Singleton
    fun provideTimeSettingsSerializer() = TimeSettingsSerializer

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        dataStore: DataStore<TimeSettings>
    ): DataStoreRepository = DataStoreRepositoryImpl(dataStore)
}