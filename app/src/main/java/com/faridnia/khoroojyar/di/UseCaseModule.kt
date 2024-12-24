package com.faridnia.khoroojyar.di

import com.faridnia.khoroojyar.domain.repository.WorkDayInfoRepository
import com.faridnia.khoroojyar.domain.use_case.db.DeleteWorkDayInfoUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetAllWorkDayInfosUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetWorkDayInfoByDayUseCase
import com.faridnia.khoroojyar.domain.use_case.db.GetWorkDayInfoByIdUseCase
import com.faridnia.khoroojyar.domain.use_case.db.InsertWorkDayInfoUseCase
import com.faridnia.khoroojyar.domain.use_case.db.UpdateWorkDayInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideInsertWorkDayInfoUseCase(repository: WorkDayInfoRepository): InsertWorkDayInfoUseCase {
        return InsertWorkDayInfoUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateWorkDayInfoUseCase(repository: WorkDayInfoRepository): UpdateWorkDayInfoUseCase {
        return UpdateWorkDayInfoUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteWorkDayInfoUseCase(repository: WorkDayInfoRepository): DeleteWorkDayInfoUseCase {
        return DeleteWorkDayInfoUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetWorkDayInfoByIdUseCase(repository: WorkDayInfoRepository): GetWorkDayInfoByIdUseCase {
        return GetWorkDayInfoByIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAllWorkDayInfosUseCase(repository: WorkDayInfoRepository): GetAllWorkDayInfosUseCase {
        return GetAllWorkDayInfosUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetWorkDayInfoByDayUseCase(repository: WorkDayInfoRepository): GetWorkDayInfoByDayUseCase {
        return GetWorkDayInfoByDayUseCase(repository)
    }
}
