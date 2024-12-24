package com.faridnia.khoroojyar.di

import android.content.Context
import androidx.room.Room
import com.faridnia.khoroojyar.data.room.AppDatabase
import com.faridnia.khoroojyar.data.room.WorkDayInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "workdayinfo_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideWorkDayInfoDao(db: AppDatabase): WorkDayInfoDao {
        return db.workDayInfoDao()
    }
}
